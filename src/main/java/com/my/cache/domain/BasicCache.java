package com.my.cache.domain;

import com.my.cache.constant.AnnotationTypeEnum;
import com.my.cache.constant.CacheTypeEnum;
import lombok.Data;

/**
 * @author: chenmingyu
 * @date: 2019/9/27 21:59
 * @description:
 */
@Data
public class BasicCache {

    /**
     * 类名称
     *//*
    private String className;
    *//**
     * 方法名
     *//*
    private String methodName;
    *//**
     * 方法入参
     *//*
    private Object[] params;*/

    /**
     * 缓存名称
     * 默认：prefixKey + key
     * 详见：
     */
    private String cacheName;
    /**
     * 缓存名称前缀
     *//*
    private String prefixKey;
    *//**
     * 缓存key，由SePL表达式解析而来
     *//*
    private String key;
    *//**
     * 是否使用入参拼接缓存名称
     *//*
    private Boolean useParam;*/

    /**
     * 条件判断，由SePL表达式解析而来，true执行缓存逻辑，false执行业务逻辑
     */
    private Boolean condition;
    /**
     * 是否支持异步
     */
    private Boolean async;

    /**
     * 默认生成缓存名称的BeanName
     */
    private String keyGenerator;

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
     * 本地缓存过期时间
     */
    private Long localCacheExpire;
    /**
     * 分布式缓存过期时间
     */
    private Long distributedCacheExpire;
}
