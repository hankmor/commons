package com.belonk.commons.util.http;

import java.util.HashMap;
import java.util.Map;

/**
 * http请求参数配置对象。
 * <p/>
 * 支持文件上传配置，当<code>uploadFilePath</code>不为空时，<code>boundary</code>也不能为空。
 * <p/>
 * 配置说明：
 * <li>params 参数信息，以"&" 隔开每个参数的值，默认为null
 * <li>isGet 是否是Get的请求方式，默认为<code>true</code>
 * <li>contentTypes <code>contentType</code>的<code>map</code>集合，默认为<code>null</code>
 * <li>cookies cookie提交的参数信息，默认为<code>null</code>
 * <li>encoding 返回内容的编码，默认为utf-8
 * <li>proxy 代理设置，默认为<code>null</code>
 * <li>uploadFilePath 需要上传的文件路径，该文件路径为绝对路径，默认为<code>null</code>，不上传文件
 * <li>boundary 文件流分割符，用于识别文件的起始位置和结束位置，模拟<code>form-data</code>进行文件上传，当uploadFilePath不为空时，该参数也不能为空
 * <p/>
 * <p>Created by Dendy on 2015/9/11.
 *
 * @author sunfuchang03@126.com
 * @version 0.1
 * @since 1.0
 */
public class HttpSetting {
	//~ Static fields/initializers =====================================================================================

	//~ Instance fields ================================================================================================

	// 参数信息，以"&" 隔开每个参数的值，无参数则为null
	private String params = null;

	// 是否是Get的请求方式
	private boolean isGet = true;

	/**
	 * contentType 的map集合 没有则设置为null
	 *
	 * @deprecated Infavor of headers
	 */
	@Deprecated
	private Map<String, String> contentTypes = null;
	/**
	 * cookie提交的参数信息 没有则设置为null
	 */
	private Map<String, String> cookies = null;
	/**
	 * 返回内容的编码
	 */
	private String encoding = "utf-8";
	/**
	 * 代理设置，如果不使用代理则设置为null
	 */
	private HttpProxy proxy = null;
	/**
	 * 文件路径 需要上传的文件路径，该文件路径为绝对路径，不需要上传文件时设置为null
	 */
	private String uploadFilePath = null;
	/**
	 * 文件流 分割符，用于识别文件的起始位置和结束位置  模拟 form-data 进行文件上传
	 */
	private String boundary = "";

	/**
	 * @since 2.0
	 */
	private String requestMethod;
	/**
	 * @since 2.0
	 */
	private Map<String, String> headers = new HashMap<>();

	//~ Methods ========================================================================================================

	/**
	 * 创建默认配置对象。
	 *
	 * @return 默认请求配置
	 */
	public static HttpSetting newInstance() {
		return new HttpSetting();
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public boolean isGet() {
		return isGet;
	}

	public void setIsGet(boolean isGet) {
		this.isGet = isGet;
	}

	/**
	 * @deprecated since 2.0, in favor of headers
	 */
	public Map<String, String> getContentTypes() {
		return contentTypes;
	}

	/**
	 * @deprecated since 2.0, in favor of headers
	 */
	public void setContentTypes(Map<String, String> contentTypes) {
		this.contentTypes = contentTypes;
	}

	public Map<String, String> getCookies() {
		return cookies;
	}

	public void setCookies(Map<String, String> cookies) {
		this.cookies = cookies;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public HttpProxy getProxy() {
		return proxy;
	}

	public void setProxy(HttpProxy proxy) {
		this.proxy = proxy;
	}

	public String getUploadFilePath() {
		return uploadFilePath;
	}

	public void setUploadFilePath(String uploadFilePath) {
		this.uploadFilePath = uploadFilePath;
	}

	public String getBoundary() {
		return boundary;
	}

	public void setBoundary(String boundary) {
		this.boundary = boundary;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
}