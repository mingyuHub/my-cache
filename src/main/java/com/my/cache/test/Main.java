package com.my.cache.test;


/**
 * @author: chenmingyu
 * @date: 2019/9/23 15:50
 * @description:
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("Cache "+ new Cache.Builder().setName("cmy").setCondition("condition").build());

    }

}