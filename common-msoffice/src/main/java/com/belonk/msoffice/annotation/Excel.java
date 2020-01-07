package com.belonk.msoffice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义导出Excel数据注解
 *
 * @author sun
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel {
    /**
     * Excel中的标题.
     */
    String title();

    /**
     * 关联对象属性名称，则取关联对象的字段名称, 如果是多级则以小数点隔开
     */
    String associate() default "";

    /**
     * 日期格式, 如: yyyy-MM-dd
     */
    String dateFormat() default "";

    /**
     * 内容格式，格式key=value,key=value, 如: 0=男,1=女,2=未知
     */
    String contentFormat() default "";

    /**
     * 导出类型（0数字 1字符串）
     */
    ColumnType cellType() default ColumnType.STRING;

    /**
     * 导出时在excel中每个列的高度 单位为字符
     */
    double height() default 16;

    /**
     * 导出时在excel中每个列的宽 单位为字符
     */
    double width() default 16;

    /**
     * 内容添加后缀, 如人民币100可以转换为¥100
     */
    String prefix() default "";

    /**
     * 内容添加后缀, 如人民币100可以转换为100元
     */
    String suffix() default "";

    /**
     * 当值为空时,字段的默认值
     */
    String defaultValue() default "";

    /**
     * 提示信息
     */
    String prompt() default "";

    /**
     * 设置只能选择不能输入的列内容.
     */
    String[] combo() default {};

    /**
     * 字段类型（0：导出导入；1：仅导出；2：仅导入）
     */
    Type type() default Type.ALL;

    boolean export() default true;

    enum Type {
        /**
         * 全部
         */
        ALL(0),
        /**
         * 导出
         */
        EXPORT(1),
        /**
         * 导入
         */
        IMPORT(2);

        private final int value;

        Type(int value) {
            this.value = value;
        }

        int value() {
            return this.value;
        }
    }

    enum ColumnType {
        /**
         * 数字
         */
        NUMERIC(0),
        /**
         * 字符串
         */
        STRING(1);
        private final int value;

        ColumnType(int value) {
            this.value = value;
        }

        int value() {
            return this.value;
        }
    }
}