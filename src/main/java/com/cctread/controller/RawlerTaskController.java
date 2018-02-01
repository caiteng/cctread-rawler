
package com.cctread.controller;

import com.cctread.dao.RawlerTaskDao;
import com.cctread.service.RawlerTaskService;
import com.cctread.util.cos.TenCentCosService;
import com.google.gson.Gson;
import com.core.rawler.SearchBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private TenCentCosService tenCentCosService;

    @Autowired
    private RawlerTaskService rawlerTaskService;

    @Autowired
    private RawlerTaskDao rawlerTaskDao;

    /**
     * 测试下载
     *
     * @return
     */
    @RequestMapping("/testdown")
    public String testdown() {
        return tenCentCosService.getObject("斗破苍穹_天蚕土豆_1");

    }

    /**
     * 测试上传
     *
     * @return
     */
    @RequestMapping("/testup")
    public String testup() {
        String text = "测试上传3千載正字一夕改<br>  《》 如今吾輩來重光3ed%$#%^&&*()@!~斗破苍\"\"穹_天蚕土豆_1";
        while (text.length() < 8000) {
            text += text;
        }
        tenCentCosService.putObject(text, "斗破苍穹_天蚕土豆_1");
        return "c";
    }

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

