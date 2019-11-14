package com.my.cache.executor;

import com.my.cache.domain.BasicCacheOperation;
import com.my.cache.domain.CacheEvictProfilerOperation;
import com.my.cache.executor.AbstractCacheExecutor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

/**
 * @author: chenmingyu
 * @date: 2019/10/10 15:18
 * @description: 清除缓存注解执行器
 */
@Service
public class CacheEvictProfilerExecutor extends AbstractCacheExecutor {

    @Override
    public Object execute(ProceedingJoinPoint joinPoint, BasicCacheOperation basicCacheInformation) {

        Object object = null;
        CacheEvictProfilerOperation cacheEvictProfilerOperation = null;
        try {
            if(basicCacheInformation instanceof CacheEvictProfilerOperation){
                cacheEvictProfilerOperation = (CacheEvictProfilerOperation)basicCacheInformation;
            }
            if(cacheEvictProfilerOperation.isBeforeExecute()){
                clearCache(cacheEvictProfilerOperation.getKey());
                return joinPoint.proceed();
            }
            joinPoint.proceed();
            clearCache(cacheEvictProfilerOperation.getKey());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return object;
    }

    private void clearCache(String key){
        distributedCache.del(key);
        localCache.del(key);
    };
}
