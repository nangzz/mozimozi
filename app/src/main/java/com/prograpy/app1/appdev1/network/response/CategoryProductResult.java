package com.prograpy.app1.appdev1.network.response;

import com.google.gson.annotations.SerializedName;
import com.prograpy.app1.appdev1.vo.ProductVO;

import java.util.ArrayList;


public class CategoryProductResult {

    @SerializedName("success")
    public boolean success = true;

    @SerializedName("categoryProductList")
    public ArrayList<ProductVO> categoryList = new ArrayList<ProductVO>();


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

