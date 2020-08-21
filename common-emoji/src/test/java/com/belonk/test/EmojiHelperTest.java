package com.belonk.test;

import com.belonk.emoji.EmojiHelper;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Created by sun on 2020/8/21.
 *
 * @author sunfuchang03@126.com
 * @since 1.0
 */
public class EmojiHelperTest {
	//~ Static fields/constants/initializer


	//~ Instance fields


	//~ Constructors


	//~ Methods

	@Test
	public void test2Alias() {
		String str = "An😃😀awesome 😃😃string with a few 😃😉emojis!";
		String alias = EmojiHelper.toAlias(str);
		System.out.println(str);
		System.out.println("EmojiConverterTest.testToAlias()=====>");
		System.out.println(alias);
		assert "An:smiley::grinning:awesome :smiley::smiley:string with a few :smiley::wink:emojis!".equals(alias);
	}

	@Test
	public void testToHtml() {
		String str = "An😃😀awesome 😃😃string with a few 😃😉emojis!";
		String result = EmojiHelper.toHtml(str);
		System.out.println(str);
		System.out.println("EmojiConverterTest.testToHtml()=====>");
		System.out.println(result);
		assert "An&#128515;&#128512;awesome &#128515;&#128515;string with a few &#128515;&#128521;emojis!".equals(result);
	}

	@Test
	public void testToUnicode() {
		String str = ":smiley: :grinning: :wink:";
		String result = EmojiHelper.toUnicode(str);
		System.err.println(str);
		System.err.println("EmojiConverterTest.testToUnicode()=====>");
		System.err.println(result);
		assert "😃 😀 😉".equals(result);
	}

	@Test
	public void test1() {
		String s = "🙅🙆😊💑🀄️👏😉😃 😀";
		String result = EmojiHelper.toAlias(s);
		System.out.println(result);
		result = EmojiHelper.toUnicode(s);
		System.out.println(result);
		result = EmojiHelper.toHtml(s);
		System.out.println(result);
	}

	@Test
	public void test2() {
		String s = "dasdfasdfas差是的发送到发现💑";
		System.out.println(EmojiHelper.toAlias(s));
		System.out.println(EmojiHelper.toHtml(s));
		System.out.println(EmojiHelper.toUnicode(s));
	}

	@Test
	public void testContainsEmoji() {
		String str = "🙆😊💑👏😉😃😀";
		boolean b = EmojiHelper.containsEmoji(str);
		System.out.println(b);
		assert b;
	}

	@Test
	public void testIsEmoji() {
		String str = "🙆😊💑👏😉😃😀🀄";
		System.out.println(EmojiHelper.isEmojis(str)); // false
		// 一个emoji占用两个长度，两个char
		for (int i = 0; i < str.length(); i += 2) {
			String s = str.substring(i, i + 2);
			boolean b = EmojiHelper.isEmojis(s);
			System.out.println(s + " : " + b);
			assert b;
		}
	}

	@Test
	public void testIsOnlyEmoji() {
		String str = "🀄🙆😊💑👏😉😃😀";
		boolean b = EmojiHelper.isOnlyEmojis(str);
		System.out.println(b);
		assert b;

		str = "🀄🙆😊💑👏😉😃😀123";
		b = EmojiHelper.isOnlyEmojis(str);
		System.out.println(b);
		assert !b;
	}

	@Test
	public void testRemoveAllEmojis() {
		String str = "12啊啊🙆😊💑  🀄👏 😉😃😀b啊啊";
		String s = EmojiHelper.removeAllEmojis(str);
		System.out.println(s);
		assert "12啊啊   b啊啊".equals(s);
	}

	@Test
	public void testExtractEmojis() {
		String str = "12啊啊🙆😊💑  🀄👏 😉😃😀b啊啊";
		List<String> emojis = EmojiHelper.extractEmojis(str);
		System.out.println(emojis);
		assert emojis.size() == 8;
	}

	@Test
	public void testReplaceAllEmojis() {
		String str = "12啊啊🙆😊💑🀄👏😉😃😀b啊啊";
		String s = EmojiHelper.replaceAllEmojis(str, "|");
		System.out.println(s);
		assert "12啊啊||||||||b啊啊".equals(s);
	}
}