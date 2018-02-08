package com.cctread.controller;

import com.cctread.entity.Book;
import com.cctread.util.cos.TenCentCosService;
import com.core.rawler._88dushu.Rawler_88;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * 项目核心接口
 *
 * @author cait
 * @version 1.0 创建时间：2018-01-08 17:24
 */
@Controller
public class RawlerController {

    @Autowired
    private TenCentCosService tenCentCosService;

    /**
     * 搜索首页
     *
     * @return
     */
    @RequestMapping("/")
    public Object index() {
        return "search/searchBook";
    }
    /**
     * 搜索首页
     *
     * @return
     */
    @RequestMapping("/2")
    public Object index2() {
        return "search/searchResult";
    }


    /**
     * 测试freemarker
     *
     * @return
     */
    @RequestMapping("/get")
    public String get(Model model,String key) {
        Book book = new Book();
        book.setContent("cses");

        try {
            Map map = Rawler_88.search(key);
            model.addAttribute("book", map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "testBook";
    }
    /**
     * 测试freemarker
     *
     * @return
     */
    @RequestMapping("/getWebUrl")
    public String getWebUrl(Model model,String url) {
        Book book = new Book();
        book.setContent("cses");
        try {
            Map map = Rawler_88.getWebUrl(url);
            model.addAttribute("book", map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "testBook";
    }


}
