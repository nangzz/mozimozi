package com.prograpy.app1.appdev1.network.response;

import com.google.gson.annotations.SerializedName;

public class ServerSuccessCheckResult {

    @SerializedName("success")
    public boolean success = false;

    @SerializedName("message")
    public String message = "";

}
