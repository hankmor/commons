package com.belonk.common.lang;

import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * <p>Created by sun on 2016/1/13.
 *
 * @author sunfuchang03@126.com
 * @version 1.0
 * @since 2.2.3
 */
public class PinyinHelper {
	//~ Static fields/initializers =====================================================================================

	//~ Instance fields ================================================================================================

	//~ Methods ========================================================================================================

	/**
	 * 获取汉字串拼音首字母，英文字符不变
	 *
	 * @param chinese 汉字串
	 * @return 汉语拼音首字母
	 */
	public static String getFirstSpell(String chinese) {
		StringBuilder pybf = new StringBuilder();
		char[] arr = chinese.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (char c : arr) {
			if (c > 128) {
				try {
					String[] temp = net.sourceforge.pinyin4j.PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat);
					if (temp != null) {
						pybf.append(temp[0].charAt(0));
					}
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pybf.append(c);
			}
		}
		return pybf.toString().replaceAll("\\W", "").trim();
	}

	/**
	 * 获取汉字串拼音，英文字符不变
	 *
	 * @param chinese 汉字串
	 * @return 汉语拼音
	 */
	public static String getFullSpell(String chinese) {
		StringBuilder pybf = new StringBuilder();
		char[] arr = chinese.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (char c : arr) {
			if (c > 128) {
				try {
					String[] strings = net.sourceforge.pinyin4j.PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat);
					if (strings == null || strings.length == 0) {
						// 跳过，可能是个特殊字符
					} else {
						pybf.append(strings[0]);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				pybf.append(c);
			}
		}
		return pybf.toString();
	}
}
