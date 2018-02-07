package com.core.rawler._88dushu;

import com.core.rawler.modules.BookBean;
import com.core.rawler.modules.RawlerUtil;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * 88读书网爬虫
 *
 * @author caiteng
 * @version 1.0 创建时间：2018-02-01 15:53
 */
public class Rawler_88 extends RawlerUtil {

    private final static String SEARCH_URL_88DUSHU = "https://www.88dushu.com/search/so.php?q=";

    /**
     * 依据条件搜索书籍
     *
     * @param key
     */
    public static Map<String, BookBean> search(String key) throws IOException, InterruptedException {
        String url = SEARCH_URL_88DUSHU + key;
        Map<String, BookBean> map = new TreeMap<String, BookBean>();
        Document doc = doRequest(url);
        Elements books = doc.select("div.block");
        for (Element book : books) {
            BookBean bookBean = null;
            Elements links = book.select("a[href]");
            for (Element link : links) {
                String linkHref = link.attr("abs:href");
                if (StringUtil.isBlank(linkHref)) {
                    continue;
                }
                bookBean = map.get(linkHref);
                if (bookBean == null) {
                    bookBean = new BookBean();
                    bookBean.setHref(linkHref);
                }
            }
            Elements imgs = book.getElementsByTag("img");
            for (Element img : imgs) {
                bookBean.setImg(img.attr("abs:src"));
                bookBean.setName(img.attr("alt"));
            }
            Elements authors = book.select("p:contains(作者)");
            for (Element author : authors) {
                bookBean.setAuthor(author.text());
            }

            map.put(bookBean.getHref(), bookBean);
        }
        return map;
    }


    /**
     * 依据搜索章节
     */
    public static Map<String, BookBean> getWebUrl(String url) throws IOException, InterruptedException {
        Map<String, BookBean> map = new TreeMap<String, BookBean>();
        Document doc = doRequest(url);
        Elements links = doc.select("a[href]");
        for (Element link : links) {

            String linkHref = link.attr("abs:href");
            String linkText = link.text();

            //   System.out.println(linkText + ":" + linkHref);
            if (StringUtil.isBlank(linkHref) || "首页".equals(linkText)) {
                continue;
            }
            if (!linkHref.contains("www.88dushu.com")) {
                continue;
            }
            if (!linkText.contains("章") || linkText.contains("节")) {
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
            if (!StringUtil.isBlank(linkText)) {
                bookBean.setName(linkText);
            }
            map.put(linkHref, bookBean);
        }
        System.out.println(map.toString());
        return map;
    }


    /**
     * 提取内容
     *
     * @param doc
     * @return 正文内容
     */
    public static String getContent(Document doc) {
        Elements content = doc.select(".yd_text2");
        return content.html();
    }


    /**
     * 获取下一章url
     *
     * @param doc
     * @return 下一章的url
     */
    public static String nextChapter(Document doc) {
        Elements links = doc.select("a[href]");
        for (Element link : links) {
            String linkHref = link.attr("abs:href");
            String linkText = link.text();
            if (linkText.contains("下一章") && linkHref.contains("www.88dushu.com") && linkHref.contains("html")) {
                return linkHref;
            }
        }
        return null;
    }
}
