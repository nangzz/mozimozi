package com.prograpy.app1.appdev1.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 민아 on 2018-05-10.
 */

public class UserInfoVO {

    @SerializedName("userid")
    public String user_id = "";
    @SerializedName("password")
    public String user_pw = "";
    @SerializedName("username")
    public String user_name = "";
    @SerializedName("usermail")
    public String user_email = "";

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_pw() {
        return user_pw;
    }

    public void setUser_pw(String user_pw) {
        this.user_pw = user_pw;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
}
