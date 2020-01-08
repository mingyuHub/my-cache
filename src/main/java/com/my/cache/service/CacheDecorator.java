package com.my.cache.service;

import java.util.concurrent.TimeUnit;

/**
 * @author: chenmingyu
 * @date: 2019/10/31 23:13
 * @description: 缓存装饰类 包括本地缓存及分布式缓存
 */
public class CacheDecorator implements DistributedCache {

    private LocalCache applicationCache;

    private DistributedCache distributedCache;

    public CacheDecorator(LocalCache applicationCache, DistributedCache distributedCache) {
        this.applicationCache = applicationCache;
        this.distributedCache = distributedCache;
    }

    @Override
    public Object get(String key) {
        Object object = null;
        object = applicationCache.get(key);
        if(null != object){
            object = distributedCache.get(key);
        }
        return object;
    }

    @Override
    public <T> T get(String key, Class<T> type) {
        Object object = null;
        object = applicationCache.get(key);
        if(null != object){
            object = distributedCache.get(key);
        }
        return (T)object;
    }

    @Override
    public void set(String key, Object value) {
        distributedCache.set(key, value);
        applicationCache.set(key, value);
    }

    @Override
    public Boolean setEx(String key, Object value, Long time, TimeUnit timeUnit) {
        distributedCache.setEx(key, value, time, timeUnit);
        applicationCache.set(key, value);
        return null;
    }

    @Override
    public void invalidate(String key) {
        distributedCache.invalidate(key);
        applicationCache.invalidate(key);
    }
}