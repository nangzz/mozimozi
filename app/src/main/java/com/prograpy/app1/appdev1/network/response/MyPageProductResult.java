package com.prograpy.app1.appdev1.network.response;

import com.google.gson.annotations.SerializedName;
import com.prograpy.app1.appdev1.vo.ProductVO;

import java.util.ArrayList;

public class MyPageProductResult {


    @SerializedName("success")
    public boolean success = true;

    @SerializedName("mypageProduct")
    public ArrayList<ProductVO> mypageProductList = new ArrayList<ProductVO>();


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<ProductVO> getMypageProductList() {
        return mypageProductList;
    }

    public void setMypageProductList(ArrayList<ProductVO> mypageProductList) {
        this.mypageProductList = mypageProductList;
    }
}
