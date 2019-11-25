package com.my.cache.constant;

/**
 * @auther: chenmingyu
 * @date: 2019/11/25 19:44
 * @description:
 */
public enum AnnotationTypeEnum {

    CACHE_PROFILER("cacheProfiler"),
    CACHE_EVICT_PROFILER("cacheEvictProfiler");

    /**
     * 注解类型
     */
    private String annotationType;

    AnnotationTypeEnum(String annotationType) {
        this.annotationType = annotationType;
    }

    public String getAnnotationType() {
        return annotationType;
    }
}
