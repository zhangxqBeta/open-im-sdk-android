package io.openim.android.sdk.http;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.openim.android.sdk.config.IMConfig;
import io.openim.android.sdk.generics.ReturnWithErr;
import io.openim.android.sdk.utils.ParamsUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiClient {

    private static final OkHttpClient httpClient = new OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .build();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final Gson gson = new Gson();

    public static class ApiResponse {

        public int errCode;
        public String errMsg;
        public String errDlt;
        public JsonObject data;
    }

    public static <T> ReturnWithErr<T> apiPost(String api, String jsonReq, Class<T> respClass) {
        RequestBody body = RequestBody.create(jsonReq, JSON);
        var reqUrl = IMConfig.getInstance().apiAddr + api;
        Request request = new Request.Builder()
            .url(reqUrl)
            .post(body)
            .addHeader("operationID", ParamsUtil.buildOperationID())
            .addHeader("token", IMConfig.getInstance().token)
            .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return new ReturnWithErr<T>(new IOException("Unexpected code " + response));
            }

            String responseBody = response.body().string();
            ApiResponse apiResponse = gson.fromJson(responseBody, ApiResponse.class);

            if (apiResponse.errCode != 0) {
                return new ReturnWithErr<T>(new Exception("API error: " + apiResponse.errMsg));
            }

            if (respClass == null) {
                return new ReturnWithErr<>(null, null);
            } else {
                // Deserialize the data into the expected response object type
                return new ReturnWithErr<T>(gson.fromJson(apiResponse.data, respClass));
            }
        } catch (IOException e) {
            return new ReturnWithErr<T>(e);
        }
    }

    public static <T> ReturnWithErr<T> callApi(String api, String jsonReq, Class<T> respClass) {
        return apiPost(api, jsonReq, respClass);  // Reusing the apiPost method
    }
}
