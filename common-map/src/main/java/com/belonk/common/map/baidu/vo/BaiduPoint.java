package com.belonk.common.map.baidu.vo;

/**
 * <p>Created by sun on 2016/6/3.
 *
 * @author sunfuchang03@126.comfuchang03@126.com
 * @since 1.0
 */
public class BaiduPoint {
    //~ Static fields/initializers =====================================================================================

    //~ Instance fields ================================================================================================
    // 纬度
    private Double lat;
    // 进度
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
