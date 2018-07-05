package com.belonk.commons.util.collection;

import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

/**
 * 集合工具类
 *
 * @author sunfuchang03@126.comfuchang03@126.com
 * @since 1.0
 * @version 1.0
 */
public class CollectionHelper extends CollectionUtils {
    /**
     * 集合是否为空
     *
     * @param collection 集合
     * @param <T>        集合类型
     * @return true:为空;false:不为空
     */
    public static <T> boolean isNull(Collection<T> collection) {
        return null == collection || collection.isEmpty();
    }

    /**
     * 集合是否为不为空
     *
     * @param collection 集合
     * @param <T>        集合类型
     * @return true:不为空;false:为空
     */
    public static <T> boolean notNull(Collection<T> collection) {
        return !isNull(collection);
    }

    /**
     * Map是否为空
     *
     * @param map map
     * @param <T> key类型
     * @param <O> value类型
     * @return true:为空;false:不为空
     */
    public static <T, O> boolean isNull(Map<T, O> map) {
        return null == map || map.isEmpty();
    }

    /**
     * Map是否为不为空
     *
     * @param map map
     * @param <T> key类型
     * @param <O> value类型
     * @return true:不为空;false:为空
     */
    public static <T, O> boolean notNull(Map<T, O> map) {
        return !isNull(map);
    }

    /**
     * 数组是否为空
     *
     * @param array 数组
     * @param <T>   数组类型
     * @return true:为空;false:不为空
     */
    public static <T> boolean isNull(T[] array) {
        return null == array || array.length == 0;
    }

    /**
     * 数组是否为不为空
     *
     * @param array 数组
     * @param <T>   数组类型
     * @return true:不为空;false:为空
     */
    public static <T> boolean notNull(T[] array) {
        return !isNull(array);
    }

    /**
     * 集合长度是否相等(任意集合为空返回false), 比较长度的集合可以为不同的实体集合
     *
     * @param collection1 集合1
     * @param collection2 集合2
     * @return true:相等;false:不等
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static boolean sizeEqual(Collection collection1, Collection collection2) {
        if (null == collection1 || null == collection2) {
            throw new NullPointerException("参数不能为Null！");
        }
        return collection1.size() == collection2.size();
    }

    /**
     * 将目标字符串集合的元素全部转为大写。
     *
     * @param stringCollection 目标集合
     * @return 转换后集合
     */
    public static Collection<String> toUpperCase(Collection<String> stringCollection) {
        if (stringCollection == null || stringCollection.size() == 0)
            return stringCollection;
        Collection<String> result = new LinkedList<String>();
        for (String s : stringCollection) {
            result.add(s.toUpperCase());
        }
        return result;
    }

    /**
     * 将目标字符串集合的元素全部转为小写。
     *
     * @param stringCollection 目标集合
     * @return 转换后集合
     */
    public static Collection<String> toLowerCase(Collection<String> stringCollection) {
        if (stringCollection == null || stringCollection.size() == 0)
            return stringCollection;
        Collection<String> result = stringCollection;
        for (String s : stringCollection) {
            result.add(s.toLowerCase());
        }
        return result;
    }
}
