package com.cctread.entity;

import com.cctread.entity.modules.AbstractEntity;

public class Chapter extends AbstractEntity {
    private Integer novelId;

    private String chapterName;

    private String rawlerUrl;

    private Integer status;

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

    public String getRawlerUrl() {
        return rawlerUrl;
    }

    public void setRawlerUrl(String rawlerUrl) {
        this.rawlerUrl = rawlerUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}