package com.my.cache.test;

import org.springframework.util.Assert;

/**
 * @author: chenmingyu
 * @date: 2019/12/6 18:23
 * @description:
 */
public class Cache {

    private final String name;

    private final String condition;

    protected Cache(Builder b) {
        this.name = b.name;
        this.condition = b.condition;
    }

    public String getName() {
        return name;
    }

    public String getCondition() {
        return condition;
    }

    public static class Builder {

        private String name = "";
        private String condition = "";

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setCondition(String condition) {
            this.condition = condition;
            return this;
        }

        public Cache build(){
            return new Cache(this);
        };
    }
}
