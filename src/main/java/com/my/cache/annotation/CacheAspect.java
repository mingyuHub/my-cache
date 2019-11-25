package com.my.cache.annotation;

import com.my.cache.constant.CacheAnnotationEnum;
import com.my.cache.domain.BasicCache;
import com.my.cache.executor.CacheEvictProfilerExecutor;
import com.my.cache.executor.CacheExecutor;
import com.my.cache.executor.CacheProfilerExecutor;
import com.my.cache.service.Cacheable;
import com.my.cache.support.CacheAnnotationParser;
import com.my.cache.support.CacheManager;
import com.my.cache.support.MyCacheAnnotationParser;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: chenmingyu
 * @date: 2019/9/23 17:44
 * @description: 缓存注解MyCacheabel切面
 */
@Aspect
public class CacheAspect {

    /**
     * 解析注解
     */
    private CacheAnnotationParser cacheAnnotationParser = new MyCacheAnnotationParser();

    /**
     * 执行器
     */
    private static final Map<String,CacheExecutor> CACHE_EXECUTOR_MAP = new HashMap<>();

    static {
        CACHE_EXECUTOR_MAP.put("cacheProfiler", new CacheProfilerExecutor());
        CACHE_EXECUTOR_MAP.put("cacheEvictProfiler", new CacheEvictProfilerExecutor());
    }

    @Autowired
    private CacheManager cacheManager;

    /**
     * CacheProfiler
     */
    @Pointcut("@annotation(com.my.cache.annotation.CacheProfiler)")
    public void cacheProfilerPoint(){
    }

    /**
     * CacheEvictProfiler
     */
    @Pointcut("@annotation(com.my.cache.annotation.CacheEvictProfiler)")
    public void CacheEvictProfiler(){
    }


    /**
     * 通知
     * @param joinPoint
     * @return
     */
    @Around("cacheProfilerPoint() || CacheEvictProfiler()")
    public Object aroundHandle(ProceedingJoinPoint joinPoint) {
        Object obj = null;
        try {
            BasicCache basicCache = cacheAnnotationParser.parseCacheAnnotations(joinPoint);
            if(null == basicCache || !basicCache.getCondition()){
                return joinPoint.proceed();
            }
            CacheExecutor cacheExecutor = CACHE_EXECUTOR_MAP.get(basicCache.getAnnotationTypeEnum().getAnnotationType());
            if(null == cacheExecutor){
                return joinPoint.proceed();
            }
            Cacheable cacheable = cacheManager.getCacheable(basicCache);
            return cacheExecutor.execute(joinPoint, basicCache, cacheable);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return obj;
    }
}
