package com.prograpy.app1.appdev1.network.response;

import com.google.gson.annotations.SerializedName;
import com.prograpy.app1.appdev1.network.response.data.MainDramaDbList;
import com.prograpy.app1.appdev1.vo.DramaVO;

import java.util.ArrayList;

public class DramaListResult {

    @SerializedName("success")
    private boolean success = true;

    @SerializedName("dramaList")
    private ArrayList<DramaVO> dramaVOArrayList = new ArrayList<DramaVO>();


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<DramaVO> getDramaVOArrayList() {
        return dramaVOArrayList;
    }

    public void setDramaVOArrayList(ArrayList<DramaVO> dramaVOArrayList) {
        this.dramaVOArrayList = dramaVOArrayList;
    }
}
