package com.my.cache.support;

import com.my.cache.service.Cache;
import com.my.cache.service.DistributedCache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: chenmingyu
 * @date: 2019/11/11 23:03
 * @description:
 */
public interface CacheManager {


    /**
     * 根据name获取Cache
     * @param name
     * @return
     */
    Cache getCache(String name);


}
