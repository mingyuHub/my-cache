package com.my.cache.support;

import com.my.cache.domain.BasicCache;
import com.my.cache.service.Cacheable;

import java.util.Set;

/**
 * @author: chenmingyu
 * @date: 2019/11/11 23:03
 * @description:
 */
public interface CacheManager {

    /**
     * 获取所有缓存名称
     * @return
     */
    Set<String> cacheNames();

    /**
     * 获取缓存
     * @param basicCache
     * @return
     */
    Cacheable getCacheable(BasicCache basicCache);
}
