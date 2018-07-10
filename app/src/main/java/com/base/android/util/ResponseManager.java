package com.base.android.util;

import com.google.gson.annotations.SerializedName;

public class ResponseManager {
    @SerializedName("responseCode")
    private String responseCode = "";

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }
}
