package com.my.cache.domain;

import org.springframework.lang.Nullable;

/**
 * @author: chenmingyu
 * @date: 2019/9/28 10:33
 * @description:
 */
public interface Cache {

    /**
     * @return 缓存key
     */
    Object getCacheName();

    /**
     * 获取缓存
     * @param key
     * @param type
     * @param <T>
     * @return
     */
    @Nullable
    <T> T get(Object key, @Nullable Class<T> type);

    /**
     * 设置缓存
     * @param key
     * @param value
     */
    void put(Object key, @Nullable Object value);

    /**
     * 淘汰缓存
     */
    void evict();
}
