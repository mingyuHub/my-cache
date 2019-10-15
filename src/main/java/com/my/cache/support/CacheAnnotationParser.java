package com.my.cache.support;

import com.my.cache.domain.BasicCacheOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

/**
 * @author: chenmingyu
 * @date: 2019/9/30 14:12
 * @description: 注解解析
 */
public interface CacheAnnotationParser {

    @Nullable
    BasicCacheOperation parseCacheAnnotations(ProceedingJoinPoint joinPoint);
}
