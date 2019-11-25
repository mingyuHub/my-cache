package com.my.cache.support;

import com.my.cache.constant.CacheTypeEnum;
import com.my.cache.domain.BasicCache;
import com.my.cache.service.ApplicationCache;
import com.my.cache.service.CacheDecorator;
import com.my.cache.service.Cacheable;
import com.my.cache.service.DistributedCache;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: chenmingyu
 * @date: 2019/11/11 23:19
 * @description:
 */
public class DefaultCacheManager implements CacheManager{

    /**
     * 分布式缓存
     */
    private DistributedCache distributedCache;

    /**
     * 带有本地缓存的Cacheable Map
     * key：过期时间 long
     * value：缓存
     */
    private final Map<Long, Cacheable> cacheMap = new ConcurrentHashMap<>(16);

    /**
     * 统计的时候使用
     */
    private volatile Set<String> cacheNames = new LinkedHashSet();

    public DefaultCacheManager(DistributedCache distributedCache) {
        this.distributedCache = distributedCache;
    }

    @Override
    public Set<String> cacheNames() {
        return cacheNames;
    }

    @Override
    public Cacheable getCacheable(BasicCache basicCache) {
        // 分布式缓存
        if(CacheTypeEnum.DISTRIBUTED.equals(basicCache.getCacheTypeEnum())){
            cacheNames.add(basicCache.getCacheName());
            return distributedCache;
        }

        // 本地缓存或者多级缓存
        Cacheable cache = this.cacheMap.get(basicCache.getLocalCacheExpire());
        if (cache != null) {
            return cache;
        }
        synchronized (this.cacheMap) {
            cache = this.cacheMap.get(basicCache.getLocalCacheExpire());
            if (cache == null) {
                cache = createCache(basicCache);
                if (cache != null) {
                    this.cacheMap.put(basicCache.getLocalCacheExpire(), cache);
                    cacheNames.add(basicCache.getCacheName());
                }
            }
            return cache;
        }
    }


    /**
     * 创建缓存Cacheable
     * @return
     */
    private Cacheable createCache(BasicCache basicCache){
        // 本地缓存
        ApplicationCache applicationCache = new ApplicationCache();
        if(CacheTypeEnum.APPLICATION.equals(basicCache.getCacheTypeEnum())){
            return applicationCache;
        }
        // 多级缓存
        CacheDecorator cacheDecorator = new CacheDecorator(applicationCache,distributedCache);
        return cacheDecorator;
    }

}
