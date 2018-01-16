package com.rawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @author cait
 * @version 1.0 创建时间：2018-01-15 10:55
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String url = "https://www.88dushu.com/search/so.php?q=斗破苍穹";
        url = "https://www.88dushu.com/xiaoshuo/0/545/";
        url = "https://www.88dushu.com/xiaoshuo/0/545/11451196.html";

        url = "https://www.88dushu.com/xiaoshuo/0/545/11451197.html";


        Get_Url(url);

    }

    public static void Get_Url(String url) {
        try {
            Document doc = Jsoup.connect(url)
                    //.data("query", "Java")
                    .userAgent("头部")
                    //.cookie("auth", "token")
                    .timeout(5000)
                    //.post()
                    .get();

            Elements links = doc.select("a[href]");
            for (Element link : links) {

                String linkHref = link.attr("abs:href");
                String linkText = link.text();
                System.out.println(linkText + ":" + linkHref);
//                hm.put(linkText, linkHref);
//                href=linkText;
            }
            Elements content = doc.select(".yd_text2");
            System.out.println(content);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void getText() {

    }


}
