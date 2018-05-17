package com.prograpy.app1.appdev1.network.response;

import com.google.gson.annotations.SerializedName;
import com.prograpy.app1.appdev1.network.response.data.MainDramaDbList;
import com.prograpy.app1.appdev1.network.response.data.MypageDbList;
import com.prograpy.app1.appdev1.network.response.data.ProductDbList;

import java.util.ArrayList;

public class AppResult {

    @SerializedName("success")
    private boolean success = true;

    @SerializedName("mainDramaDbList")
    private ArrayList<MainDramaDbList> mainDramamDbList = null;

    @SerializedName("mypageList")
    private ArrayList<MypageDbList> mypageDbList = null;

    @SerializedName("productDbList")
    private ArrayList<ProductDbList> productDbLists = null;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public ArrayList<MainDramaDbList> getMainDramamDbList() {
        return mainDramamDbList;
    }

    public void setMainDramamDbList(ArrayList<MainDramaDbList> mainDramamDbList) {
        this.mainDramamDbList = mainDramamDbList;
    }

    public ArrayList<MypageDbList> getMypageDbList() {
        return mypageDbList;
    }

    public void setMypageDbList(ArrayList<MypageDbList> mypageDbList) {
        this.mypageDbList = mypageDbList;
    }

    public ArrayList<ProductDbList> getProductDbLists() {
        return productDbLists;
    }

    public void setProductDbLists(ArrayList<ProductDbList> productDbLists) {
        this.productDbLists = productDbLists;
    }
}


