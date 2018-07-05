package com.belonk.msoffice.excel.util;

import com.belonk.commons.util.collection.CollectionHelper;
import com.belonk.msoffice.excel.ExcelCellDef;
import com.belonk.msoffice.excel.ExcelConstants;
import com.belonk.msoffice.excel.ExcelDateFormat;
import com.belonk.msoffice.excel.ExcelRowDef;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class POIExcelUtil {
    private static Logger log = LoggerFactory.getLogger(POIExcelUtil.class);

    /**
     * 根据文件流获取Excel工作簿
     *
     * @param file 文件
     * @return Excel工作簿
     */
    public static Workbook createWorkbook(File file) {
        Workbook workbook = null;
        try {
            InputStream inputStream = new FileInputStream(file);
            workbook = createWorkbook(inputStream);
        } catch (IOException e) {
            log.error("IO Exception: ", e);
        }
        return workbook;
    }

    /**
     * 根据文件流获取Excel工作簿
     *
     * @param inputStream 文件流
     * @return Excel工作簿
     */
    public static Workbook createWorkbook(InputStream inputStream) {
        if (!inputStream.markSupported()) {
            inputStream = new PushbackInputStream(inputStream, 8);
        }
        try {
            // .xls
            if (POIFSFileSystem.hasPOIFSHeader(inputStream)) {
                return new HSSFWorkbook(inputStream);
            }
            // .xlsx
            if (POIXMLDocument.hasOOXMLHeader(inputStream)) {
                return new XSSFWorkbook(inputStream);
            }
        } catch (IOException e) {
            log.error("IO Exception: ", e);
        }
        return null;
    }

    /**
     * 创建excel工作簿(.xls)
     *
     * @return 新创建的excel工作薄(.xls)
     */
    public static HSSFWorkbook createHSSFWorkbook() {
        return new HSSFWorkbook();
    }

    /**
     * 创建excel工作簿(.xlsx)
     *
     * @return 新创建的excel工作薄(.xlsx)
     */
    public static XSSFWorkbook createXSSFWorkbook() {
        return new XSSFWorkbook();
    }

    /**
     * 创建Excel工作簿(.xls)
     *
     * @param inputStream 输入文件流
     * @return excel工作薄(.xls)
     */
    public static HSSFWorkbook createHSSFWorkbook(InputStream inputStream) {
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook(inputStream);
        } catch (IOException e) {
            log.error("IO Exception: ", e);
        }
        return workbook;
    }

    /**
     * 创建Excel工作簿(.xls)
     *
     * @param file 文件
     * @return excel工作薄(.xls)
     */
    public static HSSFWorkbook createHSSFWorkbook(File file) {
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook(new FileInputStream(file));
        } catch (IOException e) {
            log.error("IO Exception: ", e);
        }
        return workbook;
    }

    /**
     * 创建Excel工作簿(.xls)
     *
     * @param filePath 文件路径
     * @return excel工作薄(.xls)
     */
    public static HSSFWorkbook createHSSFWorkbook(String filePath) {
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook(new FileInputStream(filePath));
        } catch (IOException e) {
            log.error("IO Exception: ", e);
        }
        return workbook;
    }

    /**
     * 创建Excel工作簿(.xlsx)
     *
     * @param inputStream 输入文件流
     * @return excel工作薄(.xls)
     */
    public static XSSFWorkbook createXSSFWorkbook(InputStream inputStream) {
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            log.error("IO Exception: ", e);
        }
        return workbook;
    }

    /**
     * 创建Excel工作簿(.xlsx)
     *
     * @param file 文件
     * @return excel工作薄(.xlsx)
     */
    public static XSSFWorkbook createXSSFWorkbook(File file) {
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(new FileInputStream(file));
        } catch (IOException e) {
            log.error("IO Exception: ", e);
        }
        return workbook;
    }

    /**
     * 创建Excel工作簿(.xlsx)
     *
     * @param filePath 文件路径
     * @return excel工作薄(.xlsx)
     */
    public static XSSFWorkbook createXSSFWorkbook(String filePath) {
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(new FileInputStream(filePath));
        } catch (IOException e) {
            log.error("IO Exception: ", e);
        }
        return workbook;
    }

    /**
     * 设置表头
     *
     * @param sheet  工作表
     * @param titles 表头名称
     */
    public static void setHeader(Sheet sheet, String[] titles) {
        setHeader(sheet, titles, null);
    }

    /**
     * 设置表头
     *
     * @param sheet     工作表
     * @param titles    表头名称
     * @param cellStyle 单元格样式
     */
    public static void setHeader(Sheet sheet, String[] titles, CellStyle cellStyle) {
        // 默认设置表头为第一行
        Row titleRow = sheet.createRow(ExcelConstants.DEFAUT_HEADER_SHOW_LINE);
        for (int i = 0; i < titles.length; i++) {
            Cell cell = titleRow.createCell(i, HSSFCell.CELL_TYPE_STRING);// 创建数据列
            if (cellStyle != null) {
                cell.setCellStyle(cellStyle);
            }
            cell.setCellValue(titles[i]);
        }
    }

    /**
     * 设置数据
     *
     * @param sheet    工作表
     * @param dataList 数据集合
     */
    public static void setWorkbookData(Sheet sheet, List<List<Object>> dataList) {
        setWorkbookData(sheet, dataList, null);
    }

    /**
     * 设置数据
     *
     * @param sheet     工作表
     * @param dataList  数据集合
     * @param cellStyle 单元格样式
     */
    public static void setWorkbookData(Sheet sheet, List<List<Object>> dataList, CellStyle cellStyle) {
        if (dataList == null || dataList.isEmpty())
            return;
        // 数据从第二行开始设置
        int line = ExcelConstants.DEFAUT_HEADER_SHOW_LINE + 1;
        for (List<Object> datas : dataList) {
            Row dataRow = sheet.createRow(line++);
            int col = 0;
            for (Object data : datas) {
                // 单元格从0开始
                Cell cell = dataRow.createCell(col++);
                if (cellStyle != null) {
                    cell.setCellStyle(cellStyle);
                }
                setCellValue(cell, data);
            }
        }
    }

    /**
     * 设置单元格的值
     *
     * @param cell   单元格
     * @param object 值对象
     */
    private static void setCellValue(Cell cell, Object object) {
        try {
            if (null != object) {
                if (object instanceof Boolean) {
                    cell.setCellValue((Boolean) object);
                } else if (object instanceof Date) {
                    cell.setCellValue((Date) object);
                } else if (object instanceof Calendar) {
                    cell.setCellValue((Calendar) object);
                } else if (object instanceof Double) {
                    cell.setCellValue((Double) object);
                } else if (object instanceof HSSFRichTextString) {
                    cell.setCellValue((HSSFRichTextString) object);
                } else {
                    cell.setCellValue(object.toString());
                }
            } else {
                cell.setCellType(Cell.CELL_TYPE_BLANK);
            }
        } catch (Exception e) {
            cell.setCellErrorValue((byte) ErrorConstants.ERROR_VALUE);
        }
    }

    /**
     * 设置错误行数据
     *
     * @param sheet    工作表
     * @param dataList 错误行数据集合
     */
    public static void setErrorWorkbookData(Workbook workbook, Sheet sheet, List<List<Object>> dataList) {
        if (dataList == null || dataList.isEmpty())
            return;
        // 数据从第二行开始设置
        int line = ExcelConstants.DEFAUT_HEADER_SHOW_LINE + 1;
        for (List<Object> datas : dataList) {
            Row dataRow = sheet.createRow(line++);
            int col = 0;
            for (Object data : datas) {
                // 单元格从0开始
                Cell cell = dataRow.createCell(col++);
                if (null == data) {
                    cell.setCellType(Cell.CELL_TYPE_BLANK);
                } else if (data instanceof Cell) {
                    Cell errorCell = (Cell) data;
                    cell.setCellType(errorCell.getCellType());
                    CellStyle cellStyle = workbook.createCellStyle();
                    cellStyle.setDataFormat(errorCell.getCellStyle().getDataFormat());
                    cell.setCellStyle(cellStyle);
                    switch (errorCell.getCellType()) {
                        case Cell.CELL_TYPE_BOOLEAN:
                            cell.setCellValue(errorCell.getBooleanCellValue());
                            break;
                        case Cell.CELL_TYPE_ERROR:
                            cell.setCellErrorValue(errorCell.getErrorCellValue());
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            if (DateUtil.isCellDateFormatted(errorCell)) {
                                cell.setCellValue(errorCell.getDateCellValue());
                            } else {
                                cell.setCellValue(errorCell.getNumericCellValue());
                            }
                            break;
                        case Cell.CELL_TYPE_STRING:
                            cell.setCellValue(errorCell.getStringCellValue());
                            break;
                        default:
                            cell.setCellValue(errorCell.toString());
                            break;
                    }
                } else {
                    cell.setCellValue(String.valueOf(data));
                }
            }

            // 设置最后一列错误信息字体为红色
            Cell errorMsgCell = dataRow.getCell(dataRow.getLastCellNum() - 1);
            CellStyle cellStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setColor(HSSFColor.RED.index);
            cellStyle.setFont(font);
            errorMsgCell.setCellStyle(cellStyle);
        }
    }

    /**
     * 获取默认单元格样式
     *
     * @param workbook 工作簿
     * @param isFull   是否填充单元格
     * @return 单元格样式
     */
    public static CellStyle getCellStyle(Workbook workbook, boolean isFull) {
        CellStyle cellStyle = workbook.createCellStyle();
        if (isFull) {
            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 填充单元格
            cellStyle.setFillForegroundColor(HSSFColor.AQUA.index); // 填绿色
        }
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBottomBorderColor((short) 0);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setLeftBorderColor((short) 0);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setRightBorderColor((short) 0);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor((short) 0);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中显示
        return cellStyle;
    }

    /**
     * 返回Excel工作表的所有行数
     *
     * @param sheet 工作表
     * @return 工作表的行数
     */
    public static int getSheetRows(Sheet sheet) {
        return sheet.getPhysicalNumberOfRows();
    }

    /**
     * 获取Excel工作表的全部数据
     *
     * @param sheet       Excel工作表
     * @param excelRowDef excel行对象
     * @param errorList   错误记录信息集合 <br>两种格式：<br>1、当前行所有单元格对象+错误原因列表 <br>2、错误原因信息
     * @return Excel工作表的数据集合，无匹配数据时返回空集合 <br>返回null表示的情况(原因保存在errorList中)：<br>1、当前excel工作表没有数据(包括空Excel或只有1行的情况，默认第一行为表头) <br>2、excel单元格与行模板表头列数量不匹配 <br>3、起始行号超过excel行数
     */
    public static List<List<Object>> getAllSheetData(Sheet sheet, ExcelRowDef excelRowDef, List<Object> errorList) {
        return getSheetData(sheet, excelRowDef, ExcelConstants.DEFAUT_DATA_SHOW_LINE, errorList);
    }

    /**
     * 获取Excel工作表的数据
     *
     * @param sheet       Excel工作表
     * @param excelRowDef excel行对象
     * @param startRow    起始行号
     * @param errorList   错误记录信息集合 <br>两种格式：<br>1、当前行所有单元格对象+错误原因列表 <br>2、错误原因信息
     * @return Excel工作表的数据集合，无匹配数据时返回空集合 <br>返回null表示的情况(原因保存在errorList中)：<br>1、当前excel工作表没有数据(包括空Excel或只有1行的情况，默认第一行为表头) <br>2、excel单元格与行模板表头列数量不匹配 <br>3、起始行号超过excel行数
     */
    public static List<List<Object>> getSheetData(Sheet sheet, ExcelRowDef excelRowDef, int startRow, List<Object> errorList) {
        // Excel合法性验证
        if (!isRightExcelSheet(sheet, excelRowDef, startRow, errorList)) {
            return null;
        }

        // 表头匹配验证
        Row headerRow = sheet.getRow(0);
        for (int i = 0; i < excelRowDef.getExcelCellDefListSize(); i++) {
            Cell headerCell = headerRow.getCell(i);
            if (isCellEmpty(headerCell) || !excelRowDef.getCell(i).getName().equals(headerCell.toString())) {
                errorList.add("表头与模板不匹配，解析失败！");
                return null;
            }
        }

        // 数据集合
        List<List<Object>> list = new ArrayList<List<Object>>();

        for (int i = startRow; i < getSheetRows(sheet); i++) {
            Row row = sheet.getRow(i);
            List<Object> cellValueList = new ArrayList<Object>();
            List<Object> errorValueList = new ArrayList<Object>();
            StringBuilder errorMsg = new StringBuilder();
            int cellCount = excelRowDef.getExcelCellDefListSize();
            int rowMaxCellNum = row.getLastCellNum();
            int indexNum = cellCount;
            if (cellCount < rowMaxCellNum) {
                indexNum = rowMaxCellNum;
            }

            // 当前行不是空行
            if (!isEmptyRow(row, rowMaxCellNum)) {
                for (int j = 0; j < indexNum; j++) {
                    Cell cell = row.getCell(j);
                    ExcelCellDef cellDef = null;
                    if (j < cellCount) {
                        cellDef = excelRowDef.getCell(j);
                    }

                    setCellData(cell, cellDef, cellValueList, errorValueList, errorMsg);
                }

                // 数据列比表头列多
                if (cellCount < rowMaxCellNum) {
                    cellValueList.clear();
                    errorMsg.delete(0, errorMsg.length());
                    errorMsg.append("当前行数据与表头不匹配！，");
                }

                if (StringUtils.isNotBlank(errorMsg.toString())) {
                    cellValueList.clear();
                    // 去除最后一个空格
                    errorValueList.add(errorMsg.deleteCharAt(errorMsg.length() - 1).toString());
                    errorList.add(errorValueList);
                }

                if (CollectionHelper.notNull(cellValueList)) {
                    list.add(cellValueList);
                }
            }
        }

        return list;
    }

    /**
     * 验证是否是正确的EXCEL
     *
     * @param sheet       Excel工作表
     * @param excelRowDef excel行对象
     * @param startRow    起始行号
     * @param errorList   错误记录信息
     * @return 验证结果 true:正确;false:不正确
     */
    private static boolean isRightExcelSheet(Sheet sheet, ExcelRowDef excelRowDef, int startRow, List<Object> errorList) {
        int rows = getSheetRows(sheet);

        if (rows == 0) {
            errorList.add("Excel无数据！");
            return false;
        }

        if (sheet.getRow(0).getLastCellNum() != excelRowDef.getExcelCellDefListSize()) {
            errorList.add("表头与模板不匹配，解析失败！");
            return false;
        }

        if (rows == 1) {
            errorList.add("Excel无数据！");
            return false;
        }

        if (startRow >= rows) {
            errorList.add("起始行号超过实际数据行数！");
            return false;
        }

        return true;
    }

    /**
     * 封装单元格数据
     *
     * @param cell           单元格
     * @param cellDef        单元格对象
     * @param cellValueList  单元格数据存放List
     * @param errorValueList 错误单元格数据存放List
     * @param errorMsg       错误信息
     */
    private static void setCellData(Cell cell, ExcelCellDef cellDef, List<Object> cellValueList, List<Object> errorValueList, StringBuilder errorMsg) {
        if (isCellEmpty(cell)) { // 单元格为空
            errorValueList.add(null);

            // 必填项
            if (cellDef.isOptional()) {
                errorMsg.append("单元格\"");
                errorMsg.append(cellDef.getName());
                errorMsg.append("\"必须填写，");
            } else {
                cellValueList.add(ExcelConstants.BLANK);
            }
        } else if (null == cellDef) {
            errorValueList.add(cell);
        } else if (cellDef.getType() == ExcelConstants.UNCHECKED ||
                cell.getCellType() == cellDef.getType()) { // 不检查类型的单元格或者类型匹配的单元格
            errorValueList.add(cell);

            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                try {
                    if (ExcelDateFormat.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
                        if (Date.class.getName().equals(cellDef.getJavaType().getName())) {
                            cellValueList.add(cell.getDateCellValue());
                        } else {
                            SimpleDateFormat sdf = null;
                            if (cell.getCellStyle().getDataFormat() == BuiltinFormats.getBuiltinFormat("h:mm")) {
                                sdf = new SimpleDateFormat("HH:mm");
                            } else { // 日期
                                sdf = new SimpleDateFormat("yyyy-MM-dd");
                            }
                            cellValueList.add(sdf.format(cell.getDateCellValue()));
                        }
                    } else {
                        double value = cell.getNumericCellValue();
                        int valueInt = (int) value;
                        DecimalFormat format = new DecimalFormat("#");
                        // 是否是整数
                        boolean isIntegerNum = false;
                        if (value == valueInt) {
                            isIntegerNum = true;
                        }

                        if (Long.class.getName().equals(cellDef.getJavaType().getName())) {
                            if (isIntegerNum) {
                                cellValueList.add(Long.valueOf(format.format(value)));
                            } else {
                                errorMsg.append("单元格\"");
                                errorMsg.append(cellDef.getName());
                                errorMsg.append("\"格式错误，");
                            }
                        } else if (Integer.class.getName().equals(cellDef.getJavaType().getName())) {
                            if (isIntegerNum) {
                                cellValueList.add(Integer.valueOf(format.format(value)));
                            } else {
                                errorMsg.append("单元格\"");
                                errorMsg.append(cellDef.getName());
                                errorMsg.append("\"格式错误，");
                            }
                        } else if (Short.class.getName().equals(cellDef.getJavaType().getName())) {
                            if (isIntegerNum) {
                                cellValueList.add(Short.valueOf(format.format(value)));
                            } else {
                                errorMsg.append("单元格\"");
                                errorMsg.append(cellDef.getName());
                                errorMsg.append("\"格式错误，");
                            }
                        } else if (Byte.class.getName().equals(cellDef.getJavaType().getName())) {
                            if (isIntegerNum) {
                                cellValueList.add(Byte.valueOf(format.format(value)));
                            } else {
                                errorMsg.append("单元格\"");
                                errorMsg.append(cellDef.getName());
                                errorMsg.append("\"格式错误，");
                            }
                        } else if (Double.class.getName().equals(cellDef.getJavaType().getName())) {
                            cellValueList.add(value);
                        } else if (Float.class.getName().equals(cellDef.getJavaType().getName())) {
                            cellValueList.add(Double.valueOf(value).floatValue());
                        } else {
                            cellValueList.add(String.valueOf(value));
                        }
                    }
                } catch (Exception e) {
                    log.info("格式转换错误！");
                    errorMsg.append("单元格\"");
                    errorMsg.append(cellDef.getName());
                    errorMsg.append("\"格式错误，");
                }
            } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
                cellValueList.add(cell.toString().toLowerCase());
            } else {
                cellValueList.add(cell.toString());
            }
        } else { // 不匹配的单元格
            log.info("当前的类型:{}, 需要的类型:{}", cell.getCellType(), cellDef.getType());
            errorMsg.append("单元格\"");
            errorMsg.append(cellDef.getName());
            errorMsg.append("\"格式错误，");

            errorValueList.add(cell);
        }
    }

    /**
     * 判断当前行是否是空行
     *
     * @param row       当前行
     * @param cellCount 当前行单元格数量
     * @return true：是；false：否
     */
    private static boolean isEmptyRow(Row row, int cellCount) {
        boolean isEmpty = true;
        for (int j = 0; j < cellCount; j++) {
            if (!isCellEmpty(row.getCell(j))) {
                isEmpty = false;
                break;
            }
        }
        return isEmpty;
    }

    /**
     * 判断单元格是否为空
     *
     * @param cell 单元格
     * @return true：是；false：否
     */
    private static boolean isCellEmpty(Cell cell) {
        return null == cell || cell.getCellType() == Cell.CELL_TYPE_BLANK;
    }

    public static void main(String[] args) throws IOException {
        Workbook hssfWorkbook = createHSSFWorkbook();

        String[] headers = {
                "年级",
                "期班",
                "班级",
                "学生类型",
                "组织架构",
                "入校年份",
                "护照号",
                "学号",
                "姓名",
                "身份证号码",
                "性别",
                "民族",
                "籍贯",
                "政治面貌",
                "家庭通讯地址",
                "邮编",
                "招飞单位"
        };
        List<List<Object>> datas = new LinkedList<>();
        ExcelRowDef excelRowDef = new ExcelRowDef(headers.length);
        List<Object> row1 = new LinkedList<>();
        List<Object> row2 = new LinkedList<>();
        datas.add(row1);
        datas.add(row2);
        for (int i = 0; i < headers.length; ++i) {
            if (i == 1 || i >= 3 && i <= 6) {
                excelRowDef.addCell(headers[i]);
            } else {
                excelRowDef.addCell(headers[i], true);
            }
            row1.add("row[1]cell[" + i + "]");
            row2.add("row[2]cell[" + i + "]");
        }
        Sheet sheet = hssfWorkbook.createSheet("test");
        CellStyle cellStyle = hssfWorkbook.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        Font font = hssfWorkbook.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);
        setHeader(sheet, headers, cellStyle);
        setWorkbookData(sheet, datas);

        File file = new File("E://学生名单.xls");
        OutputStream os = new FileOutputStream(file);
        hssfWorkbook.write(os);
        os.flush();
        os.close();
    }
}