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
public @interface CacheEvictProfiler {

    /**
     * key前缀
     */
    String prefixKey() default "";;

    /**
     * 入参
     * 支持SpEL表达式
     */
    String key() default "";

    /**
     * 拼接key，优先级小于key()
     * @return
     */
    boolean useParams() default true;

    /**
     * 当SpEL表达式为true时进行删除
     * @return
     */
    String condition() default "";

    /**
     * 异步删除缓存
     * @return
     */
    boolean asyncEvict() default false;

    /**
     * 方法执行之前删除缓存
     * @return
     */
    boolean beforeExecute() default true;

    /**
     * key生成方式
     */
    String keyGenerator() default "";
}
