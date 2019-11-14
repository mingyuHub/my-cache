package com.my.cache.executor;

import com.my.cache.domain.BasicCacheOperation;
import com.my.cache.domain.CacheProfilerOperation;
import com.my.cache.executor.AbstractCacheExecutor;
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
                result = localCache.get(key);
            }
            if(null != result){
                return result;
            }
            result = distributedCache.get(key);
            if(null == result){
                result = joinPoint.proceed();
            }
            distributedCache.setEx(key,result,cacheProfilerOperation.getExpire(),cacheProfilerOperation.getTimeUnit());
            localCache.setEx(key,result,cacheProfilerOperation.getExpire(),cacheProfilerOperation.getTimeUnit());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }

}
