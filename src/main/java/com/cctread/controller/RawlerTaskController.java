
package com.cctread.controller;

import com.cctread.dao.RawlerTaskDao;
import com.cctread.entity.RawlerTask;
import com.cctread.service.RawlerTaskService;
import com.google.gson.Gson;
import com.rawler.SearchBook;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 爬虫任务
 *
 * @author cait
 * @version 1.0 创建时间：2018-01-08 16:40
 */
@RestController
public class RawlerTaskController {

    @Autowired
    private RawlerTaskService rawlerTaskService;

    @Autowired
    private RawlerTaskDao rawlerTaskDao;

    /**
     * 搜索
     *
     * @param token
     * @param website
     * @param key
     * @param page
     * @return
     */
    @RequestMapping("/search")
    public Object search(String token, String website, String key, String page) {
        if (page == null) {

        }

        Map map = null;
        try {
            map = SearchBook.search(key);
            return new Gson().toJson(map);
        } catch (IOException e) {
            e.printStackTrace();
            return "test";
        }

    }


}

