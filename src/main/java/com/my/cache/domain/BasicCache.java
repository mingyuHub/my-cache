package com.my.cache.domain;

import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * @author: chenmingyu
 * @date: 2019/9/27 21:59
 * @description:
 */
@Data
public class BasicCache {
    /**
     * 缓存名称
     * 格式: 前缀:后缀
     * 默认: prefixKey + key  替补: beanName:method:params
     * 详见:
     */
    private String cacheName;

    /**
     * 条件判断
     * 由SePL表达式解析而来，true执行缓存逻辑，false执行业务逻辑
     */
    private Boolean condition;
    /**
     * 是否支持异步
     */
    private Boolean async;

    /**
     * 支持的缓存类型
     * 该参数决定了创建缓存的类型
     */
    private CacheTypeEnum cacheTypeEnum;
    /**
     * 支持的缓存类型
     * 该参数决定了创建缓存的类型
     */
    private AnnotationTypeEnum annotationTypeEnum;

    /**
     * 时间单位
     */
    private TimeUnit timeUnit;
    /**
     * 本地缓存过期时间
     */
    private Long localCacheExpire;
    /**
     * 分布式缓存过期时间
     */
    private Long distributedCacheExpire;
}
