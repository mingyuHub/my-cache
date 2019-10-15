package com.my.cache.support;

import java.lang.reflect.Method;

/**
 * @author: chenmingyu
 * @date: 2019/10/14 14:58
 * @description:
 */
public interface KeyGenerator {

    String generate(String target, String method, Object... params);
}
