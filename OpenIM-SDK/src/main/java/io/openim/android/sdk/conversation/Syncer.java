package io.openim.android.sdk.conversation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Syncer<T, V> {

    public static final int UnchangedState = 0;
    public static final int InsertState = 1;
    public static final int UpdateState = 2;
    public static final int DeleteState = 3;

    private Function<T, Exception> insert;
    private Function<T, Exception> delete;
    private BiFunction<T, T, Exception> update;
    private Function<T, V> uuid;
    private BiFunction<T, T, Boolean> equal;
    private TriFunction<Integer, T, T, Exception> notice;
    private String ts;

    // Constructors
    public Syncer(
        Function<T, Exception> insert,
        Function<T, Exception> delete,
        BiFunction<T, T, Exception> update,
        Function<T, V> uuid,
        BiFunction<T, T, Boolean> equal,
        TriFunction<Integer, T, T, Exception> notice
    ) {
        this.insert = insert;
        this.update = update;
        this.delete = delete;
        this.notice = notice;
        this.equal = equal;
        this.uuid = uuid;
        // Determine the type of T and remove pointer indirection if necessary.
        this.ts = "T";
    }

    // Helper method to check equality
    public boolean eq(T server, T local) {
        if (this.equal != null) {
            return this.equal.apply(server, local);
        }
        return Objects.equals(server, local);
    }

    // Helper method to handle notifications
    public Exception onNotice(int state, T server, T local, TriFunction<Integer, T, T, Exception> fn) {
        if (this.notice != null) {
            Exception ex = this.notice.apply(state, server, local);
            if (ex != null) {
                return ex;
            }
        }
        if (fn != null) {
            return fn.apply(state, server, local);
        }
        return null;
    }

    // Main synchronization method
    public Exception sync(List<T> serverData, List<T> localData, TriFunction<Integer, T, T, Exception> notice, boolean... noDel) {
        // Early exit if both datasets are empty
        if (serverData.isEmpty() && localData.isEmpty()) {
            return null;
        }

        // Create a map of local data
        Map<V, T> localMap = new HashMap<>();
        for (T item : localData) {
            localMap.put(uuid.apply(item), item);
        }

        // Synchronize server data with local data
        for (T server : serverData) {
            V id = uuid.apply(server);
            T local = localMap.remove(id); // Attempt to remove from map to find matching local item

            if (local == null) {
                Exception ex = insert.apply(server);
                if (ex != null) {
                    return ex;
                }
                ex = onNotice(1, server, local, notice);
                if (ex != null) {
                    return ex;
                }
                continue;
            }

            if (!eq(server, local)) {
                Exception ex = update.apply(server, local);
                if (ex != null) {
                    return ex;
                }
                ex = onNotice(2, server, local, notice);
                if (ex != null) {
                    return ex;
                }
            } else {
                Exception ex = onNotice(0, server, local, notice);
                if (ex != null) {
                    return ex;
                }
            }
        }

        // Handle deletions if noDel flag is not set
        if (noDel.length == 0 || !noDel[0]) {
            for (T local : localMap.values()) {
                Exception ex = delete.apply(local);
                if (ex != null) {
                    return ex;
                }
                ex = onNotice(3, null, local, notice);
                if (ex != null) {
                    return ex;
                }
            }
        }

        return null;
    }

    // Interfaces for function types used in Syncer
    @FunctionalInterface
    public interface Function<X, Z> {

        Z apply(X x);
    }

    @FunctionalInterface
    public interface BiFunction<X, Y, Z> {

        Z apply(X x, Y y);
    }

    @FunctionalInterface
    public interface TriFunction<X, Y, Z, R> {

        R apply(X x, Y y, Z z);
    }
}
