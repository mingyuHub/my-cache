package com.my.cache.support;

import com.my.cache.annotation.CacheEvictProfiler;
import com.my.cache.annotation.CacheProfiler;
import com.my.cache.constant.SeparatorConstant;
import com.my.cache.domain.BasicCacheOperation;
import com.my.cache.domain.CacheEvictProfilerOperation;
import com.my.cache.domain.CacheProfilerOperation;
import com.my.cache.expression.CacheExpressionEvaluator;
import com.my.cache.support.CacheAnnotationParser;
import com.my.cache.support.KeyGenerator;
import com.my.cache.util.ToStringUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author: chenmingyu
 * @date: 2019/9/30 14:17
 * @description:
 */
public class MyCacheAnnotationParser implements CacheAnnotationParser {

    private static final Set<Class<? extends Annotation>> CACHE_OPERATION_ANNOTATIONS = new LinkedHashSet<>(8);

    private static final String DEFAULT_KEY_GENERATOR = "defaultKeyGenerator";

    @Autowired
    private Map<String,KeyGenerator> keyGeneratorMap;

    private final CacheExpressionEvaluator evaluator = new CacheExpressionEvaluator();

    static {
        CACHE_OPERATION_ANNOTATIONS.add(CacheProfiler.class);
        CACHE_OPERATION_ANNOTATIONS.add(CacheEvictProfiler.class);
    }

    @Override
    public BasicCacheOperation parseCacheAnnotations(ProceedingJoinPoint joinPoint) {
        try {
            Method method = this.getMethod(joinPoint);
            Set<? extends Annotation> anns = AnnotatedElementUtils.findAllMergedAnnotations(method, CACHE_OPERATION_ANNOTATIONS);
            if(CollectionUtils.isEmpty(anns)){
                return null;
            }
            final List<BasicCacheOperation> cacheInformation = new ArrayList<>(1);
            anns.stream().filter(ann -> ann instanceof CacheEvictProfiler).forEach(ann->{
                cacheInformation.add(parseCacheEvictProfiler((CacheEvictProfiler) ann, method, joinPoint));
            });
            anns.stream().filter(ann -> ann instanceof CacheProfiler).forEach(ann->{
                cacheInformation.add(parseCacheProfiler((CacheProfiler) ann, method, joinPoint));
            });
            if(CollectionUtils.isEmpty(cacheInformation)){
                return null;
            }
            return cacheInformation.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取 Method
     * @param jp
     * @return
     * @throws Exception
     */
    public Method getMethod(JoinPoint jp) throws Exception {
        MethodSignature msig = (MethodSignature)jp.getSignature();
        Method method = msig.getMethod();
        return BridgeMethodResolver.findBridgedMethod(method);
    }

    /**
     * 解析CacheProfiler
     * @return
     */
    private BasicCacheOperation parseCacheProfiler(CacheProfiler cacheProfiler, Method method, ProceedingJoinPoint joinPoint){
        CacheProfilerOperation cacheProfilerOperation = new CacheProfilerOperation();
        cacheProfilerOperation.setClassName(method.getDeclaringClass().getName());
        cacheProfilerOperation.setMethodName(method.getName());
        cacheProfilerOperation.setParams(joinPoint.getArgs());
        cacheProfilerOperation.setPrefixKey(cacheProfiler.prefixKey());
        Object key = parseKey(cacheProfiler.key(), method, joinPoint.getArgs(),joinPoint.getTarget());
        cacheProfilerOperation.setKey(ToStringUtils.toString(key));
        cacheProfilerOperation.setUseParam(cacheProfiler.useParams());
        cacheProfilerOperation.setKeyGenerator(cacheProfiler.keyGenerator());
        cacheProfilerOperation.setExpire(cacheProfiler.expire());
        cacheProfilerOperation.setTimeUnit(cacheProfiler.timeUnit());
        cacheProfilerOperation.setLocalCache(cacheProfiler.localCache());
        cacheProfilerOperation.setLocalCacheExpire(cacheProfiler.localCacheExpire());
        cacheProfilerOperation.setCondition(parseCondition(cacheProfiler.condition(), method, joinPoint.getArgs(), joinPoint.getTarget()));
        cacheProfilerOperation.setCacheName(generateCacheName(cacheProfilerOperation));
        return cacheProfilerOperation;
    }

    /**
     * 解析CacheEvictProfiler
     * @return
     */
    private BasicCacheOperation parseCacheEvictProfiler(CacheEvictProfiler cacheEvictProfiler, Method method, ProceedingJoinPoint joinPoint){
        CacheEvictProfilerOperation cacheEvictProfilerOperation = new CacheEvictProfilerOperation();
        cacheEvictProfilerOperation.setClassName(method.getDeclaringClass().getSimpleName());
        cacheEvictProfilerOperation.setMethodName(method.getName());
        cacheEvictProfilerOperation.setParams(joinPoint.getArgs());
        cacheEvictProfilerOperation.setPrefixKey(cacheEvictProfiler.prefixKey());
        Object key = parseKey(cacheEvictProfiler.key(), method, joinPoint.getArgs(),joinPoint.getTarget());
        cacheEvictProfilerOperation.setKey(key.toString());
        cacheEvictProfilerOperation.setUseParam(cacheEvictProfiler.useParams());
        cacheEvictProfilerOperation.setKeyGenerator(cacheEvictProfiler.keyGenerator());
        cacheEvictProfilerOperation.setAsyncEvict(cacheEvictProfiler.asyncEvict());
        cacheEvictProfilerOperation.setBeforeExecute(cacheEvictProfiler.beforeExecute());
        cacheEvictProfilerOperation.setCondition(parseCondition(cacheEvictProfiler.condition(), method, joinPoint.getArgs(), joinPoint.getTarget()));
        cacheEvictProfilerOperation.setCacheName(generateCacheName(cacheEvictProfilerOperation));

        return cacheEvictProfilerOperation;
    }

    /**
     * generate cacheName
     * @param cacheOperation
     * @return
     */
    private String generateCacheName(BasicCacheOperation cacheOperation){
        StringBuilder cacheName = new StringBuilder();
        if(StringUtils.isNotBlank(cacheOperation.getPrefixKey())){
            cacheName.append(cacheOperation.getPrefixKey());
            if(StringUtils.isNotBlank(cacheOperation.getKey())){
                cacheName.append(SeparatorConstant.MH).append(cacheOperation.getKey());
            } else if (cacheOperation.getUseParam()) {
                cacheName.append(SeparatorConstant.MH).append(ToStringUtils.arrayToString(cacheOperation.getParams()));
            }
            return cacheName.toString();
        }
        if(StringUtils.isBlank(cacheOperation.getKeyGenerator())){
            cacheOperation.setKeyGenerator(DEFAULT_KEY_GENERATOR);
        }
        return keyGeneratorMap.get(cacheOperation.getKeyGenerator()).generate(cacheOperation.getClassName(), cacheOperation.getMethodName(), cacheOperation.getParams());
    };

    /**
     * 创建缓存
     * @param method
     * @param args
     * @param target
     * @return
     */
    private EvaluationContext createEvaluationContext(Method method, Object[] args, Object target, Class<?> targetClass){
         return evaluator.createEvaluationContext(method, args, target, targetClass);
    }

    /**
     * 解析condition
     * @param conditionExpression
     * @param method
     * @param args
     * @param target
     * @return default condition
     */
    private boolean parseCondition(String conditionExpression, Method method, Object[] args, Object target){
        if(StringUtils.isBlank(conditionExpression)){
            return true;
        }
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(target);
        EvaluationContext context = createEvaluationContext(method, args, target, targetClass);
        AnnotatedElementKey annotatedElementKey = new AnnotatedElementKey(method, targetClass);
        return evaluator.condition(conditionExpression, annotatedElementKey, context);
    }

    /**
     * 解析key
     * @param keyExpression
     * @param method
     * @param args
     * @param target
     * @return
     */
    private Object parseKey(String keyExpression, Method method, Object[] args, Object target){
        if(StringUtils.isBlank(keyExpression)){
            return null;
        }
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(target);
        EvaluationContext context = createEvaluationContext(method, args, target, targetClass);
        AnnotatedElementKey annotatedElementKey = new AnnotatedElementKey(method, targetClass);
        return evaluator.key(keyExpression, annotatedElementKey, context);
    }
}
