package com.prograpy.app1.appdev1.network.response;

import com.google.gson.annotations.SerializedName;

public class ServerSuccessCheckResult {

    @SerializedName("success")
    public boolean success = false;

    @SerializedName("message")
    public String message = "";

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
