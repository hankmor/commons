package com.belonk.common.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by sun on 2018/5/30.
 *
 * @author sunfuchang03@126.com
 * @version 1.0
 * @since 1.0
 */
public class JacksonUtil {
    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Static fields/constants/initializer
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    private static Logger log = LoggerFactory.getLogger(JacksonUtil.class);
    private static ObjectMapper defaultMapper = new ObjectMapper();
    private static ObjectMapper customMapper = new ObjectMapper();

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Instance fields
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */



    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Constructors
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    static {
        // 属性值为null不序列化
        defaultMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // map值为null不序列化
        defaultMapper.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);
        defaultMapper.setTimeZone(TimeZone.getDefault());
        defaultMapper.setLocale(Locale.getDefault());

        customMapper.setSerializationInclusion(JsonInclude.Include.USE_DEFAULTS);
        customMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        customMapper.setTimeZone(TimeZone.getDefault());
        customMapper.setLocale(Locale.getDefault());
    }

    private JacksonUtil() {
    }

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Public Methods
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    public static <T> String toJson(T t) {
        try {
            return defaultMapper.writeValueAsString(t);
            // return defaultMapper.writer()
            //         .without(SerializationFeature.WRITE_NULL_MAP_VALUES)
            //         .writeValueAsString(t);
        } catch (JsonProcessingException e) {
            log.error("Convert object to json failed : ", e);
        }
        return null;
    }

    public static <T> String toJsonWithNull(T t) {
        try {
            return customMapper.writeValueAsString(t);
            // final ObjectWriter writer = includeNullMapper.writer();
            // return writer.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            log.error("Convert object to json failed : ", e);
        }
        return null;
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return defaultMapper.readValue(json, clazz);
            // return defaultMapper.readerFor(clazz).readValue(json);
        } catch (IOException e) {
            log.error("Convert json to object failed : ", e);
        }
        return null;
    }

    public static <T> T fromJsonIgnoreUnknown(String json, Class<T> clazz) {
        try {
            return customMapper.readValue(json, clazz);
            // return defaultMapper.readerFor(clazz)
            //         .without(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            //         .readValue(json);
        } catch (IOException e) {
            log.error("Convert json to object failed : ", e);
        }
        return null;
    }

    /**
     * @param json  json
     * @param clazz 实际类型
     * @param <T>   实际类型
     * @return list
     * @since 1.1
     */
    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        try {
            TypeReference<T> tTypeReference = new TypeReference<T>() {
                @Override
                public Type getType() {
                    return clazz;
                }
            };
            // 构造一个符合类型
            CollectionType collectionType = defaultMapper.getTypeFactory().constructCollectionType(List.class, clazz);
            return defaultMapper.readValue(json, collectionType);
        } catch (IOException e) {
            log.error("Convert json to object failed : ", e);
        }
        return null;
    }

    /**
     * @param json  json
     * @param clazz 实际类型
     * @param <T>   实际类型
     * @return list
     * @since 1.1
     */
    public static <T> List<T> parseArrayIgnoreUnknown(String json, Class<T> clazz) {
        try {
            // 构造一个符合类型
            CollectionType collectionType = customMapper.getTypeFactory().constructCollectionType(List.class, clazz);
            return customMapper.readValue(json, collectionType);
        } catch (IOException e) {
            log.error("Convert json to object failed : ", e);
        }
        return null;
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return defaultMapper.readValue(json, typeReference);
            // return defaultMapper.readerFor(typeReference).readValue(json);
        } catch (IOException e) {
            log.error("Convert json to object failed : ", e);
        }
        return null;
    }

    public static <T> T fromJsonIgnoreUnknown(String json, TypeReference<T> typeReference) {
        try {
            return customMapper.readValue(json, typeReference);
            // return defaultMapper.readerFor(typeReference)
            //         .without(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)
            //         .readValue(json);
        } catch (IOException e) {
            log.error("Convert json to object failed : ", e);
        }
        return null;
    }

    public static <T> T convert(Object fromValue, Class<T> toValueType) {
        return defaultMapper.convertValue(fromValue, toValueType);
    }

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Private Methods
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */


}