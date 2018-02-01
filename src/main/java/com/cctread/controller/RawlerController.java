package com.cctread.controller;

import com.cctread.entity.Book;
import com.cctread.util.cos.TenCentCosService;
import com.core.rawler.SearchBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
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
     * 首页
     *
     * @return
     */
    @RequestMapping("/")
    public Object index() {
        return "index";
    }


    /**
     * 测试freemarker
     *
     * @return
     */
    @RequestMapping("/get")
    public String get(Model model) {
        Book book = new Book();
        book.setContent("cses");

        try {
         Map map= SearchBook.search("天珠变");
            model.addAttribute("book", map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "testBook";
    }


}
