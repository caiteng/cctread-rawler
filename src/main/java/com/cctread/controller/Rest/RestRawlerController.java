package com.cctread.controller.Rest;

import com.cctread.controller.RawlerController;
import com.cctread.dao.NovelDao;
import com.cctread.entity.Book;
import com.cctread.entity.Novel;
import com.cctread.entity.RawlerTask;
import com.cctread.service.NovelService;
import com.cctread.util.cos.TenCentCosClient;
import com.cctread.util.cos.TenCentCosService;
import com.core.exception.CctException;
import com.core.rawler._88dushu.Rawler_88;
import com.google.gson.Gson;
import com.qcloud.cos.COSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.util.Map;

/**
 * 项目核心接口
 *
 * @author cait
 * @version 1.0 创建时间：2018-01-08 17:24
 */
@RestController
@RequestMapping("/rawler")
public class RestRawlerController {

    private static final Logger log = LoggerFactory.getLogger(RestRawlerController.class);
    @Autowired
    private NovelService novelService;

    /**
     * 搜索
     */
    @RequestMapping("/search")
    public String search(String key) {
        try {
            Map map = Rawler_88.search(key);
            if (map.size() == 0) {
                throw new CctException("未找到指定书籍");
            }
            return new Gson().toJson(map);
        } catch (Exception e) {
            e.printStackTrace();
            return "{err:" + e + "}";
        }
    }

    /**
     * 添加书籍
     */
    @RequestMapping("/createBook")
    public String create(String href, String name, String img, String author) {
        log.info("createBook:"+name);
        //线程池获取线程添加书籍
        TenCentCosClient.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info("start createBook:"+name);
                    novelService.createBook(href, name, img, author);
                    log.info("over createBook:"+name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return "添加成功";

    }
}
