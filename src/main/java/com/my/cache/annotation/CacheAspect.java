package com.my.cache.annotation;

import com.my.cache.domain.BasicCacheOperation;
import com.my.cache.constant.CacheAnnotationEnum;
import com.my.cache.executor.CacheEvictProfilerExecutor;
import com.my.cache.executor.CacheExecutor;
import com.my.cache.executor.CacheProfilerExecutor;
import com.my.cache.support.CacheAnnotationParser;
import com.my.cache.support.MyCacheAnnotationParser;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: chenmingyu
 * @date: 2019/9/23 17:44
 * @description: 缓存注解MyCacheabel切面
 */
@Aspect
@Component
public class CacheAspect {


    private CacheAnnotationParser cacheAnnotationParser = new MyCacheAnnotationParser();

    /**
     * 执行器
     */
    private static final Map<String,CacheExecutor> CACHE_EXECUTOR_MAP = new HashMap<>();

    static {
        CACHE_EXECUTOR_MAP.put("CacheProfilerOperation", new CacheProfilerExecutor());
        CACHE_EXECUTOR_MAP.put("CacheEvictProfilerOperation", new CacheEvictProfilerExecutor());
    }

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
            BasicCacheOperation basicCacheInformations = cacheAnnotationParser.parseCacheAnnotations(joinPoint);
            if(null == basicCacheInformations || !basicCacheInformations.getCondition()){
                return joinPoint.proceed();
            }
            CacheExecutor cacheExecutor = CACHE_EXECUTOR_MAP.get(CacheAnnotationEnum.getAnnotationExecutorByBeanName(basicCacheInformations.getClass().getSimpleName()));
            if(null == cacheExecutor){
                return joinPoint.proceed();
            }
            return cacheExecutor.execute(joinPoint, basicCacheInformations);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return obj;
    }
}
