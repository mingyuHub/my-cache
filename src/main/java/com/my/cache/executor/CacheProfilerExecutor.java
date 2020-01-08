package com.my.cache.executor;

import com.my.cache.domain.BasicCache;
import com.my.cache.service.Cacheable;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author: chenmingyu
 * @date: 2019/10/10 14:45
 * @description: CacheProfiler 注解执行策略
 */
public class CacheProfilerExecutor implements CacheExecutor {

    @Override
    public Object execute(ProceedingJoinPoint joinPoint, BasicCache basicCache, Cacheable cacheable) {
        Object result = null;
        try {
            result = cacheable.get(basicCache.getCacheName());
            if(null != result){
                return result;
            }
            result = joinPoint.proceed();
            if(null != result){
                cacheable.set(basicCache.getCacheName(),result);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }

}
