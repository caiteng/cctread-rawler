package com.cctread.util.io;

import java.io.*;

/**
 * io工具类
 *
 * @author cait
 * @version 1.0 创建时间：2018-01-09 15:40
 */
public class IOUtil {

    /**
     * 依据编码转换流为字符串
     *
     * @param is
     * @param encoding
     * @return
     * @throws IOException
     */
    public static String inputStreamToString(InputStream is, String encoding) throws IOException {
        int count = is.available();
        byte[] b = new byte[count];
        int readCount = 0;
        while (readCount < count) {
            readCount += is.read(b, readCount, count - readCount);
        }
        return new String(b, 0, count, encoding);
    }
}