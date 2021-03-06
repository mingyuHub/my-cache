package com.my.cache.executor;

import com.my.cache.domain.BasicCache;
import com.my.cache.service.Cacheable;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author: chenmingyu
 * @date: 2019/10/10 15:18
 * @description: 清除缓存注解执行器
 */
public class CacheEvictProfilerExecutor implements CacheExecutor {

    @Override
    public Object execute(ProceedingJoinPoint joinPoint, BasicCache basicCache, Cacheable cacheable) {
        Object result = null;
        try {
            if(basicCache.getAsync()){
                cacheable.invalidate(basicCache.getCacheName());
            }
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }
}
