package com.belonk.commons.util.crypt;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * <p>Created by Dendy on 2015/12/7.
 *
 * @author sunfuchang03@126.com
 * @version 0.1
 * @since 1.0
 */
public class Base64 {
    //~ Static fields/initializers =====================================================================================

    //~ Instance fields ================================================================================================

    private static BASE64Encoder base64Encoder = new BASE64Encoder();
    private static BASE64Decoder base64Decoder = new BASE64Decoder();

    //~ Methods ========================================================================================================

    public static String encode(String encodeStr) throws UnsupportedEncodingException {
        return base64Encoder.encode(encodeStr.getBytes("utf-8"));
    }

    public static String encode(byte[] encodeBytes) {
        return base64Encoder.encode(encodeBytes);
    }

    public static byte[] decode(String decodeStr) throws IOException {
        return base64Decoder.decodeBuffer(decodeStr);
    }
}
