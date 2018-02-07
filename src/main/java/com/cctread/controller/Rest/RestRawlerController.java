package com.cctread.controller.Rest;

import com.cctread.dao.NovelDao;
import com.cctread.entity.Book;
import com.cctread.entity.Novel;
import com.cctread.entity.RawlerTask;
import com.cctread.util.cos.TenCentCosService;
import com.core.exception.CctException;
import com.core.rawler._88dushu.Rawler_88;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    private NovelDao novelDao;

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
     * 搜索
     */
    @RequestMapping("/get")
    public String sear2ch(String key) {
        return novelDao.get(1).toString();
    }
}
