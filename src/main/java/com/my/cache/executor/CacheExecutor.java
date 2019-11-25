package com.my.cache.executor;

import com.my.cache.domain.BasicCache;
import com.my.cache.service.Cacheable;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author: chenmingyu
 * @date: 2019/10/1 23:01
 * @description: 缓存执行器
 */
public interface CacheExecutor {

    /**
     * 不同注解执行不同策略
     * @param joinPoint
     * @param basicCacheInformation
     * @return
     */
    Object execute(ProceedingJoinPoint joinPoint, BasicCache basicCacheInformation, Cacheable cacheable);
}
