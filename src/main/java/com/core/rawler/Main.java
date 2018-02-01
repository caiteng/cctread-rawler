package com.core.rawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.thymeleaf.util.MapUtils;
import sun.plugin2.os.windows.Windows;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cait
 * @version 1.0 创建时间：2018-01-15 10:55
 */
public class Main {

    /**
     * 爬虫工具学习
     * 参考网站-http://blog.csdn.net/column/details/jsoup.html
     */

    /**
     * @param args
     */
    public static void main(String[] args) {
        String url = "https://www.88dushu.com/search/so.php?q=天";
//        url = "https://www.88dushu.com/xiaoshuo/0/545/";
//        url = "https://www.88dushu.com/xiaoshuo/0/545/11451196.html";
//
//        url = "https://www.88dushu.com/xiaoshuo/0/545/11451197.html";
        try {
            SearchBook.search("斗破苍穹天蚕");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //GetContent(url);

    }

    public static void GetContent(String url) {
        try {


            Document doc = Jsoup.connect(url)
                    //.data("query", "Java")
                    //.userAgent("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1 )")
                    //.cookie("auth", "token")
                    .timeout(5000)
                    //.post()
                    .get();
            Elements content = doc.select(".yd_text2");
            System.out.println(content.html());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void getText() {

    }

    /**
     * 去除div
     *
     * @param content 主体内容
     */
    public void removeDiv(String content) {
//        if(){
//
//        }

    }


}
