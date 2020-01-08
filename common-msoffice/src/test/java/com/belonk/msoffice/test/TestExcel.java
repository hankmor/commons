package com.belonk.msoffice.test;

import com.belonk.msoffice.excel.ExcelConfig;
import com.belonk.msoffice.excel.ExcelProcessor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by sun on 2020/1/8.
 *
 * @author sunfuchang03@126.com
 * @since 1.0
 */
public class TestExcel {
    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Static fields/constants/initializer
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */



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
     * Methods
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    @Test
    public void test() throws IOException {
        SXSSFWorkbook wb = new SXSSFWorkbook(10);
        int size = 10000;
        int maxRows = 1000;
        int cols = 5;
        int sheetNo = (int) Math.ceil(size / maxRows);
        for (int index = 0; index < sheetNo; index++) {
            SXSSFSheet sheet = wb.createSheet();
            wb.setSheetName(index, "sheet_" + index);

            Row row = sheet.createRow(0);
            // 写入各个字段的列头名称
            for (int col = 0; col < cols; col++) {
                // 创建列
                Cell cell = row.createCell(col);
                // 写入列信息
                cell.setCellValue("col_" + col);
            }
            int startNo = index * maxRows;
            int endNo = Math.min(startNo + maxRows, size);
            for (int i = startNo; i < endNo; i++) {
                row = sheet.createRow(i + 1 - startNo);
                int column = 0;
                for (int col = 0; col < cols; col++) {
                    // 创建cell
                    Cell cell = row.createCell(column++);
                    // 用于读取对象中的属性
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue("row_" + i + "_" + col);
                }
            }
        }
        OutputStream out = new FileOutputStream("test.xlsx");
        wb.write(out);
    }

    @Test
    public void test1() {
        ExcelConfig config = new ExcelConfig();
        config.setMaxRows(3);
        ExcelProcessor processor = new ExcelProcessor(config);

        Random ageRandom = new Random();
        Random deptRandom = new Random();
        Random statusRandom = new Random();
        List<Dept> depts = Arrays.asList(
                new Dept(1L, "开发部"),
                new Dept(2L, "市场部"),
                new Dept(3L, "客户部")
        );

        int size = 10;
        List<User> users = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            User user = new User();
            user.setId((long) i);
            user.setName("user_" + i);
            user.setAge(ageRandom.nextInt(100));
            user.setStatus(statusRandom.nextInt(2));
            user.setDept(depts.get(deptRandom.nextInt(depts.size())));
            users.add(user);
        }

        String savePath = "/root";
        String fileName = "用户信息";
        processor.export(savePath, fileName, "用户列表", User.class, users);
    }
}