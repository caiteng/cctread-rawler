package com.core.rawler;

import com.core.rawler._88dushu.Rawler_88;
import com.core.rawler.modules.RawlerUtil;

import java.io.IOException;

/**
 * @author cait
 * @version 1.0 创建时间：2018-01-15 10:55
 */
public class Main extends RawlerUtil{




    /**
     * 爬虫工具学习
     * 参考网站-http://blog.csdn.net/column/details/jsoup.html
     */

    /**
     * @param args
     */
    public static void main(String[] args) {
//        String url = "https://www.88dushu.com/search/so.php?q=天";
//        url = "https://www.88dushu.com/xiaoshuo/0/545/";
//        url = "https://www.88dushu.com/xiaoshuo/0/545/11451196.html";
//
//        url = "https://www.88dushu.com/xiaoshuo/0/545/11451197.html";
//        try {
//            System.out.println(Rawler_88.search("鹿鼎记"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {
            String  url = "https://www.88dushu.com/xiaoshuo/0/545/";
            //url="https://www.88dushu.com/xiaoshuo/20/20864/";
            Rawler_88.getWebUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        try {
//            String  url = "https://www.88dushu.com/xiaoshuo/0/545/11453500.html";
//            //url="https://www.88dushu.com/xiaoshuo/20/20864/";
//
//       String content=     Rawler_88.getContent(Rawler_88.doRequest(url));
//            System.out.println(content);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            String url = "https://www.88dushu.com/xiaoshuo/20/20864/4045610.html";
//            //url="https://www.88dushu.com/xiaoshuo/20/20864/";
//            Rawler_88.getWebUrl(url);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }




}
