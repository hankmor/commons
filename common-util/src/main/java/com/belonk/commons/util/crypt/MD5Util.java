package com.belonk.commons.util.crypt;

import com.belonk.commons.util.asserts.Assert;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

	public static String md5Digest(String pwd) throws Exception {
		MessageDigest alg = MessageDigest.getInstance("MD5");
		alg.update(pwd.getBytes("UTF-8"));
		byte[] digest = alg.digest();
		return byte2hex(digest);
	}

	public static String md5(byte[] bytes) throws Exception {
		MessageDigest alg = MessageDigest.getInstance("MD5");
		alg.update(bytes);
		byte[] digest = alg.digest();
		return byte2hex(digest);
	}

	public static String md5(byte[] bytes, int bit) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(System.getProperty(
				"MD5.algorithm", "MD5"));
		if (bit == 16)
			return byte2hex(md.digest(bytes))
					.substring(8, 24);
		return byte2hex(md.digest(bytes));
	}

	public static String md5(byte[] bytes, byte[] salt, int hashIterations) throws NoSuchAlgorithmException {
		Assert.isTrue(bytes.length > 0);
		Assert.isTrue(hashIterations > 0);
		MessageDigest digest = MessageDigest.getInstance("MD5");
		if (salt != null) {
			digest.reset();
			digest.update(salt);
		}

		byte[] hashed = digest.digest(bytes);
		for (int i = 0; i < hashIterations - 1; i++) {
			digest.reset();
			hashed = digest.digest(hashed);
		}
		return byte2hex(hashed);
	}

	private static String byte2hex(byte[] b) {
		String hs   = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toLowerCase();
	}
}
