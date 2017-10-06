package com.belonk.msoffice.excel;

import java.util.ArrayList;
import java.util.List;

public class ExcelRowDef {
    /**
     * 行单元格列表
     */
    private List<ExcelCellDef> excelCellDefList;

    public ExcelRowDef() {
        excelCellDefList = new ArrayList<ExcelCellDef>();
    }

    public ExcelRowDef(int size) {
        excelCellDefList = new ArrayList<ExcelCellDef>(size);
    }

    /**
     * 添加行中每个单元格
     *
     * @param cellName 单元格表头名称
     */
    public void addCell(String cellName) {
        excelCellDefList.add(new ExcelCellDef(cellName));
    }

    /**
     * 添加行中每个单元格
     *
     * @param cellName 单元格表头名称
     * @param cellOptional 单元格是否必填
     */
    public void addCell(String cellName, boolean cellOptional) {
        excelCellDefList.add(new ExcelCellDef(cellName, cellOptional));
    }

    /**
     * 添加行中每个单元格
     *
     * @param cellName 单元格表头名称
     * @param cellType 单元格类型
     * @param javaType 单元格值对应JAVA类型
     */
    public void addCell(String cellName, int cellType, Class javaType) {
        excelCellDefList.add(new ExcelCellDef(cellName, cellType, javaType));
    }

    /**
     * 添加行中每个单元格
     *
     * @param cellName 单元格表头名称
     * @param cellType 单元格类型
     * @param javaType 单元格值对应JAVA类型
     * @param cellOptional 单元格是否必填
     */
    public void addCell(String cellName, int cellType, Class javaType, boolean cellOptional) {
        excelCellDefList.add(new ExcelCellDef(cellName, cellType, javaType, cellOptional));
    }

    /**
     * 添加行中每个单元格
     *
     * @param excelCellDef 单元格对象
     */
    public void addCell(ExcelCellDef excelCellDef) {
        excelCellDefList.add(excelCellDef);
    }

    public List<ExcelCellDef> getExcelCellDefList() {
        return excelCellDefList;
    }

    /**
     * 获取行单元格数量
     *
     * @return 行单元格数量
     */
    public int getExcelCellDefListSize() {
        return excelCellDefList.size();
    }

    /**
     * 判断行单元格是否为空
     *
     * @return true:为空;false:不为空
     */
    public boolean isExcelCellDefListEmpty() {
        return excelCellDefList == null || excelCellDefList.isEmpty();
    }

    /**
     * 获取单元格
     *
     * @param index 下标
     *
     * @return 单元格
     */
    public ExcelCellDef getCell(int index) {
        return excelCellDefList.get(index);
    }
}
