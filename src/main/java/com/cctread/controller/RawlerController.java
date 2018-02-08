package com.cctread.controller;

import com.cctread.entity.Book;
import com.cctread.util.cos.TenCentCosService;
import com.core.exception.CctException;
import com.core.rawler._88dushu.Rawler_88;
import com.core.rawler.modules.BookBean;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(RawlerController.class);


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
     * 搜索结果
     *
     * @return
     */
    @RequestMapping("/search")
    public Object search(Model model, String key) {
        if (!StringUtil.isBlank(key)) {
            try {
                Map<String, BookBean> map = Rawler_88.search(key);
                if (map.size() == 0) {
                    throw new CctException("未找到指定书籍");
                }
                model.addAttribute("book", map);
            } catch (Exception e) {
                log.error(e.getMessage());
                model.addAttribute("msg", e.getMessage());
            }
        }
        return "search/result";
    }


    /**
     * 测试freemarker
     *
     * @return
     */
    @RequestMapping("/get")
    public String get(Model model, String key) {
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
    public String getWebUrl(Model model, String url) {
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
