package io.openim.android.sdk.utils;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import io.openim.android.sdk.models.NotificationElem;
import io.openim.android.sdk.protos.sdkws.MarkAsReadTips;
import java.lang.reflect.Type;

import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtil {

    private static final Gson gson = new GsonBuilder().registerTypeAdapter(byte[].class, new ByteArrayToBase64TypeAdapter()).create();

    private static class ByteArrayToBase64TypeAdapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {

        public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return Base64.decode(json.getAsString(), Base64.NO_WRAP);
        }

        public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(Base64.encodeToString(src, Base64.NO_WRAP));
        }
    }

    /**
     * 将object对象转成json字符串
     */
    public static String toString(Object object) {
        String gsonString = "";
        try {
            if (null != object) {
                gsonString = gson.toJson(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gsonString;
    }


    /**
     * 将gsonString转成泛型bean
     */
    public static <T> T toObj(String gsonString, Class<T> cls) {
        T t = null;
        try {
            t = cls.newInstance();
            if (null != gsonString) {
                t = gson.fromJson(gsonString, cls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    //MarkAsReadTips.class, DeleteMsgsTips.class, ClearConversationTips.class, ConversationUpdateTips.class
    public static <T> T parseNotificationElem(String json, Class<T> type) throws JsonSyntaxException {

        NotificationElem elem = toObj(json, NotificationElem.class);
        if (elem == null || elem.getDetail() == null) {
            throw new JsonSyntaxException("Notification elem detail is missing");
        }
        return toObj(elem.getDetail(), type);
    }

    /**
     * 转成list
     * 泛型在编译期类型被擦除导致报错
     * @param gsonString
     * @param cls
     * @return
     */
//    public static <T> List<T> GsonToList(String gsonString, Class<T> cls) {
//        List<T> list = null;
//        if (gson != null) {
//            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
//            }.getType());
//        }
//        return list;
//    }

    /**
     * 转成list 解决泛型在编译期类型被擦除导致报错
     */
    public static <T> List<T> toArray(String json, Class<T> cls) {
        List<T> list = null;
        try {
            if (null != json) {
                list = new ArrayList<T>();
                JsonArray array = JsonParser.parseString(json).getAsJsonArray();
                for (final JsonElement elem : array) {
                    list.add(gson.fromJson(elem, cls));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 转成list中有map的
     */
    public static <T> List<Map<String, T>> toListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        if (null != gsonString) {
            list = gson.fromJson(gsonString,
                new TypeToken<List<Map<String, T>>>() {
                }.getType());
        }
        return list;
    }


    /**
     * 转成map的
     */
    public static <T> Map<String, T> toMaps(String gsonString) {
        Map<String, T> map = null;
        if (null != gsonString) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }
}
