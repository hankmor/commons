package com.belonk.commons.util.crypt;

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


    //~ Methods ========================================================================================================

    public static String encode(String encodeStr) throws UnsupportedEncodingException {
        return org.apache.commons.codec.binary.Base64.encodeBase64String(encodeStr.getBytes("utf-8"));
    }

    public static String encode(byte[] encodeBytes) {
        return org.apache.commons.codec.binary.Base64.encodeBase64String(encodeBytes);
    }

    public static byte[] decode(String decodeStr) throws IOException {
        return org.apache.commons.codec.binary.Base64.decodeBase64(decodeStr);
    }

    /**
     * Decode a string, return a decoded string.
     *
     * @param decodeStr desc string
     * @return decoded string
     * @throws IOException
     * @since 1.2
     */
    public static String decodeString(String decodeStr) throws IOException {
        return new String(org.apache.commons.codec.binary.Base64.decodeBase64(decodeStr), "utf-8");
    }
}
