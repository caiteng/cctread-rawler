package com.cctread.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author cait
 * @version 1.0 创建时间：2018-01-08 17:24
 */
@Controller
public class RawlerController {


    /**
     * 首页
     * @return
     */
    @RequestMapping("/")
    public Object index() {
        return "index";
    }
}
