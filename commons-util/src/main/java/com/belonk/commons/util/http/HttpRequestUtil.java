package com.belonk.commons.util.http;

import com.alibaba.fastjson.JSONObject;
import com.belonk.commons.util.crypt.Algorithm3DES;
import com.belonk.commons.util.crypt.AlgorithmData;
import com.belonk.commons.util.crypt.MD5Util;
import com.belonk.commons.util.vo.ResponseMessage;
import com.belonk.commons.util.vo.ServiceMsg;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.MimetypesFileTypeMap;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.*;
import java.net.Proxy.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

/**
 * 使用java api实现的http请求工具。
 *
 * @author sunfuchang03@126.comfuchang03@126.com
 * @since 1.0
 */
public class HttpRequestUtil {
    private static final Logger LOG = LoggerFactory.getLogger(HttpRequestUtil.class);

    /**
     * 发送http请求。
     *
     * @param urlStr      访问地址
     * @param httpSetting 请求参数配置，传null则使用默认配置的<code>HttpSetting</code>对象
     * @return 返回获取的URL返回信息
     * @see HttpSetting
     */
    public static String sendRequest(String urlStr, HttpSetting httpSetting) throws IOException {
        if (httpSetting == null)
            httpSetting = HttpSetting.newInstance();

        String params = httpSetting.getParams();
        boolean isGet = httpSetting.isGet();
        Map<String, String> ctMap = httpSetting.getContentTypes();
        Map<String, String> ck = httpSetting.getCookies();
        String encoding = httpSetting.getEncoding();
        HttpProxy _proxy = httpSetting.getProxy();
        String filePath = httpSetting.getUploadFilePath();
        String boundary = httpSetting.getBoundary();

        System.setProperty("jsse.enableSNIExtension", "false");
        String retStr = "";
        urlStr += isGet ? (params != null ? ("?" + params) : "") : "";
        URL url = new URL(urlStr);

        Proxy proxy = null;
        if (_proxy != null) {
            InetAddress id = InetAddress.getByName(_proxy.getHost_url());
            InetSocketAddress socket = new InetSocketAddress(id,
                    Integer.parseInt(_proxy.getPort()));
            proxy = new Proxy(Type.HTTP, socket);
        }

        HttpURLConnection conn = null;
        if (proxy != null) {
            conn = (HttpURLConnection) url.openConnection(proxy);
        } else {
            conn = (HttpURLConnection) url.openConnection();
        }
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(30000);
        conn.setRequestMethod(isGet ? "GET" : "POST");
        // contentType
        if (ctMap != null) {
            for (String key : ctMap.keySet()) {
                if (ctMap.get(key) != null) {
                    conn.setRequestProperty(key, ctMap.get(key));
                }
            }
        }

        // Cookie
        if (ck != null) {
            String tmp = "";
            for (String key : ck.keySet()) {
                if ("".equals(tmp)) {
                    tmp = key + "=" + ck.get(key);
                } else {
                    tmp = ";" + key + "=" + ck.get(key);
                }
            }
            if (!"".equals(tmp)) {
                conn.setRequestProperty("Cookie", tmp);
            }
        }

        conn.connect();

        if (!isGet) {
            //上传文件
            File file = StringUtils.isNotBlank(filePath) ? new File(filePath) : null;
            // 为什么这里write过后会默认为是POST请求，而不管conn.setRequestMethod? 2016-7-6 15:47:48
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.write((StringUtils.trimToEmpty(params)).getBytes(httpSetting.getEncoding()));
            out.flush();

            if (file != null) {
                if (file.exists()) {
                    DataInputStream in = new DataInputStream(new FileInputStream(file));
                    String contentType = new MimetypesFileTypeMap().getContentType(file);
                    StringBuilder form_header = new StringBuilder();
                    form_header.append("--" + boundary + "\r\n");
                    form_header.append("Content-Disposition: form-data; name=\"media\"; filename=\"" + file.getName() + "\"; filelength=\"" + in.available() + "\"");
                    form_header.append("\r\n");
                    form_header.append("Content-Type: " + contentType);
                    form_header.append("\r\n\r\n");
                    out.write(form_header.toString().getBytes());
                    out.flush();

                    byte[] bytes = new byte[1024];
                    int len = 0;
                    while ((len = in.read(bytes)) != -1) {
                        out.write(bytes, 0, len);
                        out.flush();
                        len = 0;
                    }

                    out.write(("\r\n--" + boundary + "--\r\n").getBytes());
                    out.flush();

                    in.close();
                    out.flush();
                    out.close();
                } else {
                    throw new FileNotFoundException("file：\"" + filePath + "\" is not exist!");
                }
            }
            out.flush();
            out.close();
        }

        if (conn.getResponseCode() == 200) {
            String content_encoding = conn.getHeaderField("Content-Encoding");// 相应的内容的类型
            BufferedReader reader = null;
            if (null != content_encoding
                    && Pattern.compile("^.{0,}(gzip){1}.{0,}", Pattern.UNICODE_CASE).matcher(content_encoding).matches()) {
                reader = new BufferedReader(
                        new InputStreamReader(
                                new GZIPInputStream(conn.getInputStream()), encoding));
            } else {
                reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), encoding));
            }
            StringBuilder content = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            reader.close();
            retStr = content.toString();
        } else {
            LOG.error("Request failed, status is : " + conn.getResponseCode());
        }
        conn.disconnect();
        return retStr;
    }

    public static String httpsJsonRequest(String requestUrl, String requestMethod, String outputStr) {
        String response = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            response = buffer.toString();
        } catch (ConnectException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


    public static ResponseMessage callRemoteService(String urlStr, Map<String, Object> bodyObject, String appid, String appkey) throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        Map<String, Object> head = new HashMap<String, Object>();

        head.put("appid", appid.trim());
        head.put("sign", signRequest(appid, bodyObject, appkey));
        head.put("version", "2.0");

        data.put("head", head);
        data.put("body", bodyObject);

        String content = JSONObject.toJSONString(data);
        content = new String(content.getBytes("utf-8"));

        URL url = new URL(urlStr);

        ResponseMessage responseMessage = null;
        HttpURLConnection conn = null;
        OutputStreamWriter wr = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");

            conn.connect();

            wr = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
            wr.write(content);
            wr.flush();

            if (conn.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                ServiceMsg msg = JSONObject.parseObject(reader.readLine(), ServiceMsg.class);
                responseMessage = new ResponseMessage((String) msg.getHead().get("rtnCode"), (String) msg.getHead().get("rtnMsg"), msg.getBody());
                reader.close();
            } else {
                LOG.error("Request failed, status is : " + conn.getResponseCode());
            }
        } catch (Exception var20) {
            throw new IllegalStateException(var20);
        } finally {
            if (wr != null) {
                wr.close();
            }

            if (conn != null) {
                conn.disconnect();
            }
        }
        return responseMessage;
    }

    public static String signRequest(String appid, Object src, String appkey) throws Exception {
        String srcText = JSONObject.toJSONString(src);
        String base64Text = new String(Base64.encodeBase64((appid + srcText).getBytes("utf8"), false));
        String destText = MD5Util.md5Digest(base64Text);
        AlgorithmData data = new AlgorithmData();
        data.setDataMing(destText);
        data.setKey(appkey);
        Algorithm3DES.encryptMode(data);
        return data.getDataMi();
    }

//    public static void main(String[] args) {
//        String s = "{\"head\":{\"rtnMsg\":\"本次请求成功!\",\"rtnCode\":\"000000\"},\"body\":\"a3aed752693c450f9c475eb5c7713847\"}";
//        ServiceMsg msg = JSONObject.parseObject(s, ServiceMsg.class);
//        ResponseMessage responseMessage = new ResponseMessage((String)msg.getHead().get("rtnCode"), (String)msg.getHead().get("rtnMsg"), msg.getBody());
//        System.out.println(responseMessage);
//    }
}
