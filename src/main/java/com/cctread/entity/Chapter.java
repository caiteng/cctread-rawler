package com.cctread.entity;

import com.cctread.entity.modules.AbstractEntity;


/**
 * 章节信息
 *
 * @author cait
 * @version 1.0 创建时间：2018-01-08 16:39
 */
public class Chapter extends AbstractEntity {
    private Integer novelId;

    private String chapterName;

    @Override
    public String toString() {
        return "Chapter{" +
                "novelId=" + novelId +
                ", chapterName='" + chapterName + '\'' +
                '}';
    }

    public Integer getNovelId() {
        return novelId;
    }

    public void setNovelId(Integer novelId) {
        this.novelId = novelId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
}