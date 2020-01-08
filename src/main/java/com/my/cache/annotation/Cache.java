package com.my.cache.annotation;

import com.my.cache.domain.CacheTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author: chenmingyu
 * @date: 2019/9/23 17:44
 * @description: 添加缓存注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {

    /**
     * 缓存名称 前缀
     */
    String prefixKey() default "";;

    /**
     * 缓存名称 后缀 由SePL表达式解析而来
     */
    String key() default "";

    /**
     * 缓存名称是否使用入参进行拼接
     * @return boolean
     */
    boolean useParams() default true;

    /**
     * 条件判断，由SePL表达式解析而来，true执行缓存逻辑，false执行业务逻辑
     * @return String
     */
    String condition() default "";

    /**
     * 是否支持异步
     * @return boolean
     */
    boolean async() default false;

    /**
     * 支持的缓存类型枚举
     * 该参数决定了最终创建缓存的类型
     * @return CacheTypeEnum
     */
    CacheTypeEnum cacheType() default CacheTypeEnum.ALL;

    /**
     * 缓存过期时间单位，默认为秒
     * @return TimeUnit
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 本地缓存过期时间
     */
    long localCacheExpire() default 30;

    /**
     * 分布式缓存过期时间
     * 如果不设置，30秒内无访问则进行删除
     */
    long distributedCacheExpire() default -1;
}
