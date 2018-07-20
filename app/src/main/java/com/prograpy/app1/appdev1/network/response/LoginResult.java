package com.prograpy.app1.appdev1.network.response;

import com.google.gson.annotations.SerializedName;
import com.prograpy.app1.appdev1.vo.LoginVO;

import java.util.ArrayList;

public class LoginResult {


    @SerializedName("success")
    public boolean success = false;

    @SerializedName("userInfo")
    private ArrayList<LoginVO> userInfo = new ArrayList<LoginVO>();


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<LoginVO> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ArrayList<LoginVO> userInfo) {
        this.userInfo = userInfo;
    }
}
