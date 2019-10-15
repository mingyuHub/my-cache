package com.my.cache.domain;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author: chenmingyu
 * @date: 2019/10/1 22:46
 * @description:
 */
public class CacheContext {

    private ProceedingJoinPoint joinPoint;

    private BasicCacheOperation basicCacheInformation;

    public CacheContext(ProceedingJoinPoint joinPoint, BasicCacheOperation basicCacheInformation) {
        this.joinPoint = joinPoint;
        this.basicCacheInformation = basicCacheInformation;
    }
}
