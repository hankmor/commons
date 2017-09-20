package com.belonk.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by sun on 2017/9/20.
 *
 * @author sunfuchang03@126.com
 * @version 1.0
 * @since 1.0
 */
public class MultiThreadFileLineReader extends FileLineReader {
    //~ Static fields/initializers =====================================================================================
    private static Logger log = LoggerFactory.getLogger(MultiThreadFileLineReader.class);

    //~ Instance fields ================================================================================================
    private ExecutorService executorService;
    private Set<StartEndPair> startEndPairs;
    private CyclicBarrier cyclicBarrier;
    private int threadSize = 1;

    //~ Constructors ===================================================================================================
    private MultiThreadFileLineReader(File file, LineHandler handler, String charset, int bufferSize, int threadSize) {
        super(file, handler, charset, bufferSize);
        this.threadSize = threadSize;
        this.executorService = Executors.newFixedThreadPool(threadSize);
        startEndPairs = new HashSet<StartEndPair>();
    }

    //~ Methods ========================================================================================================

    @Override
    public long read() {
        long everySize = this.fileLength / this.threadSize;
        try {
            calculateStartEnd(0, everySize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final long startTime = System.currentTimeMillis();
        // 所有线程都执行完后，选择一个线程来执行Ruunable的代码
        cyclicBarrier = new CyclicBarrier(startEndPairs.size(), new Runnable() {
            @Override
            public void run() {
                log.info("Use time : " + (System.currentTimeMillis() - startTime) + "ms.");
                log.info("Read total lines : " + counter.get());
                shutdown(); // 关闭资源
            }
        });
        for (StartEndPair pair : startEndPairs) {
            log.debug("Slice size : " + pair);
            this.executorService.execute(new SliceReaderTask(pair));
        }
        return fileLength;
    }

    private void calculateStartEnd(long start, long size) throws IOException {
        if (start > fileLength - 1) {
            return;
        }
        StartEndPair pair = new StartEndPair();
        pair.start = start;
        long endPosition = start + size - 1;
        if (endPosition >= fileLength - 1) {
            pair.end = fileLength - 1;
            startEndPairs.add(pair);
            return;
        }

        randomAccessFile.seek(endPosition);
        byte tmp = (byte) randomAccessFile.read();
        while (tmp != '\n' && tmp != '\r') {
            endPosition++;
            if (endPosition >= fileLength - 1) {
                endPosition = fileLength - 1;
                break;
            }
            randomAccessFile.seek(endPosition);
            tmp = (byte) randomAccessFile.read();
        }
        pair.end = endPosition;
        startEndPairs.add(pair);

        calculateStartEnd(endPosition + 1, size);
    }

    @Override
    public void shutdown() {
        super.shutdown();
        this.executorService.shutdown();
    }

    /**
     * 开始/结束值对象
     */
    private static class StartEndPair {
        public long start;
        public long end;

        @Override
        public String toString() {
            return "star=" + start + ", end=" + end;
        }
    }

    /**
     * 读取类
     */
    private class SliceReaderTask implements Runnable {
        private long start;
        private long sliceSize;
        private byte[] readBuff;

        public SliceReaderTask(StartEndPair pair) {
            this.start = pair.start;
            this.sliceSize = pair.end - pair.start + 1;
            this.readBuff = new byte[bufferSize];
        }

        @Override
        public void run() {
            try {
                MappedByteBuffer mapBuffer = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, start, sliceSize);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                for (int offset = 0; offset < sliceSize; offset += bufferSize) {
                    int readLength;
                    if (offset + bufferSize <= sliceSize) {
                        readLength = bufferSize;
                    } else {
                        readLength = (int) (sliceSize - offset);
                    }
                    mapBuffer.get(readBuff, 0, readLength);
                    for (int i = 0; i < readLength; i++) {
                        byte tmp = readBuff[i];
                        if (tmp == '\n' || tmp == '\r') {
                            handle(bos.toByteArray());
                            bos.reset();
                        } else {
                            bos.write(tmp);
                        }
                    }
                }
                if (bos.size() > 0) {
                    handle(bos.toByteArray());
                }
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 构建器
     */
    public static class Builder extends FileLineReader.Builder {
        public Builder(String file, LineHandler handler) {
            super(file, handler);
        }

        /**
         * 设置线程数
         *
         * @param size 线程数量
         * @return 构建器
         */
        public Builder threadSize(int size) {
            if (size > 0)
                this.threadSize = size;
            return this;
        }

        @Override
        public MultiThreadFileLineReader build() {
            return new MultiThreadFileLineReader(this.file, this.handler, this.charset, this.bufferSize, this.threadSize);
        }
    }
}
