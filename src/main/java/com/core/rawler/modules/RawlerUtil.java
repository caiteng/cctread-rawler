package com.core.rawler.modules;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 爬虫公共父类
 *
 * @author caiteng
 * @version 1.0 创建时间：2018-02-01 15:59
 */
public abstract class RawlerUtil {


    /**
     * 执行请求
     *
     * @param url
     * @return 返回Document对象
     */
    public static Document doRequest(String url) throws IOException {
        Document doc = Jsoup.connect(url).headers(getHeadersMap()).timeout(5000).get();
        return doc;
    }

    /**
     * 请求头集合
     */
    public static Map<String, String> getHeadersMap() {
        Map headers = new HashMap();
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9");
        headers.put("Cache-Control", "max-age=0");
        headers.put("Connection", "keep-alive");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
        return headers;
    }
}
