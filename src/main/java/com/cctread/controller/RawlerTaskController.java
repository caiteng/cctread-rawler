
package com.cctread.controller;

import com.cctread.dao.RawlerTaskDao;
import com.cctread.entity.RawlerTask;
import com.cctread.service.RawlerTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 测试myBatis
     *
     * @return
     */
    @RequestMapping("/test")
    public Object index() {
        RawlerTask rawlerTask = new RawlerTask("斗破", "土豆", "1", "2");
        rawlerTaskDao.create(rawlerTask);
        return "test";
    }

    /**
     * 测试myBatis
     *
     * @return
     */
    @RequestMapping("/test2")
    public Object index2() {

      RawlerTask rawlerTask=  rawlerTaskDao.get(1);
        return rawlerTask.toString();
    }

}

