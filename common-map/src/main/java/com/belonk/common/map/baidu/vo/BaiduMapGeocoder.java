package com.belonk.common.map.baidu.vo;

/**
 * <p>Created by sun on 2016/6/3.
 *
 * @author sunfuchang03@126.comfuchang03@126.com
 * @since 1.0
 */
public class BaiduMapGeocoder {
    //~ Static fields/initializers =====================================================================================

    //~ Instance fields ================================================================================================
    // 位置
    private BaiduPoint baiduPoint;
    // 位置的附加信息，是否精确查找。1为精确查找，即准确打点；0为不精确，即模糊打点。
    private Integer precise;
    // 描述打点准确度
    private Integer confidence;
    // 地址类型
    private String level;
    //~ Methods ========================================================================================================

    public BaiduPoint getBaiduPoint() {
        return baiduPoint;
    }

    public void setBaiduPoint(BaiduPoint baiduPoint) {
        this.baiduPoint = baiduPoint;
    }

    public Integer getPrecise() {
        return precise;
    }

    public void setPrecise(Integer precise) {
        this.precise = precise;
    }

    public Integer getConfidence() {
        return confidence;
    }

    public void setConfidence(Integer confidence) {
        this.confidence = confidence;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
