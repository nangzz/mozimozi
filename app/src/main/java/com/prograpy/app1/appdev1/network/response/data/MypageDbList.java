package com.prograpy.app1.appdev1.network.response.data;


import com.google.gson.annotations.SerializedName;
import com.prograpy.app1.appdev1.vo.ProductVO;

import java.util.ArrayList;

public class MypageDbList {

    @SerializedName("mypageProduct")
    ArrayList<ProductVO> mypageProductList = new ArrayList<ProductVO>();
}
