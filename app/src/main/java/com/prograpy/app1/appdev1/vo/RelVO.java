package com.prograpy.app1.appdev1.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 민아 on 2018-05-10.
 */

public class RelVO {

    @SerializedName("relative_product")
    public int p_rel = 0;

    public int getP_rel() {
        return p_rel;
    }

    public void setP_rel(int p_rel) {
        this.p_rel = p_rel;
    }
}
