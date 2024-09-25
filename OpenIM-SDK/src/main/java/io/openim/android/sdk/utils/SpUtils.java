package io.openim.android.sdk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import io.openim.android.sdk.OpenIMClient;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SpUtils {

    public static final String SPKEY_TOKEN = "token";
    public static final String SPKEY_UID = "uid";
    private static final String FILE_NAME = "construct_openim";

    private static SharedPreferences getSP() {
        return OpenIMClient.getInstance().app.getApplicationContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static void putToken(String token) {
        put(SPKEY_TOKEN, token);
    }

    public static void putUid(String uid) {
        put(SPKEY_UID, uid);
    }

    public static void put(String key, String value) {
        if (TextUtils.isEmpty(value)) {
            remove(key);
            return;
        }
        SharedPreferences.Editor editor = getSP().edit();
        editor.putString(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    // save 操作 start
    public static void put(String keyId, Long value) {
        if (value == null) {
            remove(keyId);
            return;
        }
        SharedPreferences.Editor editor = getSP().edit();
        editor.putLong(keyId, value);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 移除某个key值已经对应的值
     */
    public static void remove(String key) {
        SharedPreferences.Editor editor = getSP().edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private static class SharedPreferencesCompat {

        private static final Method sApplyMethod = findApplyMethod();

        // 反射查找apply的方法
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }

        // 如果找到则使用apply执行，否则使用commit
        static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            editor.commit();
        }
    }
}
