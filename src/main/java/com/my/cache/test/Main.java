package com.my.cache.test;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

/**
 * @author: chenmingyu
 * @date: 2019/9/23 15:50
 * @description:
 */
public class Main {

    public static void main(String[] args) {
        // 手动加载
        Cache<String, Object> manualCache = Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .build();

        manualCache.put("1","test");

        System.out.println(manualCache.getIfPresent("1"));

        // 同步加载
        LoadingCache<String, Object> loadingCache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build(key -> createTestValue(key));

    }

    private static Object createTestValue(String k) {
        return k;
    }
}
