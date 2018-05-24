package com.prograpy.app1.appdev1.network.response;

import com.google.gson.annotations.SerializedName;
import com.prograpy.app1.appdev1.vo.ProductVO;

import java.util.ArrayList;


public class CategoryResult {

    @SerializedName("success")
    public boolean success = true;

    @SerializedName("dramacategoryList")
    public ArrayList<ProductVO> categoryList = new ArrayList<ProductVO>();

    @SerializedName("mypageProductList")
    public ArrayList<ProductVO> mypageProductList = new ArrayList<ProductVO>();


}

