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

    public static void main(String[] args) {
              String url = "https://www.cnblogs.com/TTyb/p/5784581.html";
              Get_Url(url);
    }

    public static void Get_Url(String url) {
        try {
            Document doc = Jsoup.connect(url)
                    //.data("query", "Java")
                    //.userAgent("头部")
                    //.cookie("auth", "token")
                    //.timeout(3000)
                    //.post()
                    .get();

            Elements links=   doc.select("a[href]");
            for(Element link:links){

                String linkHref = link.attr("abs:href");
                String linkText = link.text();
                System.out.println(linkText+":"+linkHref);
//                hm.put(linkText, linkHref);
//                href=linkText;
            }
            //得到html的所有东西
//            Element content = doc.getElementById("content");
//            //分离出html下<a>...</a>之间的所有东西
//            Elements links = content.getElementsByTag("a");
//            //Elements links = doc.select("a[href]");
//            // 扩展名为.png的图片
//            Elements pngs = doc.select("img[src$=.png]");
//            // class等于masthead的div标签
//            Element masthead = doc.select("div.masthead").first();
//
//            for (Element link : links) {
//                //得到<a>...</a>里面的网址
//                String linkHref = link.attr("href");
//                //得到<a>...</a>里面的汉字
//                String linkText = link.text();
//                System.out.println(linkText);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
