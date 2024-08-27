package io.openim.android.sdk.utils;

import io.openim.android.sdk.database.LocalConversation;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class BatchUtil {

    public static <T, V> List<V> batch(Function<T, V> fn, List<T> ts) {
        if (ts == null) {
            return null; // Return null if the input list is null
        }
        List<V> result = new ArrayList<>(ts.size());
        for (T t : ts) {
            result.add(fn.apply(t)); // Apply the function and add the result to the list
        }
        return result;
    }

//    public static <T, V> List<LocalConversation> batch(Function<T,V> serverConversationToLocal, List<String> conversationIDsList) {
//    }
}
