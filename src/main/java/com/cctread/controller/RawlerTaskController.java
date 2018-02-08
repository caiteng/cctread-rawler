
package com.cctread.controller;

import com.cctread.dao.RawlerTaskDao;
import com.cctread.entity.Book;
import com.cctread.entity.RawlerTask;
import com.cctread.service.RawlerTaskService;
import com.cctread.util.cos.TenCentCosService;
import com.core.rawler._88dushu.Rawler_88;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

import static java.awt.SystemColor.text;

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
//
//    @Autowired
//    private RawlerTaskService rawlerTaskService;
//
//    @Autowired
//    private RawlerTaskDao rawlerTaskDao;

    /**
     * 测试freemarker
     *
     * @return
     */
    @RequestMapping("/getContent")
    public String getContent(String url) {
        try {
            return Rawler_88.getContent(Rawler_88.doRequest(url));
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
    @RequestMapping("/getConte22nt")
    public String getContent111(String url) {
        try {
            Rawler_88.getContent(Rawler_88.doRequest(url));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "testBook";
    }


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
    public String testup(String url) {
        try {
            String text = Rawler_88.getContent(Rawler_88.doRequest(url));
            tenCentCosService.putObject(text, "斗破苍穹_天蚕土豆_1");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "c";
    }

}

