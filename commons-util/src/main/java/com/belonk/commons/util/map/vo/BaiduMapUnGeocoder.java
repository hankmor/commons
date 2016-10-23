package com.belonk.commons.util.map.vo;

import java.util.List;

/**
 * <p>Created by sun on 2016/6/3.
 *
 * @author sunfuchang03@126.comfuchang03@126.com
 * @since 1.0
 */
public class BaiduMapUnGeocoder {
    //~ Static fields/initializers =====================================================================================

    //~ Instance fields ================================================================================================
    // 位置
    private BaiduPointXY location;
    private String formatted_address; //结构化地址信息
    private String business; //所在商圈信息，如 "人民大学,中关村,苏州街"
    private AddressComponent addressComponent; // 地址信息
    private List<Poi> pois; // 周边poi列表
    private String sematic_description; //当前位置结合POI的语义化结果描述。
    //~ Methods ========================================================================================================

    public BaiduPointXY getLocation() {
        return location;
    }

    public void setLocation(BaiduPointXY location) {
        this.location = location;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public AddressComponent getAddressComponent() {
        return addressComponent;
    }

    public void setAddressComponent(AddressComponent addressComponent) {
        this.addressComponent = addressComponent;
    }

    public List<Poi> getPois() {
        return pois;
    }

    public void setPois(List<Poi> pois) {
        this.pois = pois;
    }

    public String getSematic_description() {
        return sematic_description;
    }

    public void setSematic_description(String sematic_description) {
        this.sematic_description = sematic_description;
    }
}
