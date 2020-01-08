package com.my.cache.support;

import com.my.cache.annotation.Cache;
import com.my.cache.annotation.CacheEvict;
import com.my.cache.domain.AnnotationTypeEnum;
import com.my.cache.domain.BasicCache;
import com.my.cache.domain.SeparatorConstant;
import com.my.cache.expression.CacheExpressionEvaluator;
import com.my.cache.util.ToStringUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.expression.EvaluationContext;
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

    private final CacheExpressionEvaluator evaluator = new CacheExpressionEvaluator();

    static {
        CACHE_OPERATION_ANNOTATIONS.add(Cache.class);
        CACHE_OPERATION_ANNOTATIONS.add(CacheEvict.class);
    }

    @Override
    public BasicCache parseCacheAnnotations(ProceedingJoinPoint joinPoint) {
        try {
            Method method = this.getMethod(joinPoint);

            Set<? extends Annotation> anns = AnnotatedElementUtils.findAllMergedAnnotations(method, CACHE_OPERATION_ANNOTATIONS);
            if(CollectionUtils.isEmpty(anns)){
                return null;
            }
            final List<BasicCache> cacheInformation = new ArrayList<>(1);
            anns.stream().filter(ann -> ann instanceof CacheEvict).forEach(ann->{
                cacheInformation.add(parseCacheEvictProfiler((CacheEvict) ann, method, joinPoint));
            });
            anns.stream().filter(ann -> ann instanceof Cache).forEach(ann->{
                cacheInformation.add(parseCacheProfiler((Cache) ann, method, joinPoint));
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
    public Method getMethod(JoinPoint jp) {
        MethodSignature msig = (MethodSignature)jp.getSignature();
        Method method = msig.getMethod();
        return BridgeMethodResolver.findBridgedMethod(method);
    }

    /**
     * 解析@CacheProfiler
     * @return
     */
    private BasicCache parseCacheProfiler(Cache cacheProfiler, Method method, ProceedingJoinPoint joinPoint){
        BasicCache basicCache = new BasicCache();
        /*basicCache.setClassName(method.getDeclaringClass().getName());
        basicCache.setMethodName(method.getName());
        basicCache.setParams(joinPoint.getArgs());*/
        Object object = parseKey(cacheProfiler.key(), method, joinPoint.getArgs(),joinPoint.getTarget());
        String key = "";
        if(Objects.nonNull(object)){
            key = object.toString();
        } else if(cacheProfiler.useParams()){
            key = ToStringUtils.arrayToString(joinPoint.getArgs());
        }
        String cacheName = generateCacheName(cacheProfiler.prefixKey(), key);
        basicCache.setCacheName(cacheName);
        basicCache.setCondition(parseCondition(cacheProfiler.condition(), method, joinPoint.getArgs(), joinPoint.getTarget()));
        basicCache.setAsync(cacheProfiler.async());
        basicCache.setCacheTypeEnum(cacheProfiler.cacheType());
        basicCache.setAnnotationTypeEnum(AnnotationTypeEnum.CACHE_PROFILER);
        basicCache.setTimeUnit(cacheProfiler.timeUnit());
        basicCache.setLocalCacheExpire(cacheProfiler.localCacheExpire());
        basicCache.setDistributedCacheExpire(cacheProfiler.distributedCacheExpire());
        return basicCache;
    }

    /**
     * 解析@CacheEvictProfiler
     * @return
     */
    private BasicCache parseCacheEvictProfiler(CacheEvict cacheEvictProfiler, Method method, ProceedingJoinPoint joinPoint){
        BasicCache basicCache = new BasicCache();
        Object object = parseKey(cacheEvictProfiler.key(), method, joinPoint.getArgs(),joinPoint.getTarget());
        String key = "";
        if(Objects.nonNull(object)){
            key = object.toString();
        } else if(cacheEvictProfiler.useParams()){
            key = ToStringUtils.arrayToString(joinPoint.getArgs());
        }
        String cacheName = generateCacheName(cacheEvictProfiler.prefixKey(), key);
        basicCache.setCacheName(cacheName);
        basicCache.setAsync(cacheEvictProfiler.async());
        basicCache.setAnnotationTypeEnum(AnnotationTypeEnum.CACHE_EVICT_PROFILER);
        basicCache.setCondition(parseCondition(cacheEvictProfiler.condition(), method, joinPoint.getArgs(), joinPoint.getTarget()));
        return basicCache;
    }

    /**
     *
     * @param prefixKey
     * @param key
     * @return
     */
    private String generateCacheName(String prefixKey, String key){
        StringBuilder cacheName = new StringBuilder();
        if (StringUtils.isNotBlank(prefixKey)) {
            cacheName.append(prefixKey);
            if (StringUtils.isNotBlank(key)) {
                cacheName.append(SeparatorConstant.MH).append(key);
            }
            return cacheName.toString();
        }
       /*if(StringUtils.isBlank(cacheOperation.getKeyGenerator())){
            cacheOperation.setKeyGenerator(DEFAULT_KEY_GENERATOR);
        }
        return keyGeneratorMap.get(cacheOperation.getKeyGenerator()).generate(cacheOperation.getClassName(), cacheOperation.getMethodName(), cacheOperation.getParams());*/
       return "";
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
