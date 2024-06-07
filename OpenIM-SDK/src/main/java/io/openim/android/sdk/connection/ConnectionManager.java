package io.openim.android.sdk.connection;

import io.openim.android.sdk.listener.OnConnListener;
import io.openim.android.sdk.utils.AsyncUtils;
import io.openim.android.sdk.utils.RunnableWrapper;
import java.net.URISyntaxException;
import java.util.List;
import kotlin.collections.ArrayDeque;

public class ConnectionManager implements OnConnListener {

    private final Connection connection;
    public List<OnConnListener> connectionListeners = new ArrayDeque<>();

    public static ConnectionManager getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {

        static ConnectionManager instance = new ConnectionManager();
    }

    private ConnectionManager() {
        try {
            connection = new Connection(this);
        } catch (URISyntaxException e) {
            onConnectFailed(-1, e.toString());
            throw new RuntimeException(e);
        }
    }

    public void disConnect() {
        connection.disconnect();
    }

    public void connect() {
        connection.connectToServer();
    }

    @Override
    public void onConnectFailed(long code, String error) {

    }

    @Override
    public void onConnectSuccess() {
        AsyncUtils.runOnUiThread(new RunnableWrapper(() -> {
            for (OnConnListener connectionListener : connectionListeners) {
                if (connectionListener != null) {
                    connectionListener.onConnectSuccess();
                }
            }
        }));
    }

    public void addConnectionListener(OnConnListener connectionListener) {
        this.connectionListeners.add(connectionListener);
    }

    public void removeConnectionListener(OnConnListener connectionListener) {
        this.connectionListeners.remove(connectionListener);
    }

}
