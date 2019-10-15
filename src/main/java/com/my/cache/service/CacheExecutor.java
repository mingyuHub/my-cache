package com.my.cache.service;

import com.my.cache.domain.BasicCacheOperation;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author: chenmingyu
 * @date: 2019/10/1 23:01
 * @description: 缓存执行器
 */
public interface CacheExecutor {

    /**
     * 根据注解不同执行不同策略
     * @param joinPoint
     * @param basicCacheInformation
     * @return
     */
    Object execute(ProceedingJoinPoint joinPoint, BasicCacheOperation basicCacheInformation);
}
