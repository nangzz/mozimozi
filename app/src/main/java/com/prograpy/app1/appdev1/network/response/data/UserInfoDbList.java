package com.prograpy.app1.appdev1.network.response.data;


import com.google.gson.annotations.SerializedName;
import com.prograpy.app1.appdev1.vo.DramaVO;
import com.prograpy.app1.appdev1.vo.ProductVO;
import com.prograpy.app1.appdev1.vo.UserInfoVO;

import java.util.ArrayList;

public class UserInfoDbList {

    @SerializedName("drama")
    ArrayList<UserInfoVO> userInfoList = new ArrayList<UserInfoVO>();

}
