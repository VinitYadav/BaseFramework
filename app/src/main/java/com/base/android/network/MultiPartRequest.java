package com.base.android.network;

import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MultiPartRequest {

    private String UG_POST_URL = "";
    private String UG_GROUP_POST_URL = "";
    private String UG_REGISTRATION_URL = "";
    private String UG_EDIT_POST_URL = "";

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(4, TimeUnit.MINUTES)
            .writeTimeout(4, TimeUnit.MINUTES)
            .readTimeout(4, TimeUnit.MINUTES)
            .build();

    /**
     * Send
     */
    public void send(final Callback callback) {
        try {
            MultipartBody.Builder builder = new MultipartBody.Builder();

            builder.setType(MultipartBody.FORM);
            builder.addFormDataPart("user_id", "");
            builder.addFormDataPart("name", "");

            ArrayList<String> list = new ArrayList<>();

            if (list != null && list.size() > 0) {
                if (list.size() == 1) {
                    builder.addFormDataPart("view_type", "0");
                } else {
                    builder.addFormDataPart("view_type", "1");
                }
            } else {
                builder.addFormDataPart("view_type", "0");
            }

            String videoPath = "";
            if (TextUtils.isEmpty(videoPath)) {
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        String path = "filePath";
                        String temp = path.replaceAll("file:\\//", "");
                        String ext = getFileExtension(temp);
                        String keyName = "post_photos[" + i + "]";
                        String fileName = "image" + i + "." + ext;
                        File fileTemp = new File(temp);
                        MediaType MEDIA_TYPE = MediaType.parse("application/" + ext);
                        builder.addFormDataPart(keyName, fileName,
                                RequestBody.create(MEDIA_TYPE, fileTemp));
                    }
                }
            } else {
                if (!TextUtils.isEmpty("")) {
                    if (list != null && list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            String path = "filePath";
                            String temp = path.replaceAll("file:\\//", "");
                            String ext = getFileExtension(temp);
                            String keyName = "post_videos[" + i + "]";
                            String fileName = "video" + i + "." + ext;
                            File fileTemp = new File(temp);
                            MediaType MEDIA_TYPE = MediaType.parse("application/" + ext);
                            builder.addFormDataPart(keyName, fileName, RequestBody.create(MEDIA_TYPE, fileTemp));
                        }
                    }
                }
            }

            RequestBody requestBody = builder.build();

            Request request = new Request.Builder()
                    .url(UG_POST_URL)
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get file extension
     *
     * @param filePath Selected file path
     * @return Return file extension
     */
    private String getFileExtension(String filePath) {
        String filenameArray[] = filePath.split("\\.");
        return filenameArray[filenameArray.length - 1];
    }
}