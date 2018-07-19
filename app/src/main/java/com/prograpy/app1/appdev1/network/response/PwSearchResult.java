package com.prograpy.app1.appdev1.network.response;

import com.google.gson.annotations.SerializedName;
import com.prograpy.app1.appdev1.vo.IdPwSearchVo;

import java.util.ArrayList;

/**
 * Created by Note on 2018-07-19.
 */

public class PwSearchResult {

    @SerializedName("success")
    public boolean success = true;

    @SerializedName("idpwList")
    public ArrayList<IdPwSearchVo> PwInfoDataVo = new ArrayList<IdPwSearchVo>();



    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<IdPwSearchVo> getPwInfoDataVo() {
        return PwInfoDataVo;
    }

    public void setPwInfoDataVo(ArrayList<IdPwSearchVo> IdInfoVOArrayList) {
        this.PwInfoDataVo = PwInfoDataVo;
    }

}
