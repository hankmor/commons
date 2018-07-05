package com.belonk.common.map.baidu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.belonk.common.map.baidu.vo.BaiduMapGeocoder;
import com.belonk.common.map.baidu.vo.BaiduMapLocate;
import com.belonk.common.map.baidu.vo.BaiduMapUnGeocoder;
import com.belonk.common.map.baidu.vo.BaiduPoint;
import com.belonk.commons.util.http.HttpHelper;
import com.belonk.commons.util.http.HttpSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * <p>Created by sun on 2016/6/3.
 *
 * @author sunfuchang03@126.comfuchang03@126.com
 * @version 1.0
 * @since 1.0
 */
public class BaiduMapHelper {
    //~ Static fields/initializers =====================================================================================

    private static final Logger LOG = LoggerFactory.getLogger(BaiduMapHelper.class);

    /**
     * 百度IP定位服务地址.
     * <P>官网："http://api.map.baidu.com/lbsapi/cloud/ip-location-api.htm"
     */
    private static final String BAIDU_LOCATE_URL = "http://api.map.baidu.com/location/ip";
    private static final String BAIDU_GEOCODING_URL = "http://api.map.baidu.com/geocoder/v2/";

    //~ Instance fields ================================================================================================

    //~ Methods ========================================================================================================

    private BaiduMapHelper() {
    }

    /**
     * 根据ip地址获取客户端地址信息
     *
     * @param baiduAkKey 百度akkey
     * @param userIp     客户端ip 不传为当前请求者的ip
     * @return 百度经纬度坐标对象.
     * @throws Exception
     */
    public static BaiduMapLocate locate(String baiduAkKey, String userIp) throws IOException {
        if (baiduAkKey == null || "".equals(baiduAkKey)) return null;
        // coor不出现时，默认为百度墨卡托坐标；coor=bd09ll时，返回为百度经纬度坐标
        LOG.info("locate - Locate using ip address starting.");
        LOG.info("locate - Request ip address : " + userIp);
        String locateUrl = BAIDU_LOCATE_URL + "?ak=" + baiduAkKey + (userIp == null || "".equals(userIp) ? "" :
                "&ip=" + userIp) + "&coor=bd09ll";
        HttpSetting httpSetting = HttpSetting.newInstance();
        httpSetting.setIsGet(true);
        String json = HttpHelper.request(locateUrl, httpSetting);
        JSONObject jsonObject = JSON.parseObject(json);
        Integer status = jsonObject.getInteger("status");

        if (status != 0) {
            LOG.error("locate - Locate failed，error code is :" + status);
            return null;
        }
        LOG.info("locate - Locate using ip address end.");
        BaiduMapLocate baiduMapLocate = new BaiduMapLocate();
        String province = "";
        String city = "";
        String district = "";
        String street = "";
        String street_number = "";
        JSONObject addrObject = jsonObject.getJSONObject("content");
        if (addrObject != null) {
            JSONObject detailObject = addrObject.getJSONObject("address_detail");
            if (detailObject != null) {
                province = detailObject.getString("province");
                city = detailObject.getString("city");
                district = detailObject.getString("district");
                street = detailObject.getString("street");
                street_number = detailObject.getString("street_number");
            }
            JSONObject pointObject = addrObject.getJSONObject("point");
            if (pointObject != null) {
                BaiduPoint baiduPoint = new BaiduPoint();
                baiduPoint.setLat(Double.valueOf(pointObject.getString("y")));
                baiduPoint.setLng(Double.valueOf(pointObject.getString("x")));
                baiduMapLocate.setBaiduPoint(baiduPoint);
            }
            baiduMapLocate.setProvince(province);
            baiduMapLocate.setCity(city);
            baiduMapLocate.setDistrict(district);
            baiduMapLocate.setStreet(street);
            baiduMapLocate.setStreet_number(street_number);
        }
        return baiduMapLocate;
    }

    /**
     * 根据具体街道位置在城市中查找，获取经纬度信息。
     *
     * @param baiduAkKey 百度api调用key
     * @param address    街道地址
     * @param city       搜索城市
     * @return 百度经纬度坐标对象
     * @throws IOException
     */
    public static BaiduMapGeocoder geocoder(String baiduAkKey, String address, String city) throws IOException {
        if (baiduAkKey == null || "".equals(baiduAkKey)) return null;
        if (address == null || "".equals(address)) return null;
        LOG.info("geocoder - geocoder starting.");
        String generatorUrl = BAIDU_GEOCODING_URL + "?ak=" + baiduAkKey + "&output=json&address=" + address +
                (city == null || "".equals(city) ? "" : "&city=" + city);
        HttpSetting httpSetting = HttpSetting.newInstance();
        httpSetting.setIsGet(true);
        String json = HttpHelper.request(generatorUrl, httpSetting);
        JSONObject jsonObject = JSON.parseObject(json);
        Integer status = jsonObject.getInteger("status");

        if (status != 0) {
            LOG.error("geocoder - geocoder failed，error code is :" + status);
            return null;
        }
        LOG.info("geocoder - geocoder end.");
        JSONObject resultObject = jsonObject.getJSONObject("result");
        if (resultObject != null) {
            BaiduMapGeocoder baiduMapGercoder = new BaiduMapGeocoder();
            JSONObject locationObject = resultObject.getJSONObject("location");
            if (locationObject != null) {
                BaiduPoint baiduPoint = new BaiduPoint();
                baiduPoint.setLat(Double.valueOf(locationObject.getString("lat")));
                baiduPoint.setLng(Double.valueOf(locationObject.getString("lng")));
                baiduMapGercoder.setBaiduPoint(baiduPoint);
            }
            Integer precise = resultObject.getInteger("precise");
            Integer confidence = resultObject.getInteger("confidence");
            String level = resultObject.getString("level");
            baiduMapGercoder.setPrecise(precise);
            baiduMapGercoder.setConfidence(confidence);
            baiduMapGercoder.setLevel(level);
            return baiduMapGercoder;
        }
        return null;
    }

    /**
     * 根据经纬度信息，获取具体街道位置在城市中查找。
     *
     * @param baiduAkKey 百度api调用key
     * @param lat        百度纬度
     * @param lng        百度经度
     * @param pois       是否显示周边poi，true - 显示周边100米内的poi，false - 不显示
     * @return 结果对象。
     * @throws IOException
     */
    public static BaiduMapUnGeocoder ungeocoder(String baiduAkKey, Double lat, Double lng, boolean pois) throws IOException {
        if (baiduAkKey == null || "".equals(baiduAkKey)) return null;
        if (lat == null || lng == null) return null;
        LOG.info("ungeocoder - ungeocoder starting.");
        String generatorUrl = BAIDU_GEOCODING_URL + "?ak=" + baiduAkKey + "&output=json&location=" + lat + "," + lng + "&output=json&pois=" + (pois ? 1 : 0);
        HttpSetting httpSetting = HttpSetting.newInstance();
        httpSetting.setIsGet(true);
        String json = HttpHelper.request(generatorUrl, httpSetting);
        JSONObject jsonObject = JSON.parseObject(json);
        Integer status = jsonObject.getInteger("status");

        if (status != 0) {
            LOG.error("ungeocoder - ungeocoder failed，error code is :" + status);
            return null;
        }
        LOG.info("ungeocoder - ungeocoder end.");
        String result = jsonObject.getString("result");
        BaiduMapUnGeocoder baiduMapUnGeocoder = JSON.parseObject(result, BaiduMapUnGeocoder.class);
        return baiduMapUnGeocoder;
    }

    public static void main(String[] args) throws Exception {
        BaiduMapLocate baiduMapLocate = BaiduMapHelper.locate("A8c959bd9c7da03a8e93791b0ebebf99", "");
        System.out.println(baiduMapLocate);
        // lat = {Double@918} "30.652033016361546"
//        lng = {Double@919} "104.06694294756872"
        BaiduMapGeocoder baiduMapGercoder = BaiduMapHelper.geocoder("A8c959bd9c7da03a8e93791b0ebebf99", "武侯区黉门街79号", "成都");
        System.out.println(baiduMapGercoder);
        BaiduMapUnGeocoder baiduMapUnGeocoder = BaiduMapHelper.ungeocoder("A8c959bd9c7da03a8e93791b0ebebf99", baiduMapLocate.getBaiduPoint().getLat(), baiduMapLocate.getBaiduPoint().getLng(), true);
        System.out.println(baiduMapUnGeocoder);
    }
}
