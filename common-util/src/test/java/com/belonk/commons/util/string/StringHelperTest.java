package com.belonk.commons.util.string;

import org.junit.jupiter.api.Test;

/**
 * Created by sun on 2020/12/1.
 *
 * @author sunfuchang03@126.com
 * @since 1.0
 */
public class StringHelperTest {
	//~ Static fields/constants/initializer


	//~ Instance fields


	//~ Constructors


	//~ Methods

	@Test
	public void test1() {
		String s = "15100000000";
		System.out.println(s.replaceAll("(.{3})", "_"));
		String blurStr = StringHelper.blurStr(s, 7, 4, 4, null);
		System.out.println(blurStr);
		blurStr = StringHelper.blurStr(s, 7, 10, 4, null);
		System.out.println(blurStr);
		blurStr = StringHelper.blurStr(s, 0, 0, 3, null);
		System.out.println(blurStr);
		blurStr = StringHelper.blurStr(s, 0, 0, 0, null);
		System.out.println(blurStr);
		String[] ss = new String[3];
		ss[0] = "a";
		ss[1] = "bb";
		ss[2] = "cc";
		System.out.println(StringHelper.join(ss, ","));

		System.out.println(StringHelper.uuid32(false));
		System.out.println(StringHelper.uuid32(true));

		String str = "this_is_underscore";
		System.out.println(StringHelper.underscoreToCamel(str));
		System.out.println(StringHelper.camelToUnderscore(StringHelper.underscoreToCamel(str)));

		s = "http://www.baidu.com/http:/";
		System.out.println(StringHelper.trim(s, "http:/"));
		System.out.println(StringHelper.trim(s, "/"));
		System.out.println(StringHelper.leftTrim(s, "http://"));
		System.out.println(StringHelper.rightTrim(s, "/http:/"));
	}
}