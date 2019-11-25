package com.my.cache.support;

import com.my.cache.domain.BasicCache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.lang.Nullable;

/**
 * @author: chenmingyu
 * @date: 2019/9/30 14:12
 * @description: 注解解析
 */
public interface CacheAnnotationParser {

    @Nullable
    BasicCache parseCacheAnnotations(ProceedingJoinPoint joinPoint);
}
