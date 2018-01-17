package com.rawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 搜索
 *
 * @author caiteng
 * @version 1.0 创建时间：2018-01-17 16:45
 */
public class SearchBook {

    private final static String SEARCH_URL_88DUSHU = "https://www.88dushu.com/search/so.php?q=";


    private static String getUrl(String key) {
        return SEARCH_URL_88DUSHU + key;
    }

    public static Map<String, String> search(String key) throws IOException {
        String url = getUrl(key);
        Map map = new TreeMap();
        System.out.println(url);
        Document doc = Jsoup.connect(url)
                //.data("query", "Java")
                .userAgent("头部")
                //.cookie("auth", "token")
                .timeout(5000)
                //.post()
                .get();

        Elements links = doc.select("a[href]");
        System.out.println(links);
        for (Element link : links) {
            String linkHref = link.attr("abs:href");
            String linkText = link.text();
            System.out.println(linkText + ":" + linkHref);
            map.put(linkText, linkHref);
        }
        return map;
    }

}
