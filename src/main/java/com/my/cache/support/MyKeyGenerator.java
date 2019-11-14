package com.my.cache.support;

import com.alibaba.fastjson.JSONObject;
import com.my.cache.constant.SeparatorConstant;
import com.my.cache.support.KeyGenerator;
import com.my.cache.util.ToStringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author: chenmingyu
 * @date: 2019/10/14 15:04
 * @description:
 */
@Service("defaultKeyGenerator")
public class MyKeyGenerator implements KeyGenerator {

    @Override
    public String generate(String target, String method, Object... params) {
        StringBuilder stringBuilder = new StringBuilder();
        if(StringUtils.isNotBlank(target)){
            stringBuilder.append(target);
        }
        if(StringUtils.isNotBlank(method)){
            stringBuilder.append(SeparatorConstant.MH).append(method);
        }
        if(ArrayUtils.isNotEmpty(params)){
            stringBuilder.append(ToStringUtils.arrayToString(params));
        }
        return stringBuilder.toString();
    }


}
