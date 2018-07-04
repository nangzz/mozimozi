package com.prograpy.app1.appdev1.network.response;

import com.google.gson.annotations.SerializedName;
import com.prograpy.app1.appdev1.vo.DramaVO;
import com.prograpy.app1.appdev1.vo.ProductVO;

import java.util.ArrayList;


public class CategoryResult {

    @SerializedName("success")
    public boolean success = true;

    @SerializedName("dramacategoryList")
    public ArrayList<ProductVO> categoryList = new ArrayList<ProductVO>();
//
//    @SerializedName("mypageProduct")
//    public ArrayList<DramaVO> mypageProductDid = new ArrayList<DramaVO>();

//리절트 클래스 하나 더 만들기
    //categoryProductList 시리얼라이즈


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<ProductVO> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ArrayList<ProductVO> categoryList) {
        this.categoryList = categoryList;
    }
}

