package com.belonk.io.test;

import com.belonk.io.FileLineReader;
import com.belonk.io.MultiThreadFileLineReader;

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


    //~ Constructors ===================================================================================================


    //~ Methods ========================================================================================================
    public static void main(String[] args) throws Exception {
        // 单个文件读取测试
        /*System.out.println();
        FileLineReader.Builder builder = new FileLineReader.Builder("D:\\work\\99-其他\\机票\\dataNFD_20170920.txt", line -> {
            // 逻辑
            // System.out.println(line);
        });
        builder.charset("gbk").bufferSize(1024);
        FileLineReader fileLineReader = builder.build();
        long start = System.currentTimeMillis();
        long readSize = fileLineReader.read();
        long total = fileLineReader.getTotalCount();
        fileLineReader.shutdown(); // 关闭流
        long end = System.currentTimeMillis();
        System.out.println("时间消耗 ：" + (end - start) + "ms.");
        System.out.println("读取字节数 ：" + readSize);
        System.out.println("读取总行数 ：" + total);*/
        //  时间消耗  ： 1369ms.
        //  读取字节数： 39231203
        //  读取总行数： 337690

        // 多线程读取测试
        MultiThreadFileLineReader.Builder builder = new MultiThreadFileLineReader.Builder("D:\\work\\99-其他\\机票\\dataNFD_20170920.txt", line -> {
            // 逻辑
            // System.out.println(line);
        });
        builder.threadSize(10).charset("gbk").bufferSize(1024);
        MultiThreadFileLineReader fileLineReader = builder.build();
        fileLineReader.read();
        // fileLineReader.shutdown(); // 关闭流，这里线程执行，不能关闭，由线程执行
        // 2018-01-16 15:44:54.034 INFO  - [com.belonk.io.MultiThreadFileLineReader] Use time : 509ms.
        // 2018-01-16 15:44:54.034 INFO  - [com.belonk.io.MultiThreadFileLineReader] Read total lines : 337690
    }
}
