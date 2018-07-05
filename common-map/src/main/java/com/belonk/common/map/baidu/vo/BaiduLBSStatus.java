package com.belonk.common.map.baidu.vo;

/**
 * <p>Created by sun on 2016/6/3.
 *
 * @author sunfuchang03@126.comfuchang03@126.com
 * @since 1.0
 */
public class BaiduLBSStatus {
    //~ Static fields/initializers =====================================================================================

    //~ Instance fields ================================================================================================

    //~ Methods ========================================================================================================
    // 正常
    public static final int SUCCESS = 0;
    // 服务器内部错误
    public static final int BAIDU_SERVER_ERROR = 1;
    // 请求参数非法
    public static final int ILLEGAL_ARGUMENT = 2;
    // 权限校验失败
    public static final int AUTHENTICATION_FAILED = 3;
    // 配额校验失败
    public static final int QUOTA_AUTH_FAILED = 4;
    // ak不存在或者非法
    public static final int BAIDU_AK_NOT_EXISTS_OR_ERROR = 5;
    // 服务禁用
    public static final int SERVICE_FORBIDDEN = 101;
    // 不通过白名单或者安全码不对
    public static final int SECURITY_CODE_INVALID = 102;
    // 无权限
    public static final int NO_AUTHENTICATION = 200;
    // 配额错误
    public static final int QUOTA_ERROR = 300;
}
