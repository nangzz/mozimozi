package com.prograpy.app1.appdev1.vo;

import com.google.gson.annotations.SerializedName;

public class ProductVO {

    @SerializedName("p_url")
    private String p_url = "";
    @SerializedName("p_id")
    private int p_id = 0;
    @SerializedName("p_name")
    private String p_name = "";
    @SerializedName("p_img")
    private String p_img = "";
    @SerializedName("p_price")
    private int p_price = 0;
    @SerializedName("p_cat")
    private String p_cat = "";
    @SerializedName("p_brand")
    private String p_brand = "";
    @SerializedName("clickNum")
    private int clickNum = 0;
    @SerializedName("p_act")
    private String p_act = "";


    public String getP_act() {
        return p_act;
    }

    public void setP_act(String p_act) {
        this.p_act = p_act;
    }

    public String getP_url() {
        return p_url;
    }

    public void setP_url(String p_url) {
        this.p_url = p_url;
    }

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

    public int getP_price() {
        return p_price;
    }

    public void setP_price(int p_price) {
        this.p_price = p_price;
    }

    public String getP_cat() {
        return p_cat;
    }

    public void setP_cat(String p_cat) {
        this.p_cat = p_cat;
    }

    public String getP_brand() {
        return p_brand;
    }

    public void setP_brand(String p_brand) {
        this.p_brand = p_brand;
    }

    public int getClickNum() {
        return clickNum;
    }

    public void setClickNum(int clickNum) {
        this.clickNum = clickNum;
    }
}
