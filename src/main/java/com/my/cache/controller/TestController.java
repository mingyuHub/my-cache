package com.my.cache.controller;

import com.my.cache.annotation.CacheProfiler;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: chenmingyu
 * @date: 2019/9/25 14:42
 * @description:
 */
@RestController
public class TestController {

    @GetMapping("/test")
    @CacheProfiler(prefixKey = "test:name", condition = "#age lt 10")
    public String test(Integer age,String name){
        System.out.println("this is test");
        return "name";
    }
}
