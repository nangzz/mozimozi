package com.prograpy.app1.appdev1.network.response;

import com.google.gson.annotations.SerializedName;
import com.prograpy.app1.appdev1.vo.CategoryVO;
import com.prograpy.app1.appdev1.vo.ProductVO;

import java.util.ArrayList;


public class CategoryResult {

    @SerializedName("success")
    private boolean success = true;

    @SerializedName("dramacategoryList")
    private ArrayList<CategoryVO> categoryList = new ArrayList<CategoryVO>();

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<CategoryVO> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ArrayList<CategoryVO> categoryList) {
        this.categoryList = categoryList;
    }
}

