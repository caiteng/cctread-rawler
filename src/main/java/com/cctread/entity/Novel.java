package com.cctread.entity;

import com.cctread.entity.modules.AbstractEntity;

/**
 * 书本信息
 *
 * @author cait
 * @version 1.0 创建时间：2018-01-08 16:39
 */
public class Novel extends AbstractEntity {
    private String bookName;

    private String author;

    private String nocalCover;

    @Override
    public String toString() {
        return "Novel{" +
                "bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", nocalCover='" + nocalCover + '\'' +
                '}';
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

    public String getNocalCover() {
        return nocalCover;
    }

    public void setNocalCover(String nocalCover) {
        this.nocalCover = nocalCover;
    }
}