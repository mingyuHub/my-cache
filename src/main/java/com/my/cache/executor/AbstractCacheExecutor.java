package com.my.cache.executor;

import com.my.cache.service.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author: chenmingyu
 * @date: 2019/10/10 16:37
 * @description:
 */
public abstract class AbstractCacheExecutor implements CacheExecutor {

    @Autowired
    @Qualifier("localCache")
    protected Cache localCache;

    @Autowired
    @Qualifier("distributedCache")
    protected Cache distributedCache;
}
