package com.cctread.dao.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * dao层父接口
 *
 * @author cait
 * @version 1.0 创建时间：2018-01-08 16:21
 * @deprecated 提供基本增删改查接口用以子接口继承
 */
public interface IParentDAO<T, PK> {

    /**
     * 依据记录id查询记录
     *
     * @param id
     * @return
     */
    public T get(PK id);

    /**
     * 查询所有记录
     *
     * @return 结果集合
     */
    public ArrayList<T> getAll();

    /**
     * 新增记录
     *
     * @param obj 数据库表对应的对象
     */
    public void create(T obj);

    /**
     * 更新记录
     *
     * @param obj 数据库表对应的对象
     */
    public void update(T obj);

    /**
     * 删除记录
     *
     * @param id 主键
     */
    public void delete(PK id);

    /**
     * 批量删除
     *
     * @param parameters
     */
    public void multiDelete(Map<String, ?> parameters);

    /**
     * 获取表总记录数
     *
     * @return
     */
    public long count();

    /**
     * 依据条件查询相关记录
     *
     * @param parameters
     * @return
     */
    public List<T> findByWhere(Map<String, ?> parameters);

    /**
     * 依据条件查询相关记录总数
     *
     * @param parameters
     * @return
     */
    public Long getCountByWhere(Map<String, ?> parameters);
}
