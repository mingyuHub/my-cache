package com.my.cache.domain;

import lombok.Data;

import java.util.Set;

/**
 * @author: chenmingyu
 * @date: 2019/9/27 21:59
 * @description:
 */
@Data
public class BasicCacheOperation {

    private String className;

    private String methodName;

    private Object[] params;

    private String cacheName;

    private String prefixKey;

    private String key;

    private String keyGenerator;

    private Boolean useParam;

    private Boolean condition;
}
