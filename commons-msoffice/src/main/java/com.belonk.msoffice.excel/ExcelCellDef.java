package com.belonk.msoffice.excel;

public class ExcelCellDef {
    /**
     * 单元格表头名称
     */
    private String name;
    /**
     * 单元格类型，默认-1（不检查）
     */
    private int type = ExcelConstants.UNCHECKED;
    /**
     * 是否必填，默认false
     */
    private boolean optional = false;
    /**
     * 单元格值对应JAVA类型，默认String
     */
    private Class javaType = String.class;

    public ExcelCellDef(String name) {
        super();
        this.name = name;
    }

    public ExcelCellDef(String name, boolean optional) {
        super();
        this.name = name;
        this.optional = optional;
    }

    public ExcelCellDef(String name, int type, Class javaType) {
        super();
        this.name = name;
        this.type = type;
        this.javaType = javaType;
    }

    public ExcelCellDef(String name, int type, Class javaType, boolean optional) {
        super();
        this.name = name;
        this.type = type;
        this.javaType = javaType;
        this.optional = optional;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    public Class getJavaType() {
        return javaType;
    }

    public void setJavaType(Class javaType) {
        this.javaType = javaType;
    }
}
