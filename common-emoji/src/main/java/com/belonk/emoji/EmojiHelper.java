package com.belonk.emoji;

import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Emoji通用工具类。
 * <p>
 * Created by sun on 2020/8/21.
 *
 * @author sunfuchang03@126.com
 * @since 2.0
 */
public class EmojiHelper {
	//~ Static fields/constants/initializer


	//~ Instance fields


	//~ Constructors


	//~ Methods

	/**
	 * 判断字符串中是否含有emoji字符。
	 *
	 * @param str 字符串
	 * @return 如果包含emoji，返回true，否则返回false
	 * @see #isEmojis(String)
	 * @see #isOnlyEmojis(String)
	 */
	public static boolean containsEmoji(String str) {
		if (str == null || "".equals(str)) {
			return false;
		}
		return EmojiManager.containsEmoji(str);
	}

	/**
	 * 判断给定字符串是否是一个emoji字符。
	 * <p>
	 * 仅判断给定字符是否是<b>一个</b>emoji，如果需要判断多个emoji，请使用{@link #isOnlyEmojis(String)}。
	 *
	 * @param str 给定字符串
	 * @return 如果是，则返回true，否则返回false
	 * @see #isOnlyEmojis(String)
	 */
	public static boolean isEmojis(String str) {
		if (str == null || "".equals(str)) {
			return false;
		}
		return EmojiManager.isEmoji(str);
	}

	/**
	 * 判断给定的字符串是否都是emoji字符。
	 *
	 * @param str 给定的字符串
	 * @return 如果都是emoji，返回true，否则返回false
	 */
	public static boolean isOnlyEmojis(String str) {
		if (str == null || "".equals(str)) {
			return false;
		}
		return EmojiManager.isOnlyEmojis(str);
	}

	/**
	 * 将给定字符串中的所有emoji转换为对应的别名。
	 *
	 * @param str 给定字符串
	 * @return 转换后的字符串
	 */
	public static String toAlias(String str) {
		if (str == null || "".equals(str)) {
			return str;
		}
		return EmojiParser.parseToAliases(str);
	}

	/**
	 * 将给定的字符串中的emoji别名，转换为unicode的emoji字符。
	 *
	 * @param str 给定字符串
	 * @return 转换后的字符串
	 */
	public static String toUnicode(String str) {
		if (str == null || "".equals(str)) {
			return str;
		}
		return EmojiParser.parseToUnicode(str);
	}

	/**
	 * 将给定的字符串中的emoji转换为html的形式。
	 *
	 * @param str 给定字符串
	 * @return 转换后的字符串
	 */
	public static String toHtml(String str) {
		if (str == null || "".equals(str)) {
			return str;
		}
		return EmojiParser.parseToHtmlDecimal(str);
	}

	/**
	 * 将给定的字符串中的emoji全部移除。
	 *
	 * @param str 给定字符串
	 * @return 移除emoji后的字符串
	 */
	public static String removeAllEmojis(String str) {
		if (str == null || "".equals(str)) {
			return str;
		}
		return EmojiParser.removeAllEmojis(str);
	}

	/**
	 * 将给定的字符串中的emoji抽取出来，仅保留emoji，其余字符删除。
	 *
	 * @param str 给定字符串
	 * @return 转换后emoji字符列表
	 */
	public static List<String> extractEmojis(String str) {
		if (str == null || "".equals(str)) {
			return new ArrayList<>();
		}
		return EmojiParser.extractEmojis(str);
	}

	/**
	 * 将给定字符串中的emoji全部替换为一个特定字符串。
	 *
	 * @param str         给定字符串
	 * @param replacement 替换的字符串
	 * @return 替换后的字符串
	 */
	public static String replaceAllEmojis(String str, String replacement) {
		if (str == null || "".equals(str) || replacement == null) {
			return str;
		}
		if ("".equals(replacement)) {
			return removeAllEmojis(str);
		}
		return EmojiParser.replaceAllEmojis(str, replacement);
	}
}