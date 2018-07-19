package com.prograpy.app1.appdev1.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Note on 2018-07-19.
 */

public class IdSearchVo {


    @SerializedName("user_id")
    public String user_id = "";


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

}