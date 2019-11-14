package com.my.cache.support;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.my.cache.domain.BasicCacheOperation;
import com.my.cache.service.ApplicationCache;
import com.my.cache.service.Cache;
import com.my.cache.service.DistributedCache;
import org.springframework.lang.Nullable;

import java.util.Collections;
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
     * key对应的缓存
     */
    private final Map<String, Cache> cacheMap = new ConcurrentHashMap<>(16);

    /**
     * 统计的时候使用
     */
    private volatile Set<String> cacheNames = new LinkedHashSet();

    @Override
    public Cache getCache(String name) {
        Cache cache = this.cacheMap.get(name);
        if (cache != null) {
            return cache;
        }
        synchronized (this.cacheMap) {
            cache = this.cacheMap.get(name);
            if (cache == null) {
                cache = getMissingCache(name,null);
                if (cache != null) {
                    this.cacheMap.put(name, cache);
                    cacheNames.add(name);
                }
            }
            return cache;
        }
    }

    /**
     * 创建cache
     * @return
     */
    private Cache getMissingCache(String name, BasicCacheOperation basicCacheOperation){
        ApplicationCache applicationCache = new ApplicationCache();
        return applicationCache;
    }


    public DefaultCacheManager(DistributedCache distributedCache) {
        this.distributedCache = distributedCache;
    }
}
