package com.cctread.dao;

import com.cctread.dao.modules.IParentDAO;
import com.cctread.entity.Novel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
/**
 * 书
 *
 * @author cait
 * @version 1.0 创建时间：2018-01-08 16:37
 */
@Mapper
@Repository
public interface NovelDao extends IParentDAO<Novel, Integer> {
}