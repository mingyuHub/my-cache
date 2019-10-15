package com.my.cache.domain;

import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * @author: chenmingyu
 * @date: 2019/9/27 21:34
 * @description:
 */
@Data
public class CacheProfilerOperation extends BasicCacheOperation {

    private Long expire;

    private Boolean localCache;

    private Long localCacheExpire;

    private TimeUnit timeUnit;
}
