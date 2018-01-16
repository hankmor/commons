package com.belonk.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 按行读取文本文件工具类。
 * <p>
 * 按行读取大文本文件，
 * Created by sun on 2017/9/20.
 *
 * @author sunfuchang03@126.com
 * @version 1.0
 * @since 1.0
 */
public class FileLineReader {
    //~ Static fields/initializers =====================================================================================
    private static Logger log = LoggerFactory.getLogger(FileLineReader.class);

    //~ Instance fields ================================================================================================
    protected String charset;
    protected int bufferSize = 1024;
    protected LineHandler handler;
    protected long fileLength;
    protected AtomicLong counter = new AtomicLong(0);
    protected RandomAccessFile randomAccessFile;
    private byte[] readBuff;

    //~ Constructors ===================================================================================================
    protected FileLineReader(File file, LineHandler handler, String charset, int bufferSize) {
        this.fileLength = file.length();
        this.handler = handler;
        this.charset = charset;
        this.bufferSize = bufferSize;
        this.readBuff = new byte[bufferSize];

        try {
            this.randomAccessFile = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException e) {
            log.error("File can not be found : ", e);
        }
    }

    //~ Methods ========================================================================================================

    /**
     * 处理读取后业务逻辑.
     *
     * @param bytes 字节数组
     * @throws UnsupportedEncodingException
     * @see LineHandler
     */
    protected void handle(byte[] bytes) throws UnsupportedEncodingException {
        String line = null;
        if (this.charset == null) {
            line = new String(bytes);
        } else {
            line = new String(bytes, charset);
        }
        if (!"".equals(line)) {
            this.handler.handle(line);
            counter.incrementAndGet();
        }
    }

    /**
     * 关闭资源
     */
    public void shutdown() {
        try {
            this.randomAccessFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 按开始位置读取一定长度文件。
     * <p>
     * 该方法会检查读取的文件末位置是否在一行，不在一行则处理为一行。
     *
     * @param start     开始位置
     * @param sliceSize 读取长度
     * @return 处理末位置为一整行后，最终读取的长度
     * @throws IOException IO异常
     */
    public long read(long start, long sliceSize) throws IOException {
        if (sliceSize > fileLength) {
            sliceSize = fileLength;
        }
        if (sliceSize < fileLength) {
            // 找到定位位置的换行，读取一行
            randomAccessFile.seek(sliceSize);
            byte tmp = (byte) randomAccessFile.read();
            while (tmp != '\n' && tmp != '\r') {
                sliceSize++;
                if (sliceSize > fileLength) {
                    sliceSize = fileLength;
                    break;
                }
                randomAccessFile.seek(sliceSize);
                tmp = (byte) randomAccessFile.read();
            }
        }
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
        return sliceSize;
    }

    /**
     * 读取整个文件。
     *
     * @return 读取文件长度
     * @throws IOException IO异常
     */
    public long read() throws IOException {
        return read(0, fileLength);
    }

    /**
     * 最终总共读取的文件行数。
     *
     * @return 行数
     */
    public long getTotalCount() {
        return counter.get();
    }

    /**
     * 构建器
     */
    public static class Builder {
        protected int threadSize = 1;
        protected String charset = "utf-8";
        protected int bufferSize = 1024;
        protected LineHandler handler;
        protected File file;

        public Builder(String file, LineHandler handler) {
            this.file = new File(file);
            if (!this.file.exists()) {
                throw new IllegalArgumentException("File can not be found.");
            }
            this.handler = handler;
        }

        /**
         * 设置按行处理字符串的字符集，默认为<code>UTF-8</code>。
         *
         * @param charset 字符集
         * @return 构建器
         */
        public FileLineReader.Builder charset(String charset) {
            if (charset != null && !"".equals(charset)) {
                this.charset = charset;
            }
            return this;
        }

        /**
         * 设置缓冲区大小，默认为1024字节。
         *
         * @param bufferSize 大小
         * @return 构建器
         */
        public FileLineReader.Builder bufferSize(int bufferSize) {
            if (bufferSize > 0) {
                this.bufferSize = bufferSize;
            }
            return this;
        }

        public FileLineReader build() {
            return new FileLineReader(this.file, this.handler, this.charset, this.bufferSize);
        }
    }
}