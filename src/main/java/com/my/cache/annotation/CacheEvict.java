package com.my.cache.annotation;

import org.springframework.cache.annotation.CacheConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @auther: chenmingyu
 * @date: 2019/9/28 09:32
 * @description: 清除缓存注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheEvict {

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

}
