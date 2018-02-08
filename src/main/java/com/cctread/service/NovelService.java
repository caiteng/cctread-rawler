package com.cctread.service;

import com.cctread.dao.ChapterDao;
import com.cctread.dao.NovelDao;
import com.cctread.entity.Chapter;
import com.cctread.entity.Novel;
import com.core.exception.CctException;
import com.core.rawler._88dushu.Rawler_88;
import com.core.rawler.modules.BookBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * 书本信息
 *
 * @author caiteng
 * @version 1.0 创建时间：2018-02-08 13:51
 */
@Component
public class NovelService {

    @Autowired
    private NovelDao novelDao;
    @Autowired
    private ChapterDao chapterDao;


    /**
     * 新增书籍
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public void createBook(String href, String name, String img, String author) throws IOException, InterruptedException {
        //Document document = Rawler_88.doRequest(href);
        //获取章节
        Map<String, BookBean> map = Rawler_88.getWebUrl(href);
        if (map.size() == 0) {
            throw new CctException("未搜索到内容");
        }
        int novelId = createNovel(name, author, img);
        Set set = map.keySet();
        for (Object key : set) {
            createChapter(novelId, map.get(key).getName(), map.get(key).getHref());
        }
    }


    /**
     * 新增书本信息
     *
     * @param bookName   书名
     * @param author     作者
     * @param nocalCover 封面
     * @return id
     */
    private int createNovel(String bookName, String author, String nocalCover) {
        Novel novel = new Novel();
        novel.setBookName(bookName);
        novel.setAuthor(author);
        novel.setNocalCover(nocalCover);
        novel.setCreateDate(new Date());
        novelDao.create(novel);
        return novel.getId();
    }

    /**
     * 新增章节
     */
    private void createChapter(int novelId, String chapterName, String rawlerUrl) {
        Chapter chapter = new Chapter();
        chapter.setNovelId(novelId);
        chapter.setChapterName(chapterName);
        chapter.setStatus(0);
        chapter.setCreateDate(new Date());
        chapter.setRawlerUrl(rawlerUrl);
        chapterDao.create(chapter);
    }


}
