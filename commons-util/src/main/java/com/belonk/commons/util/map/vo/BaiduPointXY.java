package com.belonk.commons.util.map.vo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * <p>Created by sun on 2016/6/3.
 *
 * @author sunfuchang03@126.comfuchang03@126.com
 * @since 1.0
 */
public class BaiduPointXY {
    //~ Static fields/initializers =====================================================================================

    //~ Instance fields ================================================================================================
    // 纬度
    @JSONField(name = "y")
    private Double lat;
    // 进度
    @JSONField(name = "x")
    private Double lng;
    //~ Methods ========================================================================================================

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
