package com.prograpy.app1.appdev1.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 민아 on 2018-05-10.
 */

public class productVO {

    @SerializedName("product")
    public int p_id = 0;
    public String p_name = "";
    public String p_img = "";
    public String p_cat = "";
    public int p_price = 0;

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_img() {
        return p_img;
    }

    public void setP_img(String p_img) {
        this.p_img = p_img;
    }

    public String getP_cat() {
        return p_cat;
    }

    public void setP_cat(String p_cat) {
        this.p_cat = p_cat;
    }

    public int getP_price() {
        return p_price;
    }

    public void setP_price(int p_price) {
        this.p_price = p_price;
    }
}
