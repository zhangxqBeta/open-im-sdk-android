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

    private boolean isBackground;


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

    public boolean isBackground() {
        return isBackground;
    }

    public void setBackground(boolean background) {
        isBackground = background;
    }

    public void connect() {
        connection.connectToServer();
    }

    public boolean isConnected() {
        return connection.isConnected();
    }

    @Override
    public void onKickedOffline() {
        AsyncUtils.runOnUiThread(new RunnableWrapper(() -> {
            for (OnConnListener connectionListener : connectionListeners) {
                if (connectionListener != null) {
                    connectionListener.onKickedOffline();
                }
            }
        }));
    }

    /**
     * 登录票据已经过期 请使用新签发的 UserSig 进行登录。
     */
    @Override
    public void onUserTokenExpired() {
        AsyncUtils.runOnUiThread(new RunnableWrapper(() -> {
            for (OnConnListener connectionListener : connectionListeners) {
                if (connectionListener != null) {
                    connectionListener.onUserTokenExpired();
                }
            }
        }));
    }


    @Override
    public void onConnecting() {
        AsyncUtils.runOnUiThread(new RunnableWrapper(() -> {
            for (OnConnListener connectionListener : connectionListeners) {
                if (connectionListener != null) {
                    connectionListener.onConnecting();
                }
            }
        }));
    }

    @Override
    public void onConnectFailed(long code, String error) {
        AsyncUtils.runOnUiThread(new RunnableWrapper(() -> {
            for (OnConnListener connectionListener : connectionListeners) {
                if (connectionListener != null) {
                    connectionListener.onConnectFailed(code, error);
                }
            }
        }));
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

    public Connection getConnection() {
        return connection;
    }

    public void addConnectionListener(OnConnListener connectionListener) {
        this.connectionListeners.add(connectionListener);
    }

    public void removeConnectionListener(OnConnListener connectionListener) {
        this.connectionListeners.remove(connectionListener);
    }

}
