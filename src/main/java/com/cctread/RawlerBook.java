package com.cctread;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RawlerBook {
    private static final String WEB_URL = "http://m.yibige.com/0_763/506077.html ";

    private static final String WEB_HEAD = "http://m.yibige.com";

    private static Integer count = 1;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String a = spider(WEB_URL);
        do {
            a = spider(a);
        } while (a != null && !"".equals(a));
        System.out.println("获取成功！");
    }

    public static String spider(String webUrl) {
        URL url = null;
        URLConnection connection = null;
        BufferedReader br = null;
        PrintWriter pw = null;
        String nextPage = "";
        String regex = "<a id=\"pt_next\" href=\"(.*?)\">下一章</a>";
        String regex2 = "<div id=\"nr1\">(.*?)</div>";
        String regex_unit = "/0_763/(.*?)";
        Pattern p = Pattern.compile(regex);
        Pattern p_text = Pattern.compile(regex2);
        Pattern p_unit = Pattern.compile(regex_unit);
        String txtName = "第" + count + "章";
        Integer counts = 0;
        try {
            url = new URL(webUrl);
            connection = url.openConnection();
            pw = new PrintWriter(new FileWriter("d:/read/" + txtName + ".txt"), true);
            br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "gbk"));
            String buf = null;
            while ((buf = br.readLine()) != null) {
                Matcher buf_m = p.matcher(buf);
                while (buf_m.find()) {
                    String nextUrl = buf_m.group(1).trim();
                    Matcher buf_url = p_unit.matcher(nextUrl);
                    while (buf_url.find()) {
                        nextPage = WEB_HEAD + nextUrl;
                    }
                }
                Matcher buf_text = p_text.matcher(buf);
                while (buf_text.find()) {
                    if (buf_text != null && !"".equals(buf_text)) {
                        pw.println(buf_text.group());
                        counts++;
                    }
                }
                //pw.println(buf);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            pw.close();
        }
        if (counts > 0) {
            count++;
        }
        return nextPage;
    }

}