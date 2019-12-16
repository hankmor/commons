package com.belonk.commons.util.location;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.belonk.commons.util.http.HttpHelper;
import com.belonk.commons.util.http.HttpSetting;
import com.belonk.commons.util.ip.IpHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 获取地址类
 *
 * @author sun
 * @since 1.2
 */
public class LocationUtil {
    private static final Logger log = LoggerFactory.getLogger(LocationUtil.class);

    /**
     * 淘宝IP地址库
     */
    private static final String IP_URL = "http://ip.taobao.com/service/getIpInfo.php";

    /**
     * 根据ip地址查询用户的地理位置。
     *
     * @param ip ip地址
     * @return 地理位置，格式xx xx，例如：四川 成都
     */
    public static String queryLocationByIp(String ip) {
        String address = "XX XX";

        // 内网不查询
        if (IpHelper.isInternalIp(ip)) {
            return "内网IP";
        }
        HttpSetting setting = HttpSetting.newInstance();
        String result = null;
        try {
            result = HttpHelper.request(IP_URL + "?ip=" + ip, setting);
        } catch (IOException e) {
            log.error("Query location exception : ", e);
            return address;
        }
        if (StringUtils.isEmpty(result)) {
            log.error("Query location exception : " + ip);
            return address;
        }
        JSONObject obj;
        try {
            obj = JSON.parseObject(result, JSONObject.class);
            JSONObject data = obj.getJSONObject("data");
            String region = data.getString("region");
            String city = data.getString("city");
            address = region + " " + city;
        } catch (Exception e) {
            log.error("Query location exception : " + ip);
        }
        return address;
    }

    public static void main(String[] args) {
        String ip = "182.150.186.101";
        System.out.println(LocationUtil.queryLocationByIp(ip));
    }
}
