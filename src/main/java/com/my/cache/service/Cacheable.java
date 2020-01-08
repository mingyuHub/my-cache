package com.my.cache.service;

import org.springframework.lang.Nullable;

import java.util.concurrent.TimeUnit;

/**
 * @author: chenmingyu
 * @date: 2019/9/30 14:09
 * @description:
 */
public interface Cacheable {

    /**
     * 获取缓存
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 获取缓存
     * @param key
     * @param type
     * @param <T>
     * @return
     */
    @Nullable
    <T> T get(String key, @Nullable Class<T> type);

    /**
     * 新增缓存
     * @param key
     * @param value
     */
    void set(String key, @Nullable Object value);

    /**
     * 删除缓存
     * @param cacheName
     * @return
     */
    void invalidate(String cacheName);
}
