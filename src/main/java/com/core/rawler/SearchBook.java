package com.core.rawler;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
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

    public static Map<String, BookBean> search(String key) throws IOException, InterruptedException {
        String url = getUrl(key);
        Map<String, BookBean> map = new TreeMap<String, BookBean>();
        System.out.println(url);

        Map map2 = new HashMap();
        map2.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        map2.put("Accept-Encoding", "gzip, deflate, br");
        map2.put("Accept-Language", "zh-CN,zh;q=0.9");
        map2.put("Cache-Control", "max-age=0");
        map2.put("Connection", "keep-alive");
        map2.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");


        Document doc = Jsoup.connect(url).headers(map2)
                //.data("query", "Java")
                //.userAgent("头部")
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
            if(StringUtil.isBlank(linkHref)){
                continue;
            }
            BookBean bookBean = map.get(linkHref);
            if (bookBean == null) {
                bookBean = new BookBean();
                bookBean.setHref(linkHref);
            }

            Elements imgs = link.getElementsByTag("img");
            for (Element img : imgs) {
                bookBean.setImg(img.attr("abs:src"));
            }
            if(!StringUtil.isBlank(linkText)){
                bookBean.setName(linkText);
            }
            map.put(linkHref, bookBean);
        }
        System.out.println(map.toString());

        return map;
    }

}
