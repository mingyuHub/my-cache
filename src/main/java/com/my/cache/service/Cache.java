package com.my.cache.service;

import org.springframework.lang.Nullable;

import java.util.concurrent.TimeUnit;

/**
 * @author: chenmingyu
 * @date: 2019/9/30 14:09
 * @description:
 */
public interface Cache {

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
     * 设置缓存
     * @param key
     * @param value
     */
    void set(String key, @Nullable Object value);

    /**
     * 设置缓存 有过期时间
     * @param key
     * @param object
     * @param time  缓存时间
     * @return
     */
    Boolean setEx(String key, Object object, Long time, TimeUnit timeUnit);

    /**
     * 删除key
     * @param key
     * @return
     */
    Boolean del(String key);
}
