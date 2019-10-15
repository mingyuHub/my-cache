package com.my.cache.service;

import com.my.cache.domain.BasicCacheOperation;
import com.my.cache.domain.CacheProfilerOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

/**
 * @author: chenmingyu
 * @date: 2019/10/10 14:45
 * @description: CacheProfiler 注解执行策略
 */
@Service
public class CacheProfilerExecutor extends AbstractCacheExecutor {

    @Override
    public Object execute(ProceedingJoinPoint joinPoint, BasicCacheOperation basicCacheInformation) {
        Object result = null;
        CacheProfilerOperation cacheProfilerOperation = null;
        try {
            if(basicCacheInformation instanceof CacheProfilerOperation){
                cacheProfilerOperation = (CacheProfilerOperation)basicCacheInformation;
            }
            String key = cacheProfilerOperation.getKey();
            if(cacheProfilerOperation.getLocalCache()){
                result = firstLevelCache.get(key);
            }
            if(null != result){
                return result;
            }
            result = secondLevelCache.get(key);
            if(null == result){
                result = joinPoint.proceed();
            }
            secondLevelCache.setEx(key,result,cacheProfilerOperation.getExpire(),cacheProfilerOperation.getTimeUnit());
            firstLevelCache.setEx(key,result,cacheProfilerOperation.getExpire(),cacheProfilerOperation.getTimeUnit());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }

}
