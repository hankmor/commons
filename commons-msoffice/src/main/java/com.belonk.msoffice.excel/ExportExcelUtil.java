package com.belonk.msoffice.excel;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jxl.Workbook;
import jxl.write.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * excel处理工具类。
 *
 * @author sunfuchang03@126.comfuchang03@126.com
 * @version 1.0
 * @since 1.0
 */
public class ExportExcelUtil {
    /**
     * 将json格式的内容导出到输出流。
     *
     * @param outputStream 输出流
     * @param content      json格式的excel内容
     * @throws IOException
     * @throws WriteException
     */
    public void exportWithJsonContent(OutputStream outputStream, String content) throws IOException, WriteException {
        WritableWorkbook workbook = Workbook.createWorkbook(outputStream);
        JSONArray jsonArray = JSONArray.parseArray(content);
        try {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                WritableSheet sheet = workbook.createSheet("sheetname", i);
                JSONArray jsonTable = jsonObject.getJSONArray("data");
                int colLenth = jsonTable.getJSONArray(0).toArray().length;
                int columnBestWidth[] = new int[colLenth];    //保存最佳列宽数据的数组
                for (int j = 0; j < jsonTable.size(); j++) {
                    JSONArray jsonRow = jsonTable.getJSONArray(j);
                    Object[] values = jsonRow.toArray();
                    for (int k = 0; k < values.length; k++) {
                        if (values[k] == null) {
                            values[k] = "";
                        }
                        Label label = new Label(k, j, values[k].toString());
                        WritableCellFormat writableCellFormat = new WritableCellFormat();

                        label.setCellFormat(writableCellFormat);

                        int width = values[k].toString().length() + getChineseNum(values[k].toString());    ///汉字占2个单位长度
                        if (columnBestWidth[k] < width) {   ///求取到目前为止的最佳列宽
                            if (width > 20) {
                                columnBestWidth[k] = 20;
                            } else {
                                columnBestWidth[k] = width;
                            }
                        }
                        sheet.addCell(label);
                    }
                }

                for (int j = 0; j < columnBestWidth.length; j++) {
                    sheet.setColumnView(j, columnBestWidth[j] + 2);//设置成最佳列宽后加2的长度
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workbook.write();
            workbook.close();
            outputStream.close();
        }
    }

    private int getChineseNum(String context) {
        int lenOfChinese = 0;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(context);
        while (m.find()) {
            lenOfChinese++;
        }
        return lenOfChinese;
    }
}
