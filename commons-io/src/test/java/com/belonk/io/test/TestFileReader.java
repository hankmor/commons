package com.belonk.io.test;

import com.belonk.io.FileLineReader;
import com.belonk.io.MultiThreadFileLineReader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by sun on 2017/9/20.
 *
 * @author sunfuchang03@126.com
 * @version 1.0
 * @since 1.0
 */
public class TestFileReader {
    //~ Static fields/initializers =====================================================================================


    //~ Instance fields ================================================================================================
    private static volatile int idx = 0;
    //~ Constructors ===================================================================================================


    //~ Methods ========================================================================================================
    public static void main(String[] args) throws Exception {
        // 单个文件读取测试
//        System.out.println();
//        FileLineReader.Builder builder = new FileLineReader.Builder("D:\\work\\99-其他\\机票\\dataNFD_20170920.txt", line -> {
//            // 逻辑
////            if (line.matches("^[a-z|A-Z].*")) {
////
////            } else {
//            System.out.println(line);
////            }
//
//        });
//        builder.charset("gbk").bufferSize(1024);
//        FileLineReader fileLineReader = builder.build();
//        long start = System.currentTimeMillis();
//        long readSize = fileLineReader.readFull();
////        long readSize = fileLineReader.read(0, 1024 * 1024);
//        long total = fileLineReader.getTotalCount();
//        fileLineReader.shutdown(); // 关闭流
//        long end = System.currentTimeMillis();
//        System.out.println("时间消耗  ： " + (end - start) + "ms.");
//        System.out.println("读取字节数： " + readSize);
//        System.out.println("读取总行数： " + total);

        // 多线程读取测试
        MultiThreadFileLineReader.Builder builder = new MultiThreadFileLineReader.Builder("D:\\work\\99-其他\\机票\\dataNFD_20170920.txt", line -> {
            // 逻辑
//            if (line.matches("^[a-z|A-Z].*")) {
//
//            } else {
            System.out.println(line);
//            }

        });
        builder.threadSize(10).charset("gbk").bufferSize(1024);
        MultiThreadFileLineReader fileLineReader = builder.build();
        long start = System.currentTimeMillis();
//        long readSize = fileLineReader.readFull();
//        long readSize = fileLineReader.read(0, 1024 * 1024);
        fileLineReader.read();
        long total = fileLineReader.getTotalCount();
//        fileLineReader.shutdown(); // 关闭流
        long end = System.currentTimeMillis();
        System.out.println("时间消耗  ： " + (end - start) + "ms.");
//        System.out.println("读取字节数： " + readSize);
        System.out.println("读取总行数： " + total);
    }


}
