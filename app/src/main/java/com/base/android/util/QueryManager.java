package com.base.android.util;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.base.android.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class QueryManager {

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .build();

    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static volatile QueryManager instance = null;

    // private constructor
    private QueryManager() {
    }

    public static QueryManager getInstance() {
        if (instance == null) {
            synchronized (QueryManager.class) {
                // Double check
                if (instance == null) {
                    instance = new QueryManager();
                }
            }
        }
        return instance;
    }

    public void postRequest(Activity activity, String json, final CallbackListener callback) {
        if (Utility.isNetworkConnected(activity)) {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url("BASE_URL")
                    .post(body).cacheControl(CacheControl.FORCE_NETWORK)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull final IOException e) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onResult(e, "", null);
                        }
                    });
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    final String result = response.body().string();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Gson gson = new Gson();
                                ResponseManager responseManager = gson.fromJson(result, ResponseManager.class);
                                callback.onResult(null, result, responseManager);
                            } catch (Exception e) {
                                callback.onResult(e, "", null);
                            }

                        }
                    });
                }
            });
        } else {
            Utility.showToast(activity, activity.getResources()
                    .getString(R.string.network_error));
        }

    }

    public void postUGRequest(Activity activity, String json, final CallbackListener callback) {
        if (Utility.isNetworkConnected(activity)) {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url("BASE_URL")
                    .post(body).cacheControl(CacheControl.FORCE_NETWORK)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull final IOException e) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onResult(e, "", null);
                        }
                    });
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    final String result = response.body().string();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Gson gson = new Gson();
                                ResponseManager responseManager = gson.fromJson(result, ResponseManager.class);
                                callback.onResult(null, result, responseManager);
                            } catch (Exception e) {
                                callback.onResult(e, "", null);
                            }

                        }
                    });
                }
            });
        } else {
            Utility.showToast(activity, activity.getResources()
                    .getString(R.string.network_error));
        }
    }
}
