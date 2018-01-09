package com.cctread.entity.modules;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类父类
 *
 * @author cait
 * @version 1.0 创建时间：2018-01-08 16:39
 */
public abstract class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 记录id
     */
    protected Long id;
    /**
     * 删除标记（0正常,1删除）
     */
    protected Integer delFlag = 0;
    /**
     * 版本号
     */
    protected Integer version = 1;
    /**
     * 记录新增时间
     */
    protected Date createDate;
    /**
     * 记录修改时间
     */
    protected Date updateDate = new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
