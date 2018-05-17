package com.prograpy.app1.appdev1.network.response;

import com.google.gson.annotations.SerializedName;
import com.prograpy.app1.appdev1.network.response.data.MainDramaDbList;
import com.prograpy.app1.appdev1.vo.DramaVO;

import java.util.ArrayList;

public class MainDramaResult {

    @SerializedName("success")
    public boolean success = true;

    @SerializedName("dramaList")
    public ArrayList<DramaVO> dramaVOArrayList = new ArrayList<DramaVO>();
}
