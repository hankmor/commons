package com.belonk.commons.util.crypt;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by sun on 2017/6/15.
 *
 * @author sunfuchang03@126.com
 * @version 1.0
 * @since 1.0
 */
final public class AES {
	//~ Static fields/initializers =====================================================================================


	//~ Instance fields ================================================================================================


	//~ Constructors ===================================================================================================


	//~ Methods ========================================================================================================

	/**
	 * 加密字符串。
	 *
	 * @param content  被加密字符串
	 * @param password 密钥
	 * @param charset  字符串编码
	 * @return 16进制密文
	 */
	public static String encrypt(String content, String password, String charset) {
		try {
			byte[] bytes  = content.getBytes(charset); // 原始字符串转为字节数组
			byte[] result = encrypt(bytes, password, charset);
			return parseByte2HexStr(result); // 加密后的字节数组转换为16进制字符串，这里不能直接new String(result, charset)，不可逆
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 加密字节数组。
	 *
	 * @param content  被加密字节数组
	 * @param password 密钥
	 * @param charset  字符编码
	 * @return 加密后的字节数组
	 */
	public static byte[] encrypt(byte[] content, String password, String charset) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			// fixed bug: javax.crypto.BadPaddingException: Given final block not properly padded
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(password.getBytes());
			kgen.init(128, random);
			SecretKey     secretKey    = kgen.generateKey();
			byte[]        enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key          = new SecretKeySpec(enCodeFormat, "AES");
			Cipher        cipher       = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密字符串。
	 *
	 * @param content  15进制密文字符串
	 * @param password 密钥
	 * @param charset  字符编码
	 * @return 解密后原始字符串
	 */
	public static String decrypt(String content, String password, String charset) {
		try {
			byte[] bytes  = parseHexStr2Byte(content);
			byte[] result = decrypt(bytes, password, charset);
			return new String(result, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密字节数组。
	 *
	 * @param content  加密的字节数组
	 * @param password 密钥
	 * @param charset  字符集编码
	 * @return 解密后的原始字节数组
	 */
	public static byte[] decrypt(byte[] content, String password, String charset) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			// fixed bug: javax.crypto.BadPaddingException: Given final block not properly padded
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(password.getBytes());
			kgen.init(128, random);
			SecretKey     secretKey    = kgen.generateKey();
			byte[]        enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key          = new SecretKeySpec(enCodeFormat, "AES");
			Cipher        cipher       = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 字节数组转换为16进制字符串
	 *
	 * @param buf 字节数组
	 * @return 16进制字符串
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 16进制字符串转换为字节数组
	 *
	 * @param hexStr 16进制字符串
	 * @return 字节数组
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low  = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String content  = "abctest11123asa这里有中文";
		String password = "12345678";
		String charset  = "utf-8";
		//加密
		System.out.println("加密前：" + content);
		String encryptResult = encrypt(content, password, charset);
		System.out.println("加密后：" + encryptResult);
		//解密
		String decryptResult = decrypt(encryptResult, password, charset);
		System.out.println("解密后：" + decryptResult);

		byte[] src = content.getBytes(charset);
		byte[] res = encrypt(src, password, charset);
		System.out.println("加密后：" + parseByte2HexStr(res));
		System.out.println("加密后：" + new String(res, charset)); // 错误，这里的res与原始字节数组不可逆
		byte[] decrypt = decrypt(res, password, charset);
		System.out.println("解密后：" + new String(decrypt, charset));
	}
}
