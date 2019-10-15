package com.my.cache.domain;

import org.springframework.util.StringUtils;

/**
 * @auther: chenmingyu
 * @date: 2019/10/11 10:58
 * @description: 注解，注解执行器对应关系枚举
 */
public enum CacheAnnotationEnum {

    CACHE_PROFILER_EXECUTOR("CacheProfilerOperation","cacheProfilerExecutor"),
    CACHE_EVICT_PROFILER_EXECUTOR("CacheEvictProfilerOperation","cacheEvictProfilerExecutor");

    private String beanName;

    private String annotationExecutor;

    CacheAnnotationEnum(String beanName, String annotationExecutor) {
        this.beanName = beanName;
        this.annotationExecutor = annotationExecutor;
    }

    public static String getAnnotationExecutorByBeanName(String beanName){
        if(StringUtils.isEmpty(beanName)){
            return "";
        }
        for(CacheAnnotationEnum annotationEnum: CacheAnnotationEnum.values()){
            if(annotationEnum.getBeanName().equals(beanName)){
                return annotationEnum.getAnnotationExecutor();
            }
        }
        return "";
    }

    public String getBeanName() {
        return beanName;
    }

    public String getAnnotationExecutor() {
        return annotationExecutor;
    }
}
