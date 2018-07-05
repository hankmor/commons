package com.belonk.common.map.baidu.vo;

/**
 * <p>Created by sun on 2016/6/3.
 *
 * @author sunfuchang03@126.comfuchang03@126.com
 * @since 1.0
 */
public class BaiduMapLocate {
    //~ Static fields/initializers =====================================================================================

    //~ Instance fields ================================================================================================

    // 省
    private String province;
    private String city;
    // 区县
    private String district;
    // 街道
    private String street;
    // 门址
    private String street_number;
    // 位置
    private BaiduPoint baiduPoint;
    //~ Methods ========================================================================================================

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet_number() {
        return street_number;
    }

    public void setStreet_number(String street_number) {
        this.street_number = street_number;
    }

    public BaiduPoint getBaiduPoint() {
        return baiduPoint;
    }

    public void setBaiduPoint(BaiduPoint baiduPoint) {
        this.baiduPoint = baiduPoint;
    }
}
