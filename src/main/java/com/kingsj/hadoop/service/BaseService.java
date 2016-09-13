package com.kingsj.hadoop.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseService<E, PK extends Serializable> {

    int deleteByPrimaryKey(PK id);

    // @TriggersRemove(cacheName=MyWebConstant.CACHE_REMOTE_DATA_KEY,removeAll=true)
    int insert(E record);

    // @Cacheable(cacheName=MyWebConstant.CACHE_REMOTE_DATA_KEY)
    E selectByPrimaryKey(PK id);

    int updateByPrimaryKey(E record);

    List<E> findList(Map<String, Object> param);

    Integer findTotal(Map<String, Object> param);

}
