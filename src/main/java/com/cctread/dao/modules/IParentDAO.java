package com.cctread.dao.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * dao层父接口
 *
 * @author cait
 * @version 1.0 创建时间：2018-01-08 16:21
 */
public interface IParentDAO<T, PK> {

    public T get(PK id);

    public ArrayList<T> getAll();

    public void create(T obj);

    public void update(T obj);

    public void delete(PK id);

    public void multiDelete(Map<String, ?> parameters);

    public long count();

    public List<T> findByWhere(Map<String, ?> parameters);

    public Long getCountByWhere(Map<String, ?> parameters);
}
