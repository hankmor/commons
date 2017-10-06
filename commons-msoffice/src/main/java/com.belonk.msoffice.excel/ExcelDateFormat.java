package com.belonk.msoffice.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

import java.util.HashMap;
import java.util.Map;

public class ExcelDateFormat {
    private static Map<Integer, String> excelDateFormats = new HashMap<Integer, String>();

    static {
        // yyyy"年"m"月"
        excelDateFormats.put(57, "yyyy\"年\"m\"月\"");
        // m"月"d"日"
        excelDateFormats.put(58, "m\"月\"d\"日\"");
        // yyyy"年"m"月"d"日"
        excelDateFormats.put(31, "yyyy\"年\"m\"月\"d\"日\"");
        // m/d/yy
        excelDateFormats.put(30, "m/d/yy");
        // h"时"mm"分"
        excelDateFormats.put(32, "h\"时\"mm\"分\"");
        // h"时"mm"分"ss"秒"
        excelDateFormats.put(33, "h\"时\"mm\"分\"ss\"秒\"");
        // 上午/下午h"时"mm"分"
        excelDateFormats.put(55, "上午/下午h\"时\"mm\"分\"");
        // 上午/下午h"时"mm"分"ss"秒"
        excelDateFormats.put(56, "上午/下午h\"时\"mm\"分\"ss\"秒\"");
    }

    /**
     * 单元格是否是时间格式
     *
     * @param cell 单元格
     *
     * @return true:是；false:否
     */
    public static boolean isCellDateFormatted(Cell cell) {
        if (cell == null || cell.getCellStyle() == null || !DateUtil.isValidExcelDate(cell.getNumericCellValue())) {
            return false;
        }

        boolean bDate = DateUtil.isCellDateFormatted(cell);

        if (!bDate) {
            int key = cell.getCellStyle().getDataFormat();
            bDate = excelDateFormats.containsKey(key);
        }

        return bDate;
    }
}
