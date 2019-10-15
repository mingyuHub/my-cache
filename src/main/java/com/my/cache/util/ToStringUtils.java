package com.my.cache.util;

import com.alibaba.fastjson.JSONObject;
import com.my.cache.constant.SeparatorConstant;

/**
 * @author: chenmingyu
 * @date: 2019/10/15 17:28
 * @description: toString工具类
 */
public class ToStringUtils {


    /**
     * 入参转化为字符串
     * @param param
     */
    public static String toString(Object param){
        String result = "";
        if(null == param){
            return null;
        }
        if(param instanceof String){
            result = param.toString();
        } else if (param.getClass().isPrimitive()) {
            result = param.toString();
        } else {
            result = JSONObject.toJSONString(param);
        }
        return result;
    }

    /**
     * 数组转化为字符串
     * @param params
     */
    public static String arrayToString(Object... params){
        StringBuilder arrayString = new StringBuilder();
        for (Object param : params){
            if(null == param){
                continue;
            }
            if(param instanceof String){
                arrayString.append(SeparatorConstant.MH).append(param);
            } else if (param.getClass().isPrimitive()) {
                arrayString.append(SeparatorConstant.MH).append(param.toString());
            } else {
                arrayString.append(SeparatorConstant.MH).append(JSONObject.toJSONString(param));
            }
        }
        return arrayString.toString();
    }

}
