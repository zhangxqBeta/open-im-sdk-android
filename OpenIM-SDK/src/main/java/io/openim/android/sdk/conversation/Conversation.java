package io.openim.android.sdk.conversation;

import io.openim.android.sdk.OpenIMClient;
import io.openim.android.sdk.common.Cmd2Value;
import io.openim.android.sdk.common.DeleteConNode;
import io.openim.android.sdk.common.UpdateConNode;
import io.openim.android.sdk.config.IMConfig;
import io.openim.android.sdk.connection.ConnectionManager;
import io.openim.android.sdk.database.ChatDbManager;
import io.openim.android.sdk.database.ChatLog;
import io.openim.android.sdk.database.LocalConversation;
import io.openim.android.sdk.database.LocalErrChatLog;
import io.openim.android.sdk.enums.Cmd;
import io.openim.android.sdk.enums.Constants;
import io.openim.android.sdk.enums.ConversationType;
import io.openim.android.sdk.enums.MessageType;
import io.openim.android.sdk.generics.ReturnWithErr;
import io.openim.android.sdk.http.ApiClient;
import io.openim.android.sdk.http.ServerApiRouter;
import io.openim.android.sdk.internal.cache.Cache;
import io.openim.android.sdk.internal.log.LogcatHelper;
import io.openim.android.sdk.internal.user.User;
import io.openim.android.sdk.listener.OnAdvanceMsgListener;
import io.openim.android.sdk.listener.OnConversationListener;
import io.openim.android.sdk.models.AdvancedTextElem;
import io.openim.android.sdk.models.AttachedInfoElem;
import io.openim.android.sdk.models.CardElem;
import io.openim.android.sdk.models.CustomElem;
import io.openim.android.sdk.models.FaceElem;
import io.openim.android.sdk.models.FileElem;
import io.openim.android.sdk.models.LocationElem;
import io.openim.android.sdk.models.MergeElem;
import io.openim.android.sdk.models.Message;
import io.openim.android.sdk.models.NotificationElem;
import io.openim.android.sdk.models.PictureElem;
import io.openim.android.sdk.models.QuoteElem;
import io.openim.android.sdk.models.SoundElem;
import io.openim.android.sdk.models.TextElem;
import io.openim.android.sdk.models.TypingElem;
import io.openim.android.sdk.models.VideoElem;
import io.openim.android.sdk.protos.conversation.GetAllConversationsReq;
import io.openim.android.sdk.protos.conversation.GetAllConversationsResp;
import io.openim.android.sdk.protos.conversation.GetConversationsReq;
import io.openim.android.sdk.protos.conversation.GetConversationsResp;
import io.openim.android.sdk.protos.msg.GetConversationsHasReadAndMaxSeqReq;
import io.openim.android.sdk.protos.msg.GetConversationsHasReadAndMaxSeqResp;
import io.openim.android.sdk.protos.msg.Seqs;
import io.openim.android.sdk.protos.sdkws.ConversationHasReadTips;
import io.openim.android.sdk.sdkdto.CmdNewMsgComeToConversation;
import io.openim.android.sdk.utils.AsyncUtils;
import io.openim.android.sdk.utils.BatchUtil;
import io.openim.android.sdk.utils.ConvertUtil;
import io.openim.android.sdk.utils.GeneralUtil;
import io.openim.android.sdk.utils.JsonUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.function.Function;
import java.util.stream.Collectors;
import open_im_sdk_callback.OnBatchMsgListener;

public class Conversation {

    private Syncer<LocalConversation, String> conversationSyncer;
    private OnConversationListener onConversationListener;
    private OnAdvanceMsgListener msgListener;
    private OnBatchMsgListener batchMsgListener;
    private ChatDbManager chatDbManager = ChatDbManager.getInstance();
    private User user;
    private Cache<String, LocalConversation> cache;
    private MaxSeqRecorder maxSeqRecorder;
    private Date startTime;
//    private BlockingQueue<Cmd2Value> recvCH;

    public static Conversation getInstance() {
        return Conversation.SingletonHolder.instance;
    }

    private static class SingletonHolder {

        static Conversation instance = new Conversation();
    }

    private Conversation() {
        initConversationSyncer();
        cache = new Cache<>();
        maxSeqRecorder = new MaxSeqRecorder();
        user = User.getInstance();
    }

    public BlockingQueue<Cmd2Value> getConversationCh() {
        return OpenIMClient.getInstance().getConversationCh();
    }

//    public void setConversationCh(
//        BlockingQueue<Cmd2Value> conversationCh) {
//        this.recvCH = conversationCh;
//    }

    public OnConversationListener getOnConversationListener() {
        return onConversationListener;
    }

    public void setOnConversationListener(
        OnConversationListener onConversationListener) {
        this.onConversationListener = onConversationListener;
    }

    public OnBatchMsgListener getBatchMsgListener() {
        return batchMsgListener;
    }

    public void setBatchMsgListener(OnBatchMsgListener batchMsgListener) {
        this.batchMsgListener = batchMsgListener;
    }

    public OnAdvanceMsgListener getMsgListener() {
        return msgListener;
    }

    public void setMsgListener(OnAdvanceMsgListener msgListener) {
        this.msgListener = msgListener;
    }

    public Date getStartTime() {
        return startTime;
    }

    public MaxSeqRecorder getMaxSeqRecorder() {
        return maxSeqRecorder;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }


    public void doListener() {
        while (true) {
            try {
                //conversationCh
                Cmd2Value cmd = getConversationCh().take();
                LogcatHelper.logDInDebug(String.format("conversationCh doListener: %s", cmd));
                work(cmd);
            } catch (InterruptedException e) {

            }
        }
    }

    private void work(Cmd2Value c2v) {
        switch (c2v.cmd) {
            case Cmd.CMD_DELETE_CONVERSATION:
                ConversationNotification.doDeleteConversation(c2v);
                break;
            case Cmd.CMD_NEW_MSG_COME:
                doMsgNew(c2v);
                break;
            // golang impl(no op):	case constant.CmdSuperGroupMsgCome:
            case Cmd.CMD_UPDATE_CONVERSATION:
                ConversationNotification.doUpdateConversation(c2v);
                break;
            case Cmd.CMD_UPDATE_MESSAGE:
                ConversationNotification.doUpdateMessage(c2v);
                break;
            //golang impl(no op): case constant.CmSyncReactionExtensions:
            case Cmd.CMD_NOTIFICATION:
                doNotificationNew(c2v);
                break;
        }

    }


    private void doNotificationNew(Cmd2Value cmd2Value) {
        CmdNewMsgComeToConversation value = (CmdNewMsgComeToConversation) cmd2Value.value;
        var allMsg = value.msgs;
        var syncFlag = value.syncFlag;
        switch (syncFlag) {
            case Constants.MSG_SYNC_BEGIN:
                Date startTime = new Date();
                Conversation.getInstance().setStartTime(startTime);
                Conversation.getInstance().getOnConversationListener().onSyncServerStart();
                var err = Sync.syncAllConversationHasReadSeqs();
                if (err != null) {
                    LogcatHelper.logDInDebug(String.format("SyncConversationHashReadSeqs %s", err));
                }

                User.getInstance().getOnlineStatusCache().deleteAll();
                User.getInstance().syncLoginUserInfo();
                //golang impl, SyncLoginUserInfo for now.
                // for _, syncFunc := range []func(c context.Context) error{
                //			c.user.SyncLoginUserInfo,
                //			c.friend.SyncAllBlackList, c.friend.SyncAllFriendList, c.friend.SyncAllFriendApplication, c.friend.SyncAllSelfFriendApplication,
                //			c.group.SyncAllJoinedGroupsAndMembers, c.group.SyncAllAdminGroupApplication, c.group.SyncAllSelfGroupApplication,
                //		} {
                //			go func(syncFunc func(c context.Context) error) {
                //				_ = syncFunc(ctx)
                //			}(syncFunc)
                //			time.Sleep(100 * time.Millisecond)
                //		}
                //
                break;
            case Constants.MSG_SYNC_FAILED:
                Conversation.getInstance().getOnConversationListener().onSyncServerFailed();
                break;
            case Constants.MSG_SYNC_END:
                AsyncUtils.runOnConnectThread(() -> {
                    Sync.syncAllConversations();
                });
                Conversation.getInstance().getOnConversationListener().onSyncServerFinish();
        }

        for (var entry : allMsg.entrySet()) {
            var conversationID = entry.getKey();
            var msgs = entry.getValue();
            if (msgs.getMsgsList() != null && !msgs.getMsgsList().isEmpty()) {
                var lastMsg = msgs.getMsgs(msgs.getMsgsList().size() - 1);
                if (lastMsg.getSeq() != 0) {
                    var err = ChatDbManager.getInstance().setNotificationSeq(conversationID, lastMsg.getSeq());
                    if (err != null) {
                        LogcatHelper.logDInDebug(String.format("SetNotificationSeq err : %s, conversationID: %s, lastMsg: %s", err, conversationID, lastMsg));
                    }
                }
            }

            for (var v : msgs.getMsgsList()) {
                switch (v.getContentType()) {
                    case MessageType.CONVERSATION_CHANGE_NTF:
                        ConversationNotification.doConversationChangedNotification(v);
                        break;
                    case MessageType.CONVERSATION_UNREAD_NOTIFICATION:
                        LogcatHelper.logDInDebug("no such impl, LocalConversationUnreadMessage is missing)");
                        var tips = JsonUtil.toObj(v.getContent().toString(), ConversationHasReadTips.class);
                        ConversationNotification.doUpdateConversation(
                            new Cmd2Value(new UpdateConNode(tips.getConversationID(), Constants.UNREAD_COUNT_SET_ZERO)));
                        // golang impl, the related db is not initialized
                        // c.db.DeleteConversationUnreadMessageList(ctx, tips.ConversationID, tips.UnreadCountTime)
                        ConversationNotification.doUpdateConversation(
                            new Cmd2Value(new UpdateConNode(Constants.CON_CHANGE, new String[]{tips.getConversationID()})));
                        continue;
                    case MessageType.CLEAR_CONVERSATION_NOTIFICATION:
                        Delete.doClearConversations(v);
                        break;
                    case MessageType.DELETE_MSGS_NOTIFICATION:
                        Delete.doDeleteMsgs(v);
                        break;
                    case MessageType.HAS_READ_RECEIPT:
                        ReadDrawing.doReadDrawing(v);
                        break;
                }

            }
        }
    }

    private void doMsgNew(Cmd2Value c2v) {
        CmdNewMsgComeToConversation value = (CmdNewMsgComeToConversation) c2v.value;
        var allMsg = value.msgs;
        boolean isTriggerUnReadCount = false;
        var insertMsg = new HashMap<String, List<ChatLog>>(10);
        var updateMsg = new HashMap<String, List<ChatLog>>(10);
        var exceptionMsg = new ArrayList<LocalErrChatLog>();
        var newMessages = new ArrayList<Message>();
        boolean isUnreadCount, isConversationUpdate, isHistory, isNotPrivate, isSenderConversationUpdate;
        var conversationChangedSet = new HashMap<String, LocalConversation>();
        var newConversationSet = new HashMap<String, LocalConversation>();
        var conversationSet = new HashMap<String, LocalConversation>();
        var phConversationChangedSet = new HashMap<String, LocalConversation>();
        var phNewConversationSet = new HashMap<String, LocalConversation>();

        var onlineMap = new HashSet<OnlineMsgKey>();
        for (var entry : allMsg.entrySet()) {
            var conversationID = entry.getKey();
            var msgs = entry.getValue();
            var insertMessage = new ArrayList<ChatLog>();
            var selfInsertMessage = new ArrayList<ChatLog>();
            var othersInsertMessage = new ArrayList<ChatLog>();
            var updateMessage = new ArrayList<ChatLog>();
            for (var v : msgs.getMsgsList()) {
                var options = v.getOptionsMap();
                isHistory = GeneralUtil.getSwitchFromOptions(options, Constants.IS_HISTORY);
                isUnreadCount = GeneralUtil.getSwitchFromOptions(options, Constants.IS_UNREAD_COUNT);
                isConversationUpdate = GeneralUtil.getSwitchFromOptions(options, Constants.IS_CONVERSATION_UPDATE);
                isNotPrivate = GeneralUtil.getSwitchFromOptions(options, Constants.IS_NOT_PRIVATE);
                isSenderConversationUpdate = GeneralUtil.getSwitchFromOptions(options, Constants.IS_SENDER_CONVERSATION_UPDATE);
                var msg = ConvertUtil.convertToMsgStruct(v);
                //unnecessary golang impl: msg.Content = string(v.Content)
                var attachedInfo = JsonUtil.toObj(v.getAttachedInfo(), AttachedInfoElem.class);
                msg.setAttachedInfoElem(attachedInfo);
                msg.setStatus(Constants.MSG_STATUS_SEND_SUCCESS);
                var err = Conversation.msgHandleByContentType(msg);
                if (err != null) {
                    continue;
                }
                if (msg.getStatus() == Constants.MSG_STATUS_HAS_DELETED) {
                    insertMessage.add(ConvertUtil.convertToLocalChatLog(msg));
                    continue;
                }
                if (!isNotPrivate) {
                    msg.getAttachedInfoElem().setPrivateChat(true);
                }

                if (msg.getClientMsgID() == null || msg.getClientMsgID().isEmpty()) {
                    exceptionMsg.add(ConvertUtil.convertToLocalErrChatLog(msg));
                    continue;
                }
                if (conversationID.isEmpty()) {
                    continue;
                }

                if (!isHistory) {
                    onlineMap.add(new OnlineMsgKey(v.getClientMsgID(), v.getServerMsgID()));
                    newMessages.add(msg);
                }

                if (v.getSendID().equals(IMConfig.getInstance().userID)) {
                    try {
                        var m = ChatDbManager.getInstance().getImDatabase().chatLogDao().getMessage(conversationID, msg.getClientMsgID());
                        if (m.seq == 0) {
                            if (!isConversationUpdate) {
                                msg.setStatus(Constants.MSG_STATUS_FILTERED);
                            }
                            updateMessage.add(ConvertUtil.convertToLocalChatLog(msg));

                        } else {
                            exceptionMsg.add(ConvertUtil.convertToLocalErrChatLog(msg));
                        }
                    } catch (Exception e) {
                        //golang impl: //log.ZInfo(ctx, "sync message", "msg", msg)
                        var lc = new LocalConversation();
                        lc.conversationType = v.getSessionType();
                        lc.latestMsg = JsonUtil.toString(msg);
                        lc.latestMsgSendTime = msg.getSendTime();
                        lc.conversationID = conversationID;
                        switch (v.getSessionType()) {
                            case ConversationType.SINGLE_CHAT:
                                lc.userID = v.getRecvID();
                                break;
                            case ConversationType.GROUP_CHAT:
                            case ConversationType.SUPER_GROUP_CHAT:
                                lc.groupID = v.getGroupID();
                                break;
                        }
                        if (isConversationUpdate) {
                            if (isSenderConversationUpdate) {
                                Conversation.updateConversation(lc, conversationSet);
                            }
                            newMessages.add(msg);
                        }
                        if (isHistory) {
                            selfInsertMessage.add(ConvertUtil.convertToLocalChatLog(msg));
                        }

                    }
                } else { //sent by others
                    var m = ChatDbManager.getInstance().getImDatabase().chatLogDao().getMessage(conversationID, msg.getClientMsgID());
                    //error
                    if (m == null) {
                        var lc = new LocalConversation();
                        lc.conversationType = v.getSessionType();
                        lc.latestMsg = JsonUtil.toString(msg);
                        lc.latestMsgSendTime = msg.getSendTime();
                        lc.conversationID = conversationID;

                        switch (v.getSessionType()) {
                            case ConversationType.SINGLE_CHAT:
                                lc.userID = v.getSendID();
                                lc.showName = msg.getSenderNickname();
                                lc.faceURL = msg.getSenderFaceUrl();
                                break;
                            case ConversationType.GROUP_CHAT:
                            case ConversationType.SUPER_GROUP_CHAT:
                                lc.groupID = v.getGroupID();
                                break;
                            case ConversationType.NOTIFICATION:
                                lc.userID = v.getSendID();
                                break;
                        }

                        if (isUnreadCount) {
                            if (Conversation.getInstance().getMaxSeqRecorder().isNewMsg(conversationID, msg.getSeq())) {
                                isTriggerUnReadCount = true;
                                lc.unreadCount = 1;
                                Conversation.getInstance().getMaxSeqRecorder().incr(conversationID, 1);
                            }
                        }

                        if (isConversationUpdate) {
                            Conversation.updateConversation(lc, conversationSet);
                            newMessages.add(msg);
                        }

                        if (isHistory) {
                            othersInsertMessage.add(ConvertUtil.convertToLocalChatLog(msg));
                        }

                    } else {
                        exceptionMsg.add(ConvertUtil.convertToLocalErrChatLog(msg));
                        msg.setStatus(Constants.MSG_STATUS_FILTERED);
                        msg.setClientMsgID(msg.getClientMsgID() + System.nanoTime());
                        othersInsertMessage.add(ConvertUtil.convertToLocalChatLog(msg));
                    }
                }

            }
            insertMessage.addAll(MessageCheck.faceURLAndNicknameHandle(selfInsertMessage, othersInsertMessage, conversationID));
            insertMsg.put(conversationID, insertMessage);
            updateMsg.put(conversationID, updateMessage);
        }

        var list = ChatDbManager.getInstance().getImDatabase().localConversationDao().getAllConversationListDB();
        var m = list.stream().collect(Collectors.toMap(LocalConversation::getConversationID, Function.identity()));
        diff(m, conversationSet, conversationChangedSet, newConversationSet);

        //seq sync message update
        MessageController.batchUpdateMessageList(updateMsg);
        MessageController.batchInsertMessageList(insertMsg);

        var hList = ChatDbManager.getInstance().getImDatabase().localConversationDao().getHiddenConversationList();
        for (var v : hList) {
            if (newConversationSet.containsKey(v.conversationID)) {
                var nc = newConversationSet.get(v.conversationID);
                phConversationChangedSet.put(v.conversationID, nc);
                nc.recvMsgOpt = v.recvMsgOpt;
                nc.groupAtType = v.groupAtType;
                nc.isPinned = v.isPinned;
                nc.isPrivateChat = v.isPrivateChat;

                if (nc.isPrivateChat) {
                    nc.burnDuration = v.burnDuration;
                }

                if (v.unreadCount != 0) {
                    nc.unreadCount = v.unreadCount;
                }

                nc.isNotInGroup = v.isNotInGroup;
                nc.attachedInfo = v.attachedInfo;
                nc.ex = v.ex;
                nc.isMsgDestruct = v.isMsgDestruct;
                nc.msgDestructTime = v.msgDestructTime;
            }
        }

        for (var entry : newConversationSet.entrySet()) {
            var k = entry.getKey();
            var v = entry.getValue();
            if (!phConversationChangedSet.containsKey(v.conversationID)) {
                phNewConversationSet.put(k, v);
            }
        }

        List<LocalConversation> conversationChangedList = new ArrayList<>(conversationChangedSet.values());
        conversationChangedList.addAll(phConversationChangedSet.values());
        ChatDbManager.getInstance().getImDatabase().localConversationDao().batchUpdateConversationList(conversationChangedList);

        ChatDbManager.getInstance().getImDatabase().localConversationDao().batchInsert(new ArrayList<>(phNewConversationSet.values()));
        var batchMsgListener = Conversation.getInstance().getBatchMsgListener();
        if (batchMsgListener != null) {
            Conversation.getInstance().batchNewMessages(newMessages);
        } else {
            Conversation.getInstance().newMessage(newMessages, conversationChangedSet, newConversationSet, onlineMap);
        }

        if (newConversationSet.size() > 0) {
            ConversationNotification.doUpdateConversation(
                new Cmd2Value(new UpdateConNode(Constants.NEW_CON_DIRECT, JsonUtil.toString(new ArrayList<>(newConversationSet.values())))));
        }

        if (conversationChangedSet.size() > 0) {
            ConversationNotification.doUpdateConversation(
                new Cmd2Value(new UpdateConNode(Constants.CON_CHANGE_DIRECT, JsonUtil.toString(new ArrayList<>(conversationChangedSet.values()))))
            );
        }

        if (isTriggerUnReadCount) {
            ConversationNotification.doUpdateConversation(
                new Cmd2Value(new UpdateConNode(Constants.TOTAL_UNREAD_MESSAGE_CHANGED, ""))
            );
        }

        for (var entry : allMsg.entrySet()) {
            var msgs = entry.getValue();
            for (var msg : msgs.getMsgsList()) {
                if (msg.getContentType() == MessageType.TYPING) {
                    //typing todo

                }
            }
        }
    }


    public void diff(Map<String, LocalConversation> local, Map<String, LocalConversation> generated, Map<String, LocalConversation> cc,
        Map<String, LocalConversation> nc) {
        var newConversations = new ArrayList<LocalConversation>();
        for (var entry : generated.entrySet()) {
            var v = entry.getValue();
            if (local.containsKey(v.conversationID)) {
                var localC = local.get(v.conversationID);
                if (v.latestMsgSendTime > localC.latestMsgSendTime) {
                    localC.unreadCount = localC.unreadCount + v.unreadCount;
                    localC.latestMsg = v.latestMsg;
                    localC.latestMsgSendTime = v.latestMsgSendTime;
                    cc.put(v.conversationID, localC);
                } else {
                    localC.unreadCount = localC.unreadCount + v.unreadCount;
                    cc.put(v.conversationID, localC);
                }
            } else {
                newConversations.add(v);
            }
        }

        //skip: 	if err := c.batchAddFaceURLAndName(ctx, newConversations...); err != nil
        for (var v : newConversations) {
            nc.put(v.conversationID, v);
        }
    }


    private void initConversationSyncer() {
        conversationSyncer = new Syncer<>(
            //insert
            (LocalConversation value) -> {
                chatDbManager.getImDatabase().localConversationDao().insert(value);
                return null;
            },
            //delete
            (LocalConversation value) -> {
                chatDbManager.getImDatabase().localConversationDao().delete(value);
                return null;
            },
            //update
            (LocalConversation serverConversation, LocalConversation localConversation) -> {
                return null;
            },
            //uuid
            (LocalConversation value) -> {
                return value.conversationID;
            },
            //equals
            (LocalConversation serverConversation, LocalConversation localConversation) -> {
                return false;
            },
            //notice
            null
        );

    }

    public static ReturnWithErr<LocalConversation> getOneConversation(int sessionType, String sourceID) {
        String conversationID = ConvertUtil.getConversationIDBySessionType(IMConfig.getInstance().userID, sourceID, sessionType);
        var localConversationDao = ChatDbManager.getInstance().getImDatabase().localConversationDao();
        var lc = localConversationDao.getConversation(conversationID);
        if (lc != null) {
            return new ReturnWithErr<>(lc);
        } else {
            var newConversation = new LocalConversation();
            newConversation.conversationID = conversationID;
            newConversation.conversationType = sessionType;
            switch (sessionType) {
                case ConversationType.SINGLE_CHAT:
                    newConversation.userID = sourceID;
                    //golang impl, skip
                    //			faceUrl, name, err := c.getUserNameAndFaceURL(ctx, sourceID)
                    //			if err != nil {
                    //				return nil, err
                    //			}
                    //			newConversation.ShowName = name
                    //			newConversation.FaceURL = faceUrl
                    break;
                case ConversationType.GROUP_CHAT:
                case ConversationType.SUPER_GROUP_CHAT:
                    newConversation.groupID = sourceID;
                    //todo: group impl
                    // g, err := c.full.GetGroupInfoFromLocal2Svr(ctx, sourceID, sessionType)
                    //			if err != nil {
                    //				return nil, err
                    //			}
                    //			newConversation.ShowName = g.GroupName
                    //			newConversation.FaceURL = g.FaceURL
                    break;
            }
            //fetch again
            lc = localConversationDao.getConversation(conversationID);
            if (lc != null) {
                return new ReturnWithErr<>(lc);
            }
            var id = localConversationDao.insert(newConversation);
            if (id == 0) {
                return new ReturnWithErr<>(new Exception("getOneConversation insert failed"));
            }
            return new ReturnWithErr<>(newConversation);
        }
    }

    public static void updateConversation(LocalConversation lc, Map<String, LocalConversation> cs) {
        if (!cs.containsKey(lc.conversationID)) {
            cs.put(lc.conversationID, lc);
        } else {
            var oldC = cs.get(lc.conversationID);
            if (lc.latestMsgSendTime > oldC.latestMsgSendTime) {
                oldC.unreadCount = oldC.unreadCount + lc.unreadCount;
                oldC.latestMsg = lc.latestMsg;
                oldC.latestMsgSendTime = lc.latestMsgSendTime;
                cs.put(lc.conversationID, oldC);
            } else {
                oldC.unreadCount = oldC.unreadCount + lc.unreadCount;
                cs.put(lc.conversationID, oldC);
            }
        }
    }

    public static Exception msgConvert(Message msg) {
        var err = msgHandleByContentType(msg);
        if (err != null) {
            return err;
        } else {
            if (msg.getSessionType() == ConversationType.GROUP_CHAT) {
                msg.setGroupID(msg.getRecvID());
                msg.setRecvID(IMConfig.getInstance().userID);
            }
            return null;
        }
    }

    public static Exception msgHandleByContentType(Message msg) {
        switch (msg.getContentType()) {
            case MessageType.TEXT:
                var t = JsonUtil.toObj(msg.getContent(), TextElem.class);
                msg.setTextElem(t);
                break;
            case MessageType.PICTURE:
                var p = JsonUtil.toObj(msg.getContent(), PictureElem.class);
                msg.setPictureElem(p);
                break;
            case MessageType.VOICE:
                var s = JsonUtil.toObj(msg.getContent(), SoundElem.class);
                msg.setSoundElem(s);
                break;
            case MessageType.VIDEO:
                var v = JsonUtil.toObj(msg.getContent(), VideoElem.class);
                msg.setVideoElem(v);
                break;
            case MessageType.FILE:
                var f = JsonUtil.toObj(msg.getContent(), FileElem.class);
                msg.setFileElem(f);
                break;
            case MessageType.ADVANCED_TEXT:
                var a = JsonUtil.toObj(msg.getContent(), AdvancedTextElem.class);
                msg.setAdvancedTextElem(a);
                break;
            case MessageType.AT_TEXT:
                //todo, golang impl
                //		t := sdk_struct.AtTextElem{}
                //		err = utils.JsonStringToStruct(msg.Content, &t)
                //		msg.AtTextElem = &t
                //		if err == nil {
                //			if utils.IsContain(c.loginUserID, msg.AtTextElem.AtUserList) {
                //				msg.AtTextElem.IsAtSelf = true
                //			}
                //		}
                break;
            case MessageType.LOCATION:
                var l = JsonUtil.toObj(msg.getContent(), LocationElem.class);
                msg.setLocationElem(l);
                break;

            case MessageType.CUSTOM:
            case MessageType.CUSTOM_MSG_NOT_TRIGGER_CONVERSATION:
            case MessageType.CUSTOM_MSG_ONLINE_ONLY:
                var c = JsonUtil.toObj(msg.getContent(), CustomElem.class);
                msg.setCustomElem(c);
                break;
            case MessageType.TYPING:
                var typing = JsonUtil.toObj(msg.getContent(), TypingElem.class);
                msg.setTypingElem(typing);
                break;
            case MessageType.QUOTE:
                var q = JsonUtil.toObj(msg.getContent(), QuoteElem.class);
                msg.setQuoteElem(q);
                break;
            case MessageType.MERGER:
                var m = JsonUtil.toObj(msg.getContent(), MergeElem.class);
                msg.setMergeElem(m);
                break;
            case MessageType.CUSTOM_FACE:
                var cf = JsonUtil.toObj(msg.getContent(), FaceElem.class);
                msg.setFaceElem(cf);
                break;
            case MessageType.CARD:
                var card = JsonUtil.toObj(msg.getContent(), CardElem.class);
                msg.setCardElem(card);
                break;
            default:
                var n = JsonUtil.toObj(msg.getContent(), NotificationElem.class);
                msg.setNotificationElem(n);
        }

        msg.setContent("");
        return null;
    }

    public Syncer<LocalConversation, String> getConversationSyncer() {
        return conversationSyncer;
    }


    public static ReturnWithErr<List<LocalConversation>> getServerConversationList() {
        var returnWithErr = ApiClient.callApi(ServerApiRouter.GetAllConversationsRouter,
            GetAllConversationsReq.newBuilder().setOwnerUserID(IMConfig.getInstance().userID),
            GetAllConversationsResp.class);
        if (returnWithErr.hasError()) {
            return new ReturnWithErr<>(returnWithErr.getError());
        }

        var resp = returnWithErr.getPayload();
        List<LocalConversation> localConversations = BatchUtil.batch(Convert::serverConversationToLocal, resp.getConversationsList());
        return new ReturnWithErr<>(localConversations);
    }

    public ReturnWithErr<List<LocalConversation>> getServerConversationsByIDs(List<String> conversations) {
        var loginUserID = IMConfig.getInstance().userID;
        var getConversationsReq = GetConversationsReq.newBuilder().setOwnerUserID(loginUserID).addAllConversationIDs(conversations).build();
        var returnWithErr = ApiClient.callApi(ServerApiRouter.GetConversationsRouter, getConversationsReq, GetConversationsResp.class);
        if (returnWithErr.getError() != null) {
            return new ReturnWithErr<>(returnWithErr.getError());
        }
        var response = returnWithErr.getPayload();
        List<LocalConversation> localConversations = BatchUtil.batch(Convert::serverConversationToLocal, response.getConversationsList());
        return new ReturnWithErr<>(localConversations);
    }

    public ReturnWithErr<Map<String, Seqs>> getServerHasReadAndMaxSeqs(List<String> conversationIDs) {
        var req = GetConversationsHasReadAndMaxSeqReq.newBuilder().setUserID(IMConfig.getInstance().userID)
            .addAllConversationIDs(conversationIDs).build();
        ReturnWithErr<GetConversationsHasReadAndMaxSeqResp> returnWithErr = ApiClient.apiPost(ServerApiRouter.GetConversationsHasReadAndMaxSeqRouter, req,
            GetConversationsHasReadAndMaxSeqResp.class);
        if (returnWithErr.hasError()) {
            return new ReturnWithErr<>(returnWithErr.getError());
        }

        return new ReturnWithErr<>(returnWithErr.getPayload().getSeqsMap());
    }

    public void batchNewMessages(List<Message> newMessagesList) {
        Collections.sort(newMessagesList);
        if (newMessagesList.size() > 0) {
            batchMsgListener.onRecvNewMessages(JsonUtil.toString(newMessagesList));
        }
    }

    public void newMessage(List<Message> newMessagesList, Map<String, LocalConversation> cc, Map<String, LocalConversation> nc, Set<OnlineMsgKey> onlineMsg) {
        Collections.sort(newMessagesList);
        if (ConnectionManager.getInstance().isBackground()) {

            var returnWithErr = User.getSelfUserInfo();
            if (returnWithErr.hasError()) {
                LogcatHelper.logDInDebug(returnWithErr.getError().getMessage());
                return;
            }

            var u = returnWithErr.getPayload();

            if (u.globalRecvMsgOpt != Constants.RECEIVE_MESSAGE) {
                return;
            }

            for (var w : newMessagesList) {
                var conversationID = ConvertUtil.getConversationIDByMsg(w);
                if (cc.containsKey(conversationID)) {
                    var v = cc.get(conversationID);
                    if (v.recvMsgOpt == Constants.RECEIVE_MESSAGE) {
                        Conversation.getInstance().msgListener.onRecvOfflineNewMessage(Arrays.asList(w));
                    }
                }

                if (nc.containsKey(conversationID)) {
                    var v = nc.get(conversationID);
                    if (v.recvMsgOpt == Constants.RECEIVE_MESSAGE) {
                        Conversation.getInstance().msgListener.onRecvOfflineNewMessage(Arrays.asList(w));
                    }
                }
            }
        } else {
            for (var w : newMessagesList) {
                if (w.getContentType() == MessageType.TYPING) {
                    continue;
                }

                if (onlineMsg.contains(new OnlineMsgKey(w.getClientMsgID(), w.getServerMsgID()))) {
                    Conversation.getInstance().msgListener.onRecvOnlineOnlyMessage(JsonUtil.toString(w));
                } else {
                    Conversation.getInstance().msgListener.onRecvNewMessage(w);
                }
            }
        }
    }
}
