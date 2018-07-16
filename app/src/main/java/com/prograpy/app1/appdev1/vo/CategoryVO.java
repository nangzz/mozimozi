package com.prograpy.app1.appdev1.vo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CategoryVO implements Parcelable{


    @SerializedName("p_cat")
    private String p_cat = "";

    @SerializedName("p_cat_name")
    private String p_cat_name = "";

    public String getP_cat() {
        return p_cat;
    }

    public void setP_cat(String p_cat) {
        this.p_cat = p_cat;
    }

    public String getP_cat_name() {
        return p_cat_name;
    }

    public void setP_cat_name(String p_cat_name) {
        this.p_cat_name = p_cat_name;
    }


    public CategoryVO(){

    }

    protected CategoryVO(Parcel in) {
        p_cat = in.readString();
        p_cat_name = in.readString();
    }

    public static final Creator<CategoryVO> CREATOR = new Creator<CategoryVO>() {
        @Override
        public CategoryVO createFromParcel(Parcel in) {
            return new CategoryVO(in);
        }

        @Override
        public CategoryVO[] newArray(int size) {
            return new CategoryVO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(p_cat);
        dest.writeString(p_cat_name);
    }
}
