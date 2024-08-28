package io.openim.android.sdk.conversation;

import io.openim.android.sdk.OpenIMClient;
import io.openim.android.sdk.common.SdkException;
import io.openim.android.sdk.common.UpdateConNode;
import io.openim.android.sdk.config.IMConfig;
import io.openim.android.sdk.connection.ConnectionManager;
import io.openim.android.sdk.database.ChatDbManager;
import io.openim.android.sdk.database.ChatLog;
import io.openim.android.sdk.database.LocalConversation;
import io.openim.android.sdk.database.LocalSendingMessage;
import io.openim.android.sdk.enums.Constants;
import io.openim.android.sdk.enums.ConversationType;
import io.openim.android.sdk.enums.MessageType;
import io.openim.android.sdk.enums.ReqIdentifier;
import io.openim.android.sdk.generics.ReturnWithSdkErr;
import io.openim.android.sdk.listener.OnMsgSendCallback;
import io.openim.android.sdk.models.AdvancedMessage;
import io.openim.android.sdk.models.AttachedInfoElem;
import io.openim.android.sdk.models.GetAdvancedHistoryMessageListParams;
import io.openim.android.sdk.models.Message;
import io.openim.android.sdk.models.OfflinePushInfo;
import io.openim.android.sdk.models.TextElem;
import io.openim.android.sdk.protos.sdkws.UserSendMsgResp;
import io.openim.android.sdk.utils.AsyncUtils;
import io.openim.android.sdk.utils.ConvertUtil;
import io.openim.android.sdk.utils.FileUtil;
import io.openim.android.sdk.utils.JsonUtil;
import io.openim.android.sdk.utils.ParamsUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sdk {

    public static SdkException DeleteConversationAndDeleteAllMsg(String conversationID) {
        var localConversationDao = ChatDbManager.getInstance().getImDatabase().localConversationDao();
        var exception = Delete.clearConversationFromLocalAndSvr(conversationID, localConversationDao::resetConversation);
        if (exception == null) {
            return null;
        }

        return new SdkException(SdkException.sdkUnknownErrCode, exception.getMessage());
    }


    public static ReturnWithSdkErr<Message> sendMessage(OnMsgSendCallback callback, Message s, String recvID, String groupID, OfflinePushInfo p,
        boolean isOnlineOnly) {
        var options = new HashMap<String, Boolean>(2);
        var returnWithSdkErr = checkID(s, recvID, groupID, options);
        if (returnWithSdkErr.hasError()) {
            return new ReturnWithSdkErr<>(returnWithSdkErr.getError());
        }

        var lc = returnWithSdkErr.getPayload();

        if (!isOnlineOnly) {
            var oldMessage = ChatDbManager.getInstance().getImDatabase().chatLogDao().getMessage(lc.conversationID, s.getClientMsgID());
            var imDatabase = ChatDbManager.getInstance().getImDatabase();
            //err !=nil, golang impl
            if (oldMessage == null) {
                var localMessage = ConvertUtil.msgStructToLocalChatLog(s);
                localMessage.conversationID = lc.conversationID;

                var id = imDatabase.chatLogDao().insert(localMessage);
                if (id == 0l) {
                    return new ReturnWithSdkErr<>(new SdkException(SdkException.sdInternalErrCode, "LocalChatLog insert error"));
                }

                long rowId = imDatabase.localSendingMessageDao().insert(new LocalSendingMessage(lc.conversationID, localMessage.clientMsgID));
                if (rowId == 0) {
                    return new ReturnWithSdkErr<>(new SdkException(SdkException.sdInternalErrCode, "LocalSendingMessage insert error"));
                }
            } else {
                if (oldMessage.status != Constants.MSG_STATUS_SEND_FAILED) {
                    return new ReturnWithSdkErr<>(SdkException.ErrMsgRepeated);
                } else {
                    s.setStatus(Constants.MSG_STATUS_SENDING);
                    long rowId = imDatabase.localSendingMessageDao().insert(new LocalSendingMessage(lc.conversationID, s.getClientMsgID()));
                    return new ReturnWithSdkErr<>(new SdkException(SdkException.sdInternalErrCode, "LocalSendingMessage insert error"));
                }
            }
            lc.latestMsg = JsonUtil.toString(s);
            Trigger.triggerCmdUpdateConversation(new UpdateConNode(lc.conversationID, Constants.ADD_CON_OR_UP_LAT_MSG, lc), OpenIMClient.getInstance()
                .getConversationCh());
        }

        var delFile = new ArrayList<String>();
        switch (s.getContentType()) {
            case MessageType.PICTURE:
                if (s.getStatus() == Constants.MSG_STATUS_SEND_SUCCESS) {
                    s.setContent(JsonUtil.toString(s.getPictureElem()));
                    break;
                }
                String sourcePath;
                File file = new File(s.getPictureElem().getSourcePath());
                if (file.exists()) {
                    sourcePath = s.getPictureElem().getSourcePath();
                    delFile.add(FileUtil.fileTmpPath(s.getPictureElem().getSourcePath(), IMConfig.getInstance().dataDir));
                } else {
                    sourcePath = FileUtil.fileTmpPath(s.getPictureElem().getSourcePath(), IMConfig.getInstance().dataDir);
                    delFile.add(sourcePath);
                }

                //todo: golang impl, impl later
                // res, err := c.file.UploadFile(ctx, &file.UploadFileReq{
                //			ContentType: s.PictureElem.SourcePicture.Type,
                //			Filepath:    sourcePath,
                //			Uuid:        s.PictureElem.SourcePicture.UUID,
                //			Name:        c.fileName("picture", s.ClientMsgID) + filepathExt(s.PictureElem.SourcePicture.UUID, sourcePath),
                //			Cause:       "msg-picture",
                //		}, NewUploadFileCallback(ctx, callback.OnProgress, s, lc.ConversationID, c.db))
                //		if err != nil {
                //			c.updateMsgStatusAndTriggerConversation(ctx, s.ClientMsgID, "", s.CreateTime, constant.MsgStatusSendFailed, s, lc, isOnlineOnly)
                //			return nil, err
                //		}
                //		s.PictureElem.SourcePicture.Url = res.URL
                //		s.PictureElem.BigPicture = s.PictureElem.SourcePicture
                //		u, err := url.Parse(res.URL)
                //		if err == nil {
                //			snapshot := u.Query()
                //			snapshot.Set("type", "image")
                //			snapshot.Set("width", "640")
                //			snapshot.Set("height", "640")
                //			u.RawQuery = snapshot.Encode()
                //			s.PictureElem.SnapshotPicture = &sdk_struct.PictureBaseInfo{
                //				Width:  640,
                //				Height: 640,
                //				Url:    u.String(),
                //			}
                //		} else {
                //			////log.ZError(ctx, "parse url failed", err, "url", res.URL, "err", err)
                //			s.PictureElem.SnapshotPicture = s.PictureElem.SourcePicture
                //		}
                //		s.Content = utils.StructToJsonString(s.PictureElem)
                break;
            case MessageType.VOICE:
                //todo: voice or sound impl
                break;
            case MessageType.VIDEO:
                //todo: video impl
                break;

            case MessageType.FILE:
                //todo: file impl
                break;

            case MessageType.TEXT:
                s.setContent(JsonUtil.toString(s.getTextElem()));
                break;
            case MessageType.AT_TEXT:
                s.setContent(JsonUtil.toString(s.getAtTextElem()));
                break;
            case MessageType.LOCATION:
                s.setContent(JsonUtil.toString(s.getLocationElem()));
                break;
            case MessageType.CUSTOM:
                s.setContent(JsonUtil.toString(s.getCustomElem()));
                break;
            case MessageType.MERGER:
                s.setContent(JsonUtil.toString(s.getMergeElem()));
                break;
            case MessageType.QUOTE:
                s.setContent(JsonUtil.toString(s.getQuoteElem()));
                break;
            case MessageType.CARD:
                s.setContent(JsonUtil.toString(s.getCardElem()));
                break;
            case MessageType.CUSTOM_FACE:
                s.setContent(JsonUtil.toString(s.getFaceElem()));
                break;
            case MessageType.ADVANCED_TEXT:
                s.setContent(JsonUtil.toString(s.getAdvancedTextElem()));
                break;
            default:
                return new ReturnWithSdkErr<>(SdkException.ErrMsgContentTypeNotSupport);
        }

        var contentType = s.getContentType();
        if (contentType == MessageType.PICTURE || contentType == MessageType.VOICE || contentType == MessageType.VIDEO || contentType == MessageType.FILE) {
            if (!isOnlineOnly) {
                var localMessage = ConvertUtil.msgStructToLocalChatLog(s);
                localMessage.conversationID = lc.conversationID;
                int affectedRows = ChatDbManager.getInstance().getImDatabase().chatLogDao().update(localMessage);
                if (affectedRows == 0) {
                    return new ReturnWithSdkErr<>(new SdkException(SdkException.sdInternalErrCode,
                        " ChatDbManager.getInstance().getImDatabase().localChatLogDao().update(localMessage) failed"));
                }
            }
        }

        return sendMessageToServer(s, lc, delFile, p, options, isOnlineOnly);
    }

    public static ReturnWithSdkErr<Message> createTextMessage(String text) {
        var s = new Message();
        var err = initBasicInfo(s, Constants.USER_MSG_TYPE, MessageType.TEXT);
        if (err != null) {
            return new ReturnWithSdkErr<>(err);
        }
        var textElem = new TextElem();
        textElem.setContent(text);
        s.setTextElem(textElem);
        return new ReturnWithSdkErr<>(s);
    }

    public static ReturnWithSdkErr<AdvancedMessage> getAdvancedHistoryMessageList(GetAdvancedHistoryMessageListParams req) {
        var res = getAdvancedHistoryMessageList(req, false);
        var result = res.getPayload();
        var err = res.getError();
        if (err != null) {
            return new ReturnWithSdkErr<>(err);
        }

        if (result.getMessageList() == null) {
            result.setMessageList(new ArrayList<>());
        }
        return new ReturnWithSdkErr<>(result);
    }

    public static ReturnWithSdkErr<AdvancedMessage> getAdvancedHistoryMessageListReverse(GetAdvancedHistoryMessageListParams req) {
        var res = getAdvancedHistoryMessageList(req, true);
        var result = res.getPayload();
        var err = res.getError();
        if (err != null) {
            return new ReturnWithSdkErr<>(err);
        }

        if (result.getMessageList() == null) {
            result.setMessageList(new ArrayList<>());
        }
        return new ReturnWithSdkErr<>(result);
    }

    private static ReturnWithSdkErr<AdvancedMessage> getAdvancedHistoryMessageList(GetAdvancedHistoryMessageListParams req,
        boolean isReverse) {
        var messageListCallback = new AdvancedMessage();
        boolean notStartTime = false;
        long startTime = 0;
        List<ChatLog> list;

        var conversationID = req.getConversationID();
        var lc = ChatDbManager.getInstance().getImDatabase().localConversationDao().getConversation(conversationID);
        if (lc == null) {
            return new ReturnWithSdkErr<>(new SdkException(SdkException.sdInternalErrCode, String.format("conversation not found: %s", conversationID)));
        }
        var sessionType = lc.conversationType;
        var localChatLogDao = ChatDbManager.getInstance().getImDatabase().chatLogDao();
        if (req.getStartClientMsgID() == null || req.getStartClientMsgID().isEmpty()) {
            notStartTime = true;
        } else {
            var m = localChatLogDao.getMessage(conversationID, req.getStartClientMsgID());
            if (m == null) {
                return new ReturnWithSdkErr<>(new SdkException(SdkException.sdInternalErrCode, String.format("localChatLog not found: %s", conversationID)));
            }
            startTime = m.sendTime;
        }

        if (notStartTime) {
            list = localChatLogDao.getMessageListNoTime(conversationID, req.getCount(), isReverse);
        } else {
            list = localChatLogDao.getMessageList(conversationID, req.getCount(), startTime, isReverse);
        }

        if (list == null) {
            return new ReturnWithSdkErr<>(
                new SdkException(SdkException.sdInternalErrCode, String.format("getMessageList or getMessageListNoTime list not found: %s", conversationID)));
        }

        var rawMessageLength = list.size();
        if (rawMessageLength < req.getCount()) {
            var checkResult = MessageCheck.messageBlocksInternalContinuityCheck(conversationID, notStartTime, isReverse, req.getCount(), startTime, list,
                messageListCallback);
            var maxSeq = checkResult.getMax();
            var minSeq = checkResult.getMin();
            var lostSeqListLength = checkResult.getLength();
            MessageCheck.messageBlocksBetweenContinuityCheck(req.getLastMinSeq(), maxSeq, conversationID, notStartTime, isReverse, req.getCount(), startTime,
                list, messageListCallback);
            if (minSeq == 1 && lostSeqListLength == 0) {
                messageListCallback.setEnd(true);
            } else {
                MessageCheck.messageBlocksEndContinuityCheck(minSeq, conversationID, notStartTime, isReverse, req.getCount(), startTime, list,
                    messageListCallback);
            }
        } else {
            var checkResult = MessageCheck.messageBlocksInternalContinuityCheck(conversationID, notStartTime, isReverse, req.getCount(), startTime, list,
                messageListCallback);
            var maxSeq = checkResult.getMax();
            MessageCheck.messageBlocksBetweenContinuityCheck(req.getLastMinSeq(), maxSeq, conversationID, notStartTime, isReverse, req.getCount(), startTime,
                list, messageListCallback);
        }

        long thisMinSeq = 0;

        var messageList = new ArrayList<Message>();
        for (var v : list) {
            if (v.seq != 0 && thisMinSeq == 0) {
                thisMinSeq = v.seq;
            }

            if (v.seq < thisMinSeq && v.seq != 0) {
                thisMinSeq = v.seq;
            }

            if (v.status >= Constants.MSG_STATUS_HAS_DELETED) {
                continue;
            }

            var temp = new Message();
            temp.setClientMsgID(v.clientMsgID);
            temp.setServerMsgID(v.serverMsgID);
            temp.setCreateTime(v.createTime);
            temp.setSendTime(v.sendTime);
            temp.setSessionType(v.sessionType);
            temp.setSendID(v.sendID);
            temp.setRecvID(v.recvID);
            temp.setMsgFrom(v.msgFrom);
            temp.setContentType(v.contentType);
            temp.setPlatformID(v.senderPlatformID);
            temp.setSenderNickname(v.senderNickname);
            temp.setSenderFaceUrl(v.senderFaceURL);
            temp.setContent(v.content);
            temp.setSeq((int) v.seq);
            temp.setRead(v.isRead);
            temp.setStatus(v.status);
            var attachedInfo = JsonUtil.toObj(v.attachedInfo, AttachedInfoElem.class);
            //todo: hot fix, removed later
            if (attachedInfo == null) {
                continue;
            }
            temp.setAttachedInfoElem(attachedInfo);
            temp.setEx(v.ex);
            var err = Conversation.msgHandleByContentType(temp);
            if (err != null) {
                continue;
            }

            switch (sessionType) {
                case ConversationType.GROUP_CHAT:
                case ConversationType.SUPER_GROUP_CHAT:
                    temp.setGroupID(temp.getRecvID());
                    temp.setRecvID(IMConfig.getInstance().userID);
            }

            if (attachedInfo.isPrivateChat() && temp.getSendTime() + attachedInfo.getBurnDuration() < (System.currentTimeMillis() / 1000)) {
                continue;
            }
            messageList.add(temp);
        }

        if (!isReverse) {
            Collections.sort(messageList);
        }

        messageListCallback.setMessageList(messageList);
        if (thisMinSeq == 0) {
            thisMinSeq = req.getLastMinSeq();
        }
        messageListCallback.setLastMinSeq((int) thisMinSeq);
        return new ReturnWithSdkErr<>(messageListCallback);
    }

    private static SdkException initBasicInfo(Message message, int msgFrom, int contentType) {
        var loginUserID = IMConfig.getInstance().userID;
        message.setCreateTime(System.currentTimeMillis());
        message.setSendTime(message.getCreateTime());
        message.setRead(false);
        message.setStatus(Constants.MSG_STATUS_SENDING);
        message.setSendID(loginUserID);

        var userInfo = ChatDbManager.getInstance().getImDatabase().localUserDao().getLoginUser(loginUserID);
        if (userInfo == null) {
            return new SdkException(SdkException.sdInternalErrCode, String.format("can't getLoginUser for %s", loginUserID));
        } else {
            message.setSenderFaceUrl(userInfo.faceURL);
            message.setSenderNickname(userInfo.nickname);
        }
        var clientMsgID = ParamsUtil.getMsgID(message.getSendID());
        message.setClientMsgID(clientMsgID);
        message.setMsgFrom(msgFrom);
        message.setContentType(contentType);
        message.setPlatformID(IMConfig.getInstance().platformID);
        message.setExternalExtensions(IMConfig.getInstance().isExternalExtensions);
        return null;
    }

    private static ReturnWithSdkErr<Message> sendMessageToServer(Message s, LocalConversation lc, List<String> delFile, OfflinePushInfo offlinePushInfo,
        Map<String, Boolean> options, boolean isOnlineOnly) {
        if (isOnlineOnly) {
            options.put(Constants.IS_HISTORY, false);
            options.put(Constants.IS_PERSISTENT, false);
            options.put(Constants.IS_SENDER_SYNC, false);
            options.put(Constants.IS_CONVERSATION_UPDATE, false);
            options.put(Constants.IS_SENDER_CONVERSATION_UPDATE, false);
            options.put(Constants.IS_UNREAD_COUNT, false);
            options.put(Constants.IS_OFFLINE_PUSH, false);
        }
        var wsMsgData = ConvertUtil.convertToMsgData(s);
        var builder = wsMsgData.toBuilder();
        builder.setAttachedInfo(JsonUtil.toString(s.getAttachedInfoElem()));
        //  golang impl: skip. already implemented in convertToMsgData
        //          wsMsgData.Content = []byte(s.Content)

        builder.setSendTime(0);
        builder.putAllOptions(options);
        if (wsMsgData.getContentType() == MessageType.AT_TEXT) {
            builder.addAllAtUserIDList(s.getAtTextElem().getAtUserList());
        }
        s.setContent("");
        wsMsgData = builder.build();
        ReturnWithSdkErr<UserSendMsgResp> returnWithSdkErr = ConnectionManager.getInstance().getConnection()
            .sendReqWaitResp(ReqIdentifier.SEND_MSG, wsMsgData, UserSendMsgResp.parser());
        var sendMsgResp = returnWithSdkErr.getPayload();
        if (returnWithSdkErr.hasError()) {
            //if send message network timeout need to double-check message has received by db.
            //if sdkerrs.ErrNetworkTimeOut.Is(err) && !isOnlineOnly {
            //			oldMessage, _ := c.db.GetMessage(ctx, lc.ConversationID, s.ClientMsgID)
            //			if oldMessage.Status == constant.MsgStatusSendSuccess {
            //				sendMsgResp.SendTime = oldMessage.SendTime
            //				sendMsgResp.ClientMsgID = oldMessage.ClientMsgID
            //				sendMsgResp.ServerMsgID = oldMessage.ServerMsgID
            //			} else {
            //				////log.ZError(ctx, "send msg to server failed", err, "message", s)
            //				c.updateMsgStatusAndTriggerConversation(ctx, s.ClientMsgID, "", s.CreateTime,
            //					constant.MsgStatusSendFailed, s, lc, isOnlineOnly)
            //				return s, err
            //			}
            //		}

            updateMsgStatusAndTriggerConversation(s.getClientMsgID(), "", s.getCreateTime(), Constants.MSG_STATUS_SEND_FAILED, s, lc, isOnlineOnly);
            return new ReturnWithSdkErr<>(s, returnWithSdkErr.getError());
        }

        s.setSendTime(sendMsgResp.getSendTime());
        s.setStatus(Constants.MSG_STATUS_SEND_SUCCESS);
        s.setServerMsgID(sendMsgResp.getServerMsgID());
        AsyncUtils.runOnConnectThread(() ->
            {
                for (var v : delFile) {
                    new File(v).delete();
                    updateMsgStatusAndTriggerConversation(sendMsgResp.getClientMsgID(), sendMsgResp.getServerMsgID(), sendMsgResp.getSendTime(),
                        Constants.MSG_STATUS_SEND_SUCCESS, s, lc, isOnlineOnly);
                }
            }
        );

        return new ReturnWithSdkErr<>(s);
    }

    private static void updateMsgStatusAndTriggerConversation(String clientMsgID, String serverMsgID, long sendTime, int status, Message s,
        LocalConversation lc,
        boolean isOnlineOnly) {
        s.setSendTime(sendTime);
        s.setStatus(status);
        s.setServerMsgID(serverMsgID);
        var imDatabase = ChatDbManager.getInstance().getImDatabase();
        imDatabase.chatLogDao().updateMessageTimeAndStatus(lc.conversationID, clientMsgID, serverMsgID, sendTime, status);
        imDatabase.localSendingMessageDao().delete(new LocalSendingMessage(lc.conversationID, clientMsgID));
        lc.latestMsg = JsonUtil.toString(s);
        lc.latestMsgSendTime = sendTime;
        Trigger.triggerCmdUpdateConversation(new UpdateConNode(lc.conversationID, Constants.ADD_CON_OR_UP_LAT_MSG, lc), OpenIMClient.getInstance()
            .getConversationCh());
    }

    private static String filepathExt(String... name) {
        for (var path : name) {
            if (path == null || path.length() <= 1) {
                continue;
            }
            int i = path.lastIndexOf(".");
            if (i > 0) {
                return path.substring(i + 1);
            }
        }
        return "";
    }

    private static ReturnWithSdkErr<LocalConversation> checkID(Message s, String recvID, String groupID, Map<String, Boolean> options) {
        if ((recvID == null || recvID.isEmpty()) && (groupID == null || groupID.isEmpty())) {
            return new ReturnWithSdkErr<>(SdkException.ErrArgs);
        }

        s.setSendID(IMConfig.getInstance().userID);
        s.setPlatformID(IMConfig.getInstance().platformID);
        var lc = new LocalConversation();
        lc.latestMsgSendTime = s.getCreateTime();
        //根据单聊群聊类型组装消息和会话
        if (recvID == null || recvID.isEmpty()) {
            // todo: golang impl: group related
            // g, err := c.full.GetGroupInfoByGroupID(ctx, groupID)
            //		if err != nil {
            //			return nil, err
            //		}
            //		lc.ShowName = g.GroupName
            //		lc.FaceURL = g.FaceURL
            //		switch g.GroupType {
            //		case constant.NormalGroup:
            //			s.SessionType = constant.GroupChatType
            //			lc.ConversationType = constant.GroupChatType
            //			lc.ConversationID = c.getConversationIDBySessionType(groupID, constant.GroupChatType)
            //		case constant.SuperGroup, constant.WorkingGroup:
            //			s.SessionType = constant.SuperGroupChatType
            //			lc.ConversationID = c.getConversationIDBySessionType(groupID, constant.SuperGroupChatType)
            //			lc.ConversationType = constant.SuperGroupChatType
            //		}
            //		s.GroupID = groupID
            //		lc.GroupID = groupID
            //		gm, err := c.db.GetGroupMemberInfoByGroupIDUserID(ctx, groupID, c.loginUserID)
            //		if err == nil && gm != nil {
            //			if gm.Nickname != "" {
            //				s.SenderNickname = gm.Nickname
            //			}
            //		}
            //		var attachedInfo sdk_struct.AttachedInfoElem
            //		attachedInfo.GroupHasReadInfo.GroupMemberCount = g.MemberCount
            //		s.AttachedInfoElem = &attachedInfo
        } else {
            s.setSessionType(ConversationType.SINGLE_CHAT);
            s.setRecvID(recvID);
            lc.conversationID = ConvertUtil.getConversationIDByMsg(s);
            lc.userID = recvID;
            lc.conversationType = ConversationType.SINGLE_CHAT;
            var oldLc = ChatDbManager.getInstance().getImDatabase().localConversationDao().getConversation(lc.conversationID);
            if (oldLc != null && oldLc.isPrivateChat) {
                options.put(Constants.IS_NOT_PRIVATE, false);
                var attachedInfo = new AttachedInfoElem();
                attachedInfo.setPrivateChat(true);
                attachedInfo.setBurnDuration(oldLc.burnDuration);
                s.setAttachedInfoElem(attachedInfo);
            }

            if (oldLc == null) {
                //golang impl: skip
                // t := time.Now()
                //			faceUrl, name, err := c.getUserNameAndFaceURL(ctx, recvID)
                //			////log.ZDebug(ctx, "GetUserNameAndFaceURL", "cost time", time.Since(t))
                //			if err != nil {
                //				return nil, err
                //			}
                //			lc.FaceURL = faceUrl
                //			lc.ShowName = name
            }
        }
        return new ReturnWithSdkErr<>(lc);

    }
}
