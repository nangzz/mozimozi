package com.prograpy.app1.appdev1.vo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 민아 on 2018-05-10.
 */

public class dramaVO {

    @SerializedName("drama")
    public int d_id = 0;
    public String channel = "";
    public String d_name = "";
    public String d_img = "";
    public String d_act = "";

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }

    public String getD_img() {
        return d_img;
    }

    public void setD_img(String d_img) {
        this.d_img = d_img;
    }

    public String getD_act() {
        return d_act;
    }

    public void setD_act(String d_act) {
        this.d_act = d_act;
    }

    public int getD_id() {

        return d_id;
    }

    public void setD_id(int d_id) {
        this.d_id = d_id;
    }
}
