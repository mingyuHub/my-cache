package com.my.cache.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author: chenmingyu
 * @date: 2019/10/10 16:37
 * @description:
 */
public abstract class AbstractCacheExecutor implements CacheExecutor{

    @Autowired
    @Qualifier("localCache")
    protected CacheService localCache;

    @Autowired
    @Qualifier("distributedCache")
    protected CacheService distributedCache;
}
