package io.openim.android.sdk.listener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.openim.android.sdk.models.C2CReadReceiptInfo;
import io.openim.android.sdk.models.GroupMessageReceipt;
import io.openim.android.sdk.models.KeyValue;
import io.openim.android.sdk.models.Message;
import io.openim.android.sdk.models.RevokedInfo;
import io.openim.android.sdk.utils.CommonUtil;
import io.openim.android.sdk.utils.JsonUtil;


final public class _AdvanceMsgListener extends BaseListener<OnAdvanceMsgListener>
    implements open_im_sdk_callback.OnAdvancedMsgListener {

    public _AdvanceMsgListener(OnAdvanceMsgListener listener) {
        super(listener);
    }

    @Override
    public void onMsgDeleted(String s) {
        Message msg = JsonUtil.toObj(s, Message.class);
        post(() -> listener.onMsgDeleted(msg));
    }

    @Override
    public void onNewRecvMessageRevoked(String s) {
        RevokedInfo info = JsonUtil.toObj(s, RevokedInfo.class);
        post(() -> listener.onRecvMessageRevokedV2(info));
    }

    @Override
    public void onRecvC2CReadReceipt(String s) {
        List<C2CReadReceiptInfo> list = JsonUtil.toArray(s, C2CReadReceiptInfo.class);
        post(() -> listener.onRecvC2CReadReceipt(list));
    }

    @Override
    public void onRecvGroupReadReceipt(String s) {
        GroupMessageReceipt groupMessageReceipt = JsonUtil.toObj(s, GroupMessageReceipt.class);
        post(() -> listener.onRecvGroupMessageReadReceipt(groupMessageReceipt));
    }

    @Override
    public void onRecvMessageExtensionsAdded(String s, String s1) {
        List<KeyValue> list = JsonUtil.toArray(s1, KeyValue.class);
        post(() -> listener.onRecvMessageExtensionsAdded(s, list));
    }

    @Override
    public void onRecvMessageExtensionsChanged(String s, String s1) {
        List<KeyValue> list = JsonUtil.toArray(s1, KeyValue.class);
        post(() -> listener.onRecvMessageExtensionsChanged(s, list));
    }

    @Override
    public void onRecvMessageExtensionsDeleted(String s, String s1) {
        List<String> list = JsonUtil.toArray(s1, String.class);
        post(() -> listener.onRecvMessageExtensionsDeleted(s, list));
    }

    @Override
    public void onRecvNewMessage(String s) {
        Message msg = JsonUtil.toObj(s, Message.class);
        post(() -> listener.onRecvNewMessage(msg));
    }

    @Override
    public void onRecvOfflineNewMessage(String s) {
        List<Message> list = JsonUtil.toArray(s, Message.class);
        post(() -> listener.onRecvOfflineNewMessage(list));
    }

    public void onRecvOnlineOnlyMessage(String s) {
        post(() -> listener.onRecvOnlineOnlyMessage(s));
    }
}
