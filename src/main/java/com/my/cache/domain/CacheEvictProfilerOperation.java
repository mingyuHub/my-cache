package com.my.cache.domain;

import lombok.Data;

/**
 * @author: chenmingyu
 * @date: 2019/9/30 14:32
 * @description:
 */
@Data
public class CacheEvictProfilerOperation extends BasicCacheOperation {

    /**
     * 异步删除
     */
    private boolean asyncEvict;

    /**
     * 先删除缓存在执行方法
     */
    private boolean beforeExecute;
}