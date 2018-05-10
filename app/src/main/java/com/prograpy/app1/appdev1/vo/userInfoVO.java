package com.prograpy.app1.appdev1.vo;

/**
 * Created by 민아 on 2018-05-10.
 */

public class userInfoVO {

//    @SerializedName("login")
    public String user_id = "";
    public String user_pw = "";
    public String user_name = "";
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
