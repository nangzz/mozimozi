package com.prograpy.app1.appdev1.network.response;

import com.google.gson.annotations.SerializedName;
import com.prograpy.app1.appdev1.vo.ProductVO;

import java.util.ArrayList;

/**
 * Created by Note on 2018-06-30.
 */

public class SearchResult {

    @SerializedName("success")
    public boolean success = true;

    @SerializedName("categoryProductList")
    public ArrayList<ProductVO> productVOArrayList = new ArrayList<ProductVO>();


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<ProductVO> getProductVOArrayList() {
        return productVOArrayList;
    }

    public void setProductVOArrayList(ArrayList<ProductVO> productVOArrayList) {
        this.productVOArrayList = productVOArrayList;
    }
}
