package com.belonk.commons.util.asserts;

import org.apache.commons.lang.StringUtils;

import java.io.File;

/**
 * 参数检查、断言工具
 *
 * @author sunfuchang03@126.com
 * @since 1.0
 */
public abstract class Assert {
    /**
     * Assert a boolean expression, throwing <code>IllegalArgumentException</code> if the test result is <code>false</code>.
     * <p/>
     * <pre class="code">
     * Assert.isTrue(i &gt; 0, &quot;The value must be greater than zero&quot;);
     * </pre>
     *
     * @param expression a boolean expression
     * @param message    the exception message to use if the assertion fails
     * @throws IllegalArgumentException if expression is <code>false</code>
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert a boolean expression, throwing <code>IllegalArgumentException</code> if the test result is <code>false</code>.
     * <p/>
     * <pre class="code">
     * Assert.isTrue(i &gt; 0);
     * </pre>
     *
     * @param expression a boolean expression
     * @throws IllegalArgumentException if expression is <code>false</code>
     */
    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] - this expression must be true");
    }

    /**
     * Assert that an object is <code>null</code> .
     * <p/>
     * <pre class="code">
     * Assert.isNull(value, &quot;The value must be null&quot;);
     * </pre>
     *
     * @param object  the object to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the object is not <code>null</code>
     */
    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that an object is <code>null</code> .
     * <p/>
     * <pre class="code">
     * Assert.isNull(value);
     * </pre>
     *
     * @param object the object to check
     * @throws IllegalArgumentException if the object is not <code>null</code>
     */
    public static void isNull(Object object) {
        isNull(object, "[Assertion failed] - the object argument must be null");
    }

    /**
     * Assert that an object is not <code>null</code> .
     * <p/>
     * <pre class="code">
     * Assert.notNull(clazz, &quot;The class must not be null&quot;);
     * </pre>
     *
     * @param object  the object to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the object is <code>null</code>
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that an object is not <code>null</code> .
     * <p/>
     * <pre class="code">
     * Assert.notNull(clazz);
     * </pre>
     *
     * @param object the object to check
     * @throws IllegalArgumentException if the object is <code>null</code>
     */
    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }

    /**
     * 检查多个参数不为空
     *
     * @param msg 异常信息
     * @param o   参数对象
     */
    public static void notNull(String msg, Object... o) {
        for (Object object : o) {
            if (object == null)
                throw new IllegalArgumentException(msg);
        }
    }

    /**
     * 检查数组不为null且长度不能为0
     *
     * @param o   数组对象
     * @param msg 异常信息
     */
    public static void notEmpty(Object[] o, String msg) {
        if (o == null || o.length == 0)
            throw new IllegalArgumentException(msg);
    }


    /**
     * 检查数组不为null且长度不能为0
     *
     * @param o
     */
    public static void notEmpty(Object[] o) {
        notEmpty(o, "ids of objects deleting must not be null.");
    }


    /**
     * Assert that the given String is not empty; that is,
     * it must not be <code>null</code> and not the empty String.
     * <p/>
     * <pre class="code">
     * Assert.hasLength(name, &quot;Name must not be empty&quot;);
     * </pre>
     *
     * @param text    the String to check
     * @param message the exception message to use if the assertion fails
     */
    public static void hasLength(String text, String message) {
        if (text == null || StringUtils.isBlank(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that the given String is not empty; that is,
     * it must not be <code>null</code> and not the empty String.
     * <p/>
     * <pre class="code">
     * Assert.hasLength(name);
     * </pre>
     *
     * @param text the String to check
     */
    public static void hasLength(String text) {
        hasLength(text, "[Assertion failed] - this String argument must have length; it must not be null or empty");
    }

    /**
     * 检查数组长度是否与给定长度相同
     *
     * @param array  数组
     * @param length 给定长度
     */
    public static void lengthEquals(Object[] array, int length) {
        notNull(array);
        if (array.length != length)
            throw new IllegalArgumentException("the length of target array is not equals the special length : " +
                    length);
    }

    /**
     * 检查两个数组长度是否相同
     *
     * @param array  数组1
     * @param array2 数组2
     * @param msg    异常信息
     */
    public static void lengthEquals(Object[] array, Object[] array2, String msg) {
        notNull(array);
        notNull(array2);
        if (array.length != array2.length)
            throw new IllegalArgumentException(msg);
    }


    /**
     * Assert that the given file exists and size is great then zero;
     *
     * @param file the file to check.
     */
    public static void hasSize(File file) {
        notNull(file);
        exists(file);
        isFile(file);
        if (file.length() <= 0)
            throw new RuntimeException("[Assertion failed] - this size of given file must great then zero.");
    }

    /**
     * Assert that the given file is not directory but a file.
     *
     * @param file the file to check.
     */
    public static void isFile(File file) {
        if (file.isDirectory())
            throw new RuntimeException("[Assertion failed] - this given file is required; it must not be a directory.");
    }

    /**
     * Assert that the given file exists.
     *
     * @param file the file to check.
     */
    public static void exists(File file) {
        if (!file.exists())
            throw new RuntimeException("[Assertion failed] - this given file or directory is required; it must not be null and exist.");
    }
}
