package com.my.cache.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author: chenmingyu
 * @date: 2019/10/10 11:14
 * @description: 默认一级/本地缓存
 */
@Service("secondLevelCache")
public class SecondLevelCacheService implements CacheService{

    @Override
    public Object get(String key) {
        System.out.println("二级缓存：get");
        return null;
    }

    @Override
    public <T> T get(String key, Class<T> type) {
        return null;
    }

    @Override
    public void set(String key, Object value) {
        System.out.println("二级缓存：set");
    }

    @Override
    public Boolean setEx(String key, Object object, Long time, TimeUnit timeUnit) {
        System.out.println("二级缓存：setEx");
        return null;
    }

    @Override
    public Boolean del(String key) {
        return null;
    }
}
