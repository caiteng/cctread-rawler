package com.cctread.dao;

import com.cctread.dao.modules.IParentDAO;
import com.cctread.entity.Chapter;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
/**
 * 章节
 *
 * @author cait
 * @version 1.0 创建时间：2018-01-08 16:37
 */
@Mapper
@Repository
public interface ChapterDao extends IParentDAO<Chapter, Integer> {
}