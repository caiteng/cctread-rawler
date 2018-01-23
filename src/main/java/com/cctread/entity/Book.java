package com.cctread.entity;

import com.cctread.entity.modules.AbstractEntity;

/**
 * 书
 *
 * @author caiteng
 * @version 1.0 创建时间：2018-01-23 16:28
 */
public class Book extends AbstractEntity {

    /**
     * 书名
     */
    private String bookName;

    /**
     * 章节编号
     */
    private int ChapterNumber;

    /**
     * 章节名
     */
    private String ChapterName;
    /**
     * 内容
     */
    private String content;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getChapterNumber() {
        return ChapterNumber;
    }

    public void setChapterNumber(int chapterNumber) {
        ChapterNumber = chapterNumber;
    }

    public String getChapterName() {
        return ChapterName;
    }

    public void setChapterName(String chapterName) {
        ChapterName = chapterName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
