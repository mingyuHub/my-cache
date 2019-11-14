package com.my.cache.expression;

import lombok.Getter;

import java.lang.reflect.Method;

/**
 * @author: chenmingyu
 * @date: 2019/10/15 15:38
 * @description: 参照springCache实现  CacheExpressionRootObject
 */
@Getter
public class CacheExpressionRootObject {

    private final Method method;

    private final Object[] args;

    private final Object target;

    private final Class<?> targetClass;


    public CacheExpressionRootObject(Method method, Object[] args, Object target, Class<?> targetClass) {
        this.method = method;
        this.target = target;
        this.targetClass = targetClass;
        this.args = args;
    }
}
