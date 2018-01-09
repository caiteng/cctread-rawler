
package com.cctread.entity;

import com.cctread.entity.modules.AbstractEntity;

import java.util.Date;

/**
 * 爬虫任务
 *
 * @author cait
 * @version 1.0 创建时间：2018-01-08 16:39
 */
public class RawlerTask extends AbstractEntity {

    /**
     * 书名
     */
    private String bookName;
    /**
     * 作者
     */
    private String author;
    /**
     * 开始章节
     */
    private String startChapter;
    /**
     * 结束章节
     */
    private String endChapter;
    /**
     * 任务状态(0=未执行1=已执行)
     */
    private Integer status = 0;

    public RawlerTask() {
    }

    public RawlerTask(String bookName, String author, String startChapter, String endChapter) {
        this.bookName = bookName;
        this.author = author;
        this.startChapter = startChapter;
        this.endChapter = endChapter;
        this.createDate = new Date();
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStartChapter() {
        return startChapter;
    }

    public void setStartChapter(String startChapter) {
        this.startChapter = startChapter;
    }

    public String getEndChapter() {
        return endChapter;
    }

    public void setEndChapter(String endChapter) {
        this.endChapter = endChapter;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

