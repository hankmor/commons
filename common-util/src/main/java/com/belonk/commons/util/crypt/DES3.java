package com.belonk.commons.util.crypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * <p>Created by Dendy on 2015/12/8.
 *
 * @author sunfuchang03@126.com
 * @version 0.1
 * @since 1.0
 */
public class DES3 {
    private static final String ALGORITHM = "DESede"; // 定义 加密算法,可用
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final DES3 INSTANCE = new DES3();

    public static DES3 getInstance() {
        return INSTANCE;
    }

    /**
     * 生产24字节的key。
     */
    public static int num = 168;//24字节

    public class EncodedResult<T> {
        private T key;
        private T data;

        public EncodedResult() {
        }

        public EncodedResult(T key, T data) {
            this.key = key;
            this.data = data;
        }

        public T getKey() {
            return key;
        }

        public void setKey(T key) {
            this.key = key;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }


    /**
     * 加密。
     *
     * @param src
     * @param keybyte
     * @return
     * @throws Exception
     */
    public String encrypt(byte[] src, byte[] keybyte) throws Exception {
        if (src == null || src.length == 0)
            return null;
        if (keybyte == null || keybyte.length == 0)
            throw new RuntimeException("Must provide the key.");
        // 生成密钥
        SecretKey secretKey = new SecretKeySpec(keybyte, ALGORITHM);
        // 加密
        Cipher c1 = Cipher.getInstance(ALGORITHM);
        c1.init(Cipher.ENCRYPT_MODE, secretKey);
        return byte2hex(c1.doFinal(src));
    }

    /**
     * 加密。
     *
     * @param src
     * @param keyStr
     * @return
     * @throws Exception
     */
    public String encrypt(byte[] src, String keyStr) throws Exception {
        if (src == null || src.length == 0)
            return null;
        if (keyStr == null || keyStr.length() == 0)
            throw new RuntimeException("Must provide the key.");
        byte[] keybyte = hex2Byte(keyStr);
        SecretKey secretKey = new SecretKeySpec(keybyte, ALGORITHM);
        // 加密
        Cipher c1 = Cipher.getInstance(ALGORITHM);
        c1.init(Cipher.ENCRYPT_MODE, secretKey);
        return byte2hex(c1.doFinal(src));
    }

    public EncodedResult<String> encrypt(byte[] src) throws Exception {
        if (src == null || src.length == 0)
            return null;
        // 生成key
        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
        kg.init(num);
        SecretKey secretKey = kg.generateKey();
        // 加密
        Cipher c1 = Cipher.getInstance(ALGORITHM);
        c1.init(Cipher.ENCRYPT_MODE, secretKey);
        return new EncodedResult<String>(byte2hex(secretKey.getEncoded()), byte2hex(c1.doFinal(src)));
    }

    /**
     * 解密。
     *
     * @param keybyte
     * @param src
     * @return
     */
    public byte[] decrypt(String src, byte[] keybyte) throws Exception {
        if (src == null || src.length() == 0)
            return null;
        if (keybyte == null || keybyte.length == 0)
            throw new RuntimeException("Must provide the key.");
        SecretKey secretKey = null;
        secretKey = new SecretKeySpec(keybyte, ALGORITHM);
        Cipher c1 = Cipher.getInstance(ALGORITHM);
        c1.init(Cipher.DECRYPT_MODE, secretKey);
        return c1.doFinal(hex2Byte(src));
    }

    /**
     * 解密。
     *
     * @param src
     * @param keyStr
     * @return
     * @throws Exception
     */
    public byte[] decrypt(String src, String keyStr) throws Exception {
        if (src == null || src.length() == 0)
            return null;
        if (keyStr == null || keyStr.length() == 0)
            throw new RuntimeException("Must provide the key.");
        byte[] keybyte = hex2Byte(keyStr);
        SecretKey secretKey = new SecretKeySpec(keybyte, ALGORITHM);
        Cipher c1 = Cipher.getInstance(ALGORITHM);
        c1.init(Cipher.DECRYPT_MODE, secretKey);
        return c1.doFinal(hex2Byte(src));
    }

    private byte[] hex2Byte(String str) {
        byte[] bytes = new byte[str.length() / 2];
        int len = bytes.length;
        for (int i = 0; i < len; i++) {
            bytes[i] = (byte) Integer.parseInt(str.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    // 转换成十六进制字符串
    private String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
//            if (n < b.length - 1) {
//                hs = hs + ":";
//            }
        }
        return hs;
    }
}
