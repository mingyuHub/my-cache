package com.my.cache.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.my.cache.domain.BasicCache;
import org.springframework.util.Assert;

/**
 * @author: chenmingyu
 * @date: 2019/10/10 11:13
 * @description: 本地缓存
 */
public class LocalCache implements Cacheable {

    /**
     * 缓存对象
     */
    private final Cache<Object, Object> cache;

    @Override
    public Object get(String cacheName) {
        Assert.hasText(cacheName, "LocalCache get cacheName must not be empty");
        return this.cache.getIfPresent(cacheName);
    }

    @Override
    public <T> T get(String cacheName, Class<T> type) {
        Assert.hasText(cacheName, "LocalCache get cacheName must not be empty");
        Assert.isNull(type,"LocalCache get type must not be null");
        return (T) this.cache.getIfPresent(cacheName);
    }

    @Override
    public void set(String cacheName, Object value) {
        Assert.hasText(cacheName, "LocalCache set cacheName must not be empty");
        System.out.println("一级缓存：set");
        this.cache.put(cacheName, value);
    }

    @Override
    public void invalidate(String cacheName) {
        Assert.hasText(cacheName, "LocalCache invalidate cacheName must not be empty");
        this.cache.invalidate(cacheName);
    }

    public LocalCache(BasicCache basicCache) {
        this.cache = createCache(basicCache);
    }

    /**
     * 创建本地缓存
     * @return
     */
    private Cache<Object, Object> createCache(BasicCache basicCache){
        Cache<Object, Object> cache = Caffeine.newBuilder()
                .expireAfterWrite(basicCache.getLocalCacheExpire(), basicCache.getTimeUnit())
                .build();
        return cache;
    }
}
