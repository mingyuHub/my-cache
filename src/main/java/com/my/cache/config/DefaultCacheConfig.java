package com.my.cache.config;

import com.my.cache.domain.BasicCache;
import com.my.cache.domain.CacheTypeEnum;
import com.my.cache.exception.CacheException;
import com.my.cache.service.CacheDecorator;
import com.my.cache.service.Cacheable;
import com.my.cache.service.DistributedCache;
import com.my.cache.service.LocalCache;
import com.my.cache.support.KeyGenerator;
import com.my.cache.support.MyKeyGenerator;
import org.springframework.util.Assert;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: chenmingyu
 * @date: 2019/11/11 23:19
 * @description:
 */
public class DefaultCacheConfig implements CacheConfig {

    /**
     * 分布式缓存
     */
    private DistributedCache distributedCache;

    /**
     * 缓存名称生成器
     */
    private KeyGenerator keyGenerator;

    /**
     * 缓存Map
     * key：过期时间 long
     * value：缓存操作对象
     */
    private final Map<Long, Cacheable> cacheMap = new ConcurrentHashMap<>(16);

    /**
     * 统计的时候使用
     */
    private volatile Set<String> cacheNames = new LinkedHashSet();

    private DefaultCacheConfig(Builder builder) {
        if(null == builder || null == builder.distributedCache){
            throw new CacheException("create DefaultCacheConfig: builder.distributedCache must not be null");
        }
        this.distributedCache = builder.distributedCache;
        this.keyGenerator = builder.keyGenerator;
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
        LocalCache applicationCache = new LocalCache(basicCache);
        if(CacheTypeEnum.LOCAL.equals(basicCache.getCacheTypeEnum())){
            return applicationCache;
        }
        // 多级缓存
        CacheDecorator cacheDecorator = new CacheDecorator(applicationCache,distributedCache);
        return cacheDecorator;
    }

    /**
     * 静态内部类
     */
    public static class Builder {

        /**
         * 分布式缓存
         */
        private DistributedCache distributedCache;

        /**
         * 缓存名称生成器
         */
        private KeyGenerator keyGenerator = new MyKeyGenerator();

        public Builder distributedCache(DistributedCache distributedCache) {
            this.distributedCache = distributedCache;
            return this;
        }

        public Builder keyGenerator(KeyGenerator keyGenerator) {
            this.keyGenerator = keyGenerator;
            return this;
        }

        public DefaultCacheConfig build() {
            return new DefaultCacheConfig(this);
        }

    }
}
