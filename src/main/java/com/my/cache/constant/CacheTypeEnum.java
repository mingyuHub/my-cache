package com.my.cache.constant;

/**
 * @auther: chenmingyu
 * @date: 2019/11/25 16:02
 * @description: 注解类型
 */
public enum CacheTypeEnum {

    APPLICATION("本地缓存"),
    DISTRIBUTED("分布式缓存"),
    ALL("多级缓存");

    private String description;

    CacheTypeEnum(String description) {
        this.description = description;
    }
}
