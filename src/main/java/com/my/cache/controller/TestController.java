package com.my.cache.controller;

import com.my.cache.annotation.Cache;
import com.my.cache.config.DefaultCacheConfig;
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
    @Cache(prefixKey = "test:name", condition = "#age lt 10")
    public String test(Integer age,String name){

        System.out.println("this is test");
        return "name";
    }
}
