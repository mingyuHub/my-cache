package com.my.cache.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

/**
 * @author: chenmingyu
 * @date: 2019/10/10 11:13
 * @description: 本地缓存
 */
public class ApplicationCache extends AbstractCache {


    /**
     * 缓存对象
     */
    private final Cache<Object, Object> cache;

    @Override
    public Object get(String key) {

        System.out.println("一级缓存：get");
        return null;
    }

    @Override
    public <T> T get(String key, Class<T> type) {
        return null;
    }

    @Override
    public void set(String key, Object value) {
        System.out.println("一级缓存：set");
    }

    @Override
    public Boolean setEx(String key, Object object, Long time, TimeUnit timeUnit) {
        System.out.println("一级缓存：setEx");
        return null;
    }

    @Override
    public Boolean del(String key) {
        return null;
    }


    public ApplicationCache() {
        this.cache = createCache();
    }

    /**
     * 创建本地缓存
     * @return
     */
    private Cache createCache(){
        Caffeine<Object, Object> builder = Caffeine.newBuilder().initialCapacity(64).initialCapacity(64);
        return builder.build();
    }
}
