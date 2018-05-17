package com.prograpy.app1.appdev1.network.response.data;

import com.google.gson.annotations.SerializedName;
import com.prograpy.app1.appdev1.vo.DramaVO;
import com.prograpy.app1.appdev1.vo.ProductVO;

import java.util.ArrayList;

public class MainDramaDbList {

    @SerializedName("drama")
    ArrayList<DramaVO> mainDramaList = new ArrayList<DramaVO>();

    @SerializedName("drama")
    ArrayList<ProductVO> mainDramaList2 = new ArrayList<ProductVO>();
}
