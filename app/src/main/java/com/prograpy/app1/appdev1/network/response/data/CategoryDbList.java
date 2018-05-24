package com.prograpy.app1.appdev1.network.response.data;

import com.google.gson.annotations.SerializedName;
import com.prograpy.app1.appdev1.vo.DramaVO;
import com.prograpy.app1.appdev1.vo.ProductVO;

import java.util.ArrayList;

public class CategoryDbList {

    @SerializedName("drama")
    ArrayList<DramaVO> mainDramaList = new ArrayList<DramaVO>();

    @SerializedName("dramacategoryList")
    ArrayList<ProductVO> dramacategoryList = new ArrayList<ProductVO>();
}