package com.my.cache.executor;

import com.my.cache.domain.BasicCacheOperation;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author: chenmingyu
 * @date: 2019/10/1 23:01
 * @description: 缓存执行器
 */
public interface CacheExecutor {

    /**
     * 根据不同注解执行各自策略
     * @param joinPoint
     * @param basicCacheInformation
     * @return
     */
    Object execute(ProceedingJoinPoint joinPoint, BasicCacheOperation basicCacheInformation);
}
