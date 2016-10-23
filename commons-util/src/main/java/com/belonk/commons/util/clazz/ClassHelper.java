package com.belonk.commons.util.clazz;

import org.apache.commons.lang.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * 类处理工具类。
 * <p/>
 * <p>Created by sun on 2016/6/16.
 *
 * @author sunfuchang03@126.comfuchang03@126.com
 * @version 1.0
 * @since 1.1
 */
public class ClassHelper extends ClassUtils {
    //~ Static fields/initializers =====================================================================================
    private static final Logger LOG = LoggerFactory.getLogger(ClassHelper.class);
    //~ Instance fields ================================================================================================

    //~ Methods ========================================================================================================
    private ClassHelper() {
    }

    /**
     * 从目标包和其子包下获取所有目标类的子类，如果目标类为接口，则获取所有接口实现。
     *
     * @param targetClass 目标类，接口或者类
     * @param packageName 目标包名，如果为空，则默认从接口类所在的包和子包查找。
     * @return 目标接口实现类列表/目标类子类列表。
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static List<Class> listAssignable(Class targetClass, String packageName, boolean filterAbstract) throws IOException, ClassNotFoundException {
        if (targetClass == null) return Collections.emptyList();
        if (packageName == null || "".equals(packageName))
            packageName = targetClass.getPackage().getName();
        List<Class> result = new ArrayList<Class>();
        List<Class> classes = getClasses(packageName);
        for (int i = 0; i < classes.size(); i++) {
            Class clazz = classes.get(i);
            if (targetClass.isAssignableFrom(clazz)) {
                if (!targetClass.equals(clazz)) {
                    if (filterAbstract) {
                        if (!Modifier.isAbstract(clazz.getModifiers()))
                            result.add(clazz);
                    } else
                        result.add(clazz);
                }
            }
        }
        return result;
    }

    /**
     * 根据报名查找包和子包的所有类。
     *
     * @param packageName 包名
     * @return 类列表
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static List<Class> getClasses(String packageName) throws IOException, ClassNotFoundException {
        if (packageName == null || "".equals(packageName))
            return Collections.emptyList();
        ClassLoader classLoader = ClassHelper.class.getClassLoader();
        ClassLoader classLoader1 = Thread.currentThread().getContextClassLoader();

        List<File> dirs = new ArrayList<File>();
        String path = packageName.replaceAll("\\.", "/");
        Enumeration<URL> urls = classLoader.getResources(path);
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            dirs.add(new File(url.getFile()));
        }
        List<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClass(directory, packageName));
        }
        return classes;
    }

    /**
     * 查找目录下的所有类，包括子目录。
     *
     * @param directory   查找目录
     * @param packageName 类报名
     * @return 类类别
     * @throws ClassNotFoundException
     */
    public static List<Class> findClass(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                // 递归查找
                classes.addAll(findClass(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + "." + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}
