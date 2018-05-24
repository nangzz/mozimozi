package com.prograpy.app1.appdev1.network.response;

import com.google.gson.annotations.SerializedName;
import com.prograpy.app1.appdev1.vo.UserInfoVO;

import java.util.ArrayList;

public class UserInfoResult {

    @SerializedName("success")
    public boolean success = true;

    @SerializedName("message")
    public String message = "";

    @SerializedName("signup")
    public ArrayList<UserInfoVO> UserInfoVOArrayList = new ArrayList<UserInfoVO>();
}
