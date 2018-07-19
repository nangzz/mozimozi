package com.prograpy.app1.appdev1.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Note on 2018-07-19.
 */

public class IdPwSearchVo {


    @SerializedName("user_id")
    public String user_id = "";

    @SerializedName("user_pw")
    public String user_pw = "";


    public String getUser_pw() {
        return user_pw;
    }

    public void setUser_pw(String user_pw) {
        this.user_pw = user_pw;
    }



    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
