package io.openim.android.sdk.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneralUtil {

    public static boolean getSwitchFromOptions(Map<String, Boolean> options, String key) {
        if (!options.containsKey(key)) {
            return true;
        }
        //just in case of null
        return options.get(key) == true;
    }

    public static List<Long> differenceSubset(List<Long> mainSlice, List<Long> subSlice) {
        var m = new HashMap<Long, Boolean>();
        var n = new ArrayList<Long>();
        for (var v : subSlice) {
            m.put(v, true);
        }
        for (var v : mainSlice) {
            if (!m.containsKey(v)) {
                n.add(v);
            }
        }
        return n;
    }
}
