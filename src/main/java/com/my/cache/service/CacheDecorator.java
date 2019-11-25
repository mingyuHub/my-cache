package com.my.cache.service;

import java.util.concurrent.TimeUnit;

/**
 * @author: chenmingyu
 * @date: 2019/10/31 23:13
 * @description: 缓存装饰类 包括本地缓存及分布式缓存
 */
public class CacheDecorator implements Cacheable {

    private ApplicationCache applicationCache;

    private DistributedCache distributedCache;

    private Boolean useLocal = Boolean.TRUE;

    public CacheDecorator(ApplicationCache applicationCache, DistributedCache distributedCache) {
        this.applicationCache = applicationCache;
        this.distributedCache = distributedCache;
    }

    @Override
    public Object get(String key) {
        Object object = null;
        if(useLocal){
            object = applicationCache.get(key);
        }
        if(null != object){
            object = distributedCache.get(key);
        }
        return object;
    }

    @Override
    public <T> T get(String key, Class<T> type) {
        Object object = null;
        if(useLocal){
            object = applicationCache.get(key);
        }
        if(null != object){
            object = distributedCache.get(key);
        }
        return (T)object;
    }

    @Override
    public void set(String key, Object value) {
        distributedCache.set(key, value);
        if(useLocal){
            applicationCache.set(key, value);
        }
    }

    @Override
    public Boolean setEx(String key, Object value, Long time, TimeUnit timeUnit) {
        distributedCache.setEx(key, value, time, timeUnit);
        if(useLocal){
            applicationCache.set(key, value);
        }
        return null;
    }

    @Override
    public Boolean del(String key) {
        distributedCache.del(key);
        applicationCache.del(key);
        return null;
    }
}