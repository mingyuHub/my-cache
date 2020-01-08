package com.my.cache.service;

import java.util.concurrent.TimeUnit;

/**
 * @author: chenmingyu
 * @date: 2019/10/10 11:14
 * @description: 缓存装饰类
 */
public interface DistributedCache extends Cacheable {

    /**
     * 设置缓存，携带过期时间
     * @param cacheName 缓存名称
     * @param value 缓存值
     * @param time  过期时间
     * @param timeUnit 时间单位
     * @return
     */
    Boolean setEx(String cacheName, Object value, Long time, TimeUnit timeUnit);
}
