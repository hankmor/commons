package com.belonk.commons.util.clazz;

import com.belonk.commons.util.Converter;
import com.belonk.commons.util.asserts.Assert;
import com.belonk.commons.util.date.DateFormatEnum;
import com.belonk.commons.util.date.DateHelper;
import com.belonk.commons.util.string.StringHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sun on 2018/9/29.
 *
 * @author sunfuchang03@126.com
 * @since 1.0
 */
public class ReflectUtil {
    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Static fields/constants/initializer
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    private static Logger logger = LoggerFactory.getLogger(ReflectUtil.class);

    private static final String SETTER_PREFIX = "set";

    private static final String GETTER_PREFIX = "get";

    private static final String CGLIB_CLASS_SEPARATOR = "$$";

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



    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Public Methods
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    /**
     * 查找所有<code>private</code>的属性，包括继承的属性。
     * <p>
     * 不包括被volatile、static、final修饰的属性。
     *
     * @param clazz 类
     * @return field列表
     */
    public static List<Field> findAllPrivateFields(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        return findAllPrivateFields(clazz, Object.class);
    }

    /**
     * 查找所有<code>private</code>的属性，包括继承的属性。
     * <p>
     * 可以通过<code>excludeModifer</code>参数来设置过滤的属性修饰符，例如：
     * <p>
     * 过滤static修饰的属性:
     * <pre>
     * Modifier.STATIC
     * </pre>
     * 过滤掉包含final或static或volatile的属性:
     * <pre>
     * Modifier.FINAL | Modifier.STATIC | Modifier.VOLATILE
     * </pre>
     *
     * @param clazz          类
     * @param excludeModifer 过滤属性修饰符
     * @return field列表
     * @since 2.0
     */
    public static List<Field> findPrivateFields(Class<?> clazz, Integer excludeModifer) {
        if (clazz == null) {
            return null;
        }
        return findPrivateFields(clazz, Object.class, excludeModifer);
    }

    /**
     * 查找所有<code>private</code>的属性，包括继承的属性，当父类<code>Class</code>与<code>superClass</code>相同时，停止向上查找。
     * <p>
     *
     * @param clazz      Class
     * @param superClass clazz的父类Class，clazz父类等于该参数时，查找完后停止再向上查询
     * @return field列表
     */
    public static <T> List<Field> findAllPrivateFields(Class<? extends T> clazz, Class<T> superClass) {
        return findPrivateFields(clazz, superClass, Modifier.FINAL | Modifier.STATIC | Modifier.VOLATILE);
    }

    /**
     * 查找所有<code>private</code>的属性，包括继承的属性，当父类<code>Class</code>与<code>superClass</code>相同时，停止向上查找。
     * <p>
     * <p>
     * 可以通过<code>excludeModifer</code>参数来设置过滤的属性修饰符，例如：
     * <p>
     * 过滤static修饰的属性:
     * <pre>
     * Modifier.STATIC
     * </pre>
     * 过滤掉包含final或static或volatile的属性:
     * <pre>
     * Modifier.FINAL | Modifier.STATIC | Modifier.VOLATILE
     * </pre>
     *
     * @param clazz          Class
     * @param excludeModifer 过滤属性修饰符
     * @param superClass     clazz的父类Class，clazz父类等于该参数时，查找完后停止再向上查询
     * @return field列表
     */
    public static <T> List<Field> findPrivateFields(Class<? extends T> clazz, Class<T> superClass, Integer excludeModifer) {
        if (superClass == null || clazz == null) {
            return null;
        }
        List<Field> fieldList = new LinkedList<>();
        Class tmpClazz = clazz;
        while (true) {
            fieldList.addAll(getDeclaredPrivateFields(tmpClazz, excludeModifer));
            tmpClazz = tmpClazz.getSuperclass();
            if (tmpClazz == superClass) {
                // superClass
                fieldList.addAll(getDeclaredPrivateFields(tmpClazz, excludeModifer));
                break;
            }
        }
        return fieldList;
    }

    /**
     * 调用Getter方法.
     * 支持多级，如：对象名.对象名.方法
     */
    @SuppressWarnings("unchecked")
    public static <E> E invokeGetter(Object obj, String propertyName) {
        Object object = obj;
        for (String name : StringUtils.split(propertyName, ".")) {
            String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(name);
            object = invokeMethod(object, getterMethodName, new Class[]{}, new Object[]{});
        }
        return (E) object;
    }

    /**
     * 调用Setter方法, 仅匹配方法名。
     * 支持多级，如：对象名.对象名.方法
     */
    public static <E> void invokeSetter(Object obj, String propertyName, E value) {
        Object object = obj;
        String[] names = StringUtils.split(propertyName, ".");
        for (int i = 0; i < names.length; i++) {
            if (i < names.length - 1) {
                String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(names[i]);
                object = invokeMethod(object, getterMethodName, new Class[]{}, new Object[]{});
            } else {
                String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(names[i]);
                invokeMethodByName(object, setterMethodName, new Object[]{value});
            }
        }
    }

    /**
     * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
     */
    @SuppressWarnings("unchecked")
    public static <E> E getFieldValue(final Object obj, final String fieldName) {
        Field field = getAccessibleField(obj, fieldName);
        if (field == null) {
            logger.debug("在 [" + obj.getClass() + "] 中，没有找到 [" + fieldName + "] 字段 ");
            return null;
        }
        E result = null;
        try {
            result = (E) field.get(obj);
        } catch (IllegalAccessException e) {
            logger.error("不可能抛出的异常{}", e.getMessage());
        }
        return result;
    }

    /**
     * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
     */
    public static <E> void setFieldValue(final Object obj, final String fieldName, final E value) {
        Field field = getAccessibleField(obj, fieldName);
        if (field == null) {
            // throw new IllegalArgumentException("在 [" + obj.getClass() + "] 中，没有找到 [" + fieldName + "] 字段 ");
            logger.debug("在 [" + obj.getClass() + "] 中，没有找到 [" + fieldName + "] 字段 ");
            return;
        }
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            logger.error("不可能抛出的异常: {}", e.getMessage());
        }
    }

    /**
     * 直接调用对象方法, 无视private/protected修饰符.
     * 用于一次性调用的情况，否则应使用getAccessibleMethod()函数获得Method后反复调用.
     * 同时匹配方法名+参数类型，
     */
    @SuppressWarnings("unchecked")
    public static <E> E invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes,
                                     final Object[] args) {
        if (obj == null || methodName == null) {
            return null;
        }
        Method method = getAccessibleMethod(obj, methodName, parameterTypes);
        if (method == null) {
            logger.debug("在 [" + obj.getClass() + "] 中，没有找到 [" + methodName + "] 方法 ");
            return null;
        }
        try {
            return (E) method.invoke(obj, args);
        } catch (Exception e) {
            String msg = "method: " + method + ", obj: " + obj + ", args: " + args + "";
            throw convertReflectionExceptionToUnchecked(msg, e);
        }
    }

    /**
     * 直接调用对象方法, 无视private/protected修饰符，
     * 用于一次性调用的情况，否则应使用getAccessibleMethodByName()函数获得Method后反复调用.
     * 只匹配函数名，如果有多个同名函数调用第一个。
     */
    @SuppressWarnings("unchecked")
    public static <E> E invokeMethodByName(final Object obj, final String methodName, final Object[] args) {
        Method method = getAccessibleMethodByName(obj, methodName, args.length);
        if (method == null) {
            // 如果为空不报错，直接返回空。
            logger.debug("在 [" + obj.getClass() + "] 中，没有找到 [" + methodName + "] 方法 ");
            return null;
        }
        try {
            // 类型转换（将参数数据类型转换为目标方法参数类型）
            Class<?>[] cs = method.getParameterTypes();
            for (int i = 0; i < cs.length; i++) {
                if (args[i] != null && !args[i].getClass().equals(cs[i])) {
                    if (cs[i] == String.class) {
                        args[i] = Converter.toStr(args[i]);
                        if (StringUtils.endsWith((String) args[i], ".0")) {
                            args[i] = StringUtils.substringBefore((String) args[i], ".0");
                        }
                    } else if (cs[i] == Integer.class) {
                        args[i] = Converter.toInt(args[i]);
                    } else if (cs[i] == Long.class) {
                        args[i] = Converter.toLong(args[i]);
                    } else if (cs[i] == Double.class) {
                        args[i] = Converter.toDouble(args[i]);
                    } else if (cs[i] == Float.class) {
                        args[i] = Converter.toFloat(args[i]);
                    } else if (cs[i] == Date.class) {
                        if (args[i] instanceof String) {
                            args[i] = DateHelper.from((String) args[i], DateFormatEnum.YYYY_MM_DD_HH_MM_SS);
                        }
                    }
                }
            }
            return (E) method.invoke(obj, args);
        } catch (Exception e) {
            String msg = "method: " + method + ", obj: " + obj + ", args: " + args + "";
            throw convertReflectionExceptionToUnchecked(msg, e);
        }
    }

    /**
     * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问.
     * 如向上转型到Object仍无法找到, 返回null.
     */
    public static Field getAccessibleField(final Object obj, final String fieldName) {
        // 为空不报错。直接返回 null
        if (obj == null) {
            return null;
        }
        Assert.hasLength(fieldName, "fieldName can't be blank");
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                makeAccessible(field);
                return field;
            } catch (NoSuchFieldException e) {
                logger.error("Field not found: ", e);
            }
        }
        return null;
    }

    /**
     * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问.
     * 如向上转型到Object仍无法找到, 返回null.
     * 匹配函数名+参数类型。
     * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)
     */
    public static Method getAccessibleMethod(final Object obj, final String methodName,
                                             final Class<?>... parameterTypes) {
        // 为空不报错。直接返回 null
        if (obj == null) {
            return null;
        }
        Assert.hasLength(methodName, "methodName can't be blank");
        for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()) {
            try {
                Method method = searchType.getDeclaredMethod(methodName, parameterTypes);
                makeAccessible(method);
                return method;
            } catch (NoSuchMethodException e) {
                logger.error("Method not found: ", e);
            }
        }
        return null;
    }

    /**
     * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问.
     * 如向上转型到Object仍无法找到, 返回null.
     * 只匹配函数名。
     * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)
     */
    public static Method getAccessibleMethodByName(final Object obj, final String methodName, int argsNum) {
        // 为空不报错。直接返回 null
        if (obj == null) {
            return null;
        }
        for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()) {
            Method[] methods = searchType.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName) && method.getParameterTypes().length == argsNum) {
                    makeAccessible(method);
                    return method;
                }
            }
        }
        return null;
    }

    /**
     * 改变private/protected的方法为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨。
     */
    public static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
                && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    /**
     * 改变private/protected的成员变量为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨。
     */
    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())
                || Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    /**
     * 通过反射, 获得Class定义中声明的泛型参数的类型, 注意泛型必须定义在父类处
     * 如无法找到, 返回Object.class.
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClassGenricType(final Class clazz) {
        return getClassGenricType(clazz, 0);
    }

    /**
     * 通过反射, 获得Class定义中声明的父类的泛型参数的类型.
     * 如无法找到, 返回Object.class.
     */
    public static Class getClassGenricType(final Class clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            logger.debug(clazz.getSimpleName() + "'s superclass not ParameterizedType");
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            logger.debug("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
                    + params.length);
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            logger.debug(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
            return Object.class;
        }

        return (Class) params[index];
    }

    public static Class<?> getUserClass(Object instance) {
        if (instance == null) {
            throw new RuntimeException("Instance must not be null");
        }
        Class clazz = instance.getClass();
        if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null && !Object.class.equals(superClass)) {
                return superClass;
            }
        }
        return clazz;

    }

    /**
     * 将反射时的checked exception转换为unchecked exception.
     */
    public static RuntimeException convertReflectionExceptionToUnchecked(String msg, Exception e) {
        if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
                || e instanceof NoSuchMethodException) {
            return new IllegalArgumentException(msg, e);
        } else if (e instanceof InvocationTargetException) {
            return new RuntimeException(msg, ((InvocationTargetException) e).getTargetException());
        }
        return new RuntimeException(msg, e);
    }

    /**
     * <p>
     * 反射 method 方法名，例如 getId
     * </p>
     *
     * @param str 属性字符串内容
     * @return
     */
    public static String getMethodCapitalize(final String str) {
        return GETTER_PREFIX + StringHelper.firstUpper(str);
    }

    /**
     * 获取 public get方法的值
     *
     * @param cls
     * @param entity 实体
     * @param str    属性字符串内容
     * @return Object
     */
    public static Object getMethodValue(Class<?> cls, Object entity, String str) {
        Object obj = null;
        try {
            Method method = cls.getMethod(getMethodCapitalize(str));
            obj = method.invoke(entity);
        } catch (NoSuchMethodException e) {
            logger.warn(String.format("Warn: No such method. in %s.  Cause:", cls.getSimpleName()) + e);
        } catch (IllegalAccessException e) {
            logger.warn(String.format("Warn: Cannot execute a private method. in %s.  Cause:", cls.getSimpleName()) + e);
        } catch (InvocationTargetException e) {
            logger.warn("Warn: Unexpected exception on getMethodValue.  Cause:" + e);
        }
        return obj;
    }

    /**
     * 获取 public get方法的值
     *
     * @param entity 实体
     * @param str    属性字符串内容
     * @return Object
     */
    public static Object getMethodValue(Object entity, String str) {
        if (null == entity) {
            return null;
        }
        return getMethodValue(entity.getClass(), entity, str);
    }

    /**
     * 反射对象获取泛型
     *
     * @param clazz 对象
     * @param index 泛型所在位置
     * @return Class
     */
    public static Class getSuperClassGenricType(final Class clazz, final int index) {

        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            logger.warn(String.format("Warn: %s's superclass not ParameterizedType", clazz.getSimpleName()));
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            logger.warn(String.format("Warn: Index: %s, Size of %s's Parameterized Type: %s .", index, clazz.getSimpleName(),
                    params.length));
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            logger.warn(String.format("Warn: %s not set the actual class on superclass generic parameter",
                    clazz.getSimpleName()));
            return Object.class;
        }

        return (Class) params[index];
    }

    /**
     * <p>
     * 反射对象获取泛型
     * </p>
     *
     * @param clazz 对象
     * @param index 泛型所在位置
     * @return Class
     */
    public static Class<?> getSuperClassGenericType(final Class<?> clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            logger.warn(String.format("Warn: %s's superclass not ParameterizedType", clazz.getSimpleName()));
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            logger.warn(String.format("Warn: Index: %s, Size of %s's Parameterized Type: %s .", index,
                    clazz.getSimpleName(), params.length));
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            logger.warn(String.format("Warn: %s not set the actual class on superclass generic parameter",
                    clazz.getSimpleName()));
            return Object.class;
        }
        return (Class<?>) params[index];
    }

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Protected Methods
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    private static List<Field> getDeclaredPrivateFields(Class clazz, Integer excludeModifier) {
        if (clazz == null) {
            return new ArrayList<>();
        }
        List<Field> fieldList = new LinkedList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            if ((modifiers & Modifier.PRIVATE) != 0) {
                if (excludeModifier == null) {
                    fieldList.add(field);
                } else {
                    if ((modifiers & excludeModifier) == 0) {
                        fieldList.add(field);
                    }
                }
            }
        }
        return fieldList;
    }

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Property accessors
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */



    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Inner classes
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */


}