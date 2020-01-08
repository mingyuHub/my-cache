package com.my.cache.domain;


import org.apache.commons.lang.StringUtils;

/**
 * @auther: chenmingyu
 * @date: 2019/10/11 10:58
 * @description: 注解执行器枚举
 */
public enum CacheAnnotationEnum {

    CACHE_PROFILER_EXECUTOR("CacheProfilerOperation","cacheProfilerExecutor"),
    CACHE_EVICT_PROFILER_EXECUTOR("CacheEvictProfilerOperation","cacheEvictProfilerExecutor");

    private String beanName;
    /**
     * 注解执行器
     */
    private String annotationExecutor;

    CacheAnnotationEnum(String beanName, String annotationExecutor) {
        this.beanName = beanName;
        this.annotationExecutor = annotationExecutor;
    }

    public static String getAnnotationExecutorByBeanName(String beanName){
        if(StringUtils.isBlank(beanName)){
            return StringUtils.EMPTY;
        }
        for(CacheAnnotationEnum annotationEnum: CacheAnnotationEnum.values()){
            if(annotationEnum.getBeanName().equals(beanName)){
                return annotationEnum.getAnnotationExecutor();
            }
        }
        return StringUtils.EMPTY;
    }

    private String getBeanName() {
        return beanName;
    }

    private String getAnnotationExecutor() {
        return annotationExecutor;
    }
}
