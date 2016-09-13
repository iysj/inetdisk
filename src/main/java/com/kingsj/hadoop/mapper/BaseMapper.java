package com.kingsj.hadoop.mapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseMapper<E, PK extends Serializable> {

    int deleteByPrimaryKey(PK id);

    int insert(E record);

    E selectByPrimaryKey(PK id);

    int updateByPrimaryKey(E record);

    List<E> findAll();

    public Integer findTotal(Map<String, Object> paraMap);

    public List<E> findList(Map<String, Object> paraMap);
}
