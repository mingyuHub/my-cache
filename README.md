### my-cahce

### 简介

my-cache是一个轻量级的多级缓存框架，可快速接入到当前开发的项目中，支持本地缓存（一级缓存）及分布式缓存（二级缓存），默认使用Caffeine作为本地缓存，分布式缓存提供接口供开发者自定义实现，
推荐使用redis作为分布式缓存，支持热点分布式缓存自动发现并升级为本地缓存，并在流量高峰期后自动删除，当然也支持更多的自定义设置。

#### 功能

基于注解实现灵活配置，提供多维度缓存设计

支持热点缓存自动发现，升级为本地缓存，并在流量高峰期后自动删除

多维度的缓存自定义设置，支持更细粒度的缓存设置，满足绝大多数的缓存场景

缓存注解支持SePL表达式进行配置，使用上更灵活，并提供相应的默认设置，如缓存的key生成方式，禁用本地缓存等

提供各级缓存命中率统计功能，提供web监控面板

分布式缓存支持自定义实现，接入使用更便捷高效


### 为什么将分布式缓存设计为需要自定义实现？

假设my-cache提供了使用redis实现的分布式缓存实现

场景一
现在有一个系统A想要接入的my-cache，自身的缓存使用ehcache实现，作为一个框架，不应该强制系统A接入redis，接入redis就意味着系统复杂度的增加，需要考虑到redis的可用性

场景二
现在有一个系统B想要接入my-cache，自身使用的是redis缓存，心里想，这回好了可以直接用了，是可以直接使用了，但是如果系统B在使用缓存的时候为了系统健壮性，采用的缓存主备集群双写的策略那

以上这两种场景my-cache提供的默认实现就显得多余了

如果使用自定义实现分布式缓存，我们只需要实现 rediscache提供的接口就行了，如果系统中已经有缓存实现，直接包一层就可以了，对于没有缓存实现的系统而言就必须去接入一个分布式缓存，
然后通过实现接口，提供缓存就可以了

在对比了提供默认分布式缓存与自定义实现分布式缓存的收益后，决定采用用户自定义分布式缓存的实现的方式


#### 生成缓存的key名称
思路：
两个缓存注解共有的参数
    
    /**
     * key前缀
     */
    String prefixKey() default "";

    /**
     * 入参
     * 支持SpEL表达式
     */
    String key() default "";

    /**
     * 拼接key，优先级小于key()
     * @return
     */
    boolean useParams() default true;

如果prefixKey 有值
    判断key的值
        如果有值，则缓存的key名称 = prefixKey:key
        如果没有值，判断useParams是否为true
        如果为true，则缓存的key名称 = prefixKey:方法入参
如果prefixKey 没有值
    则调用KeyGenerator的实现，生成缓存的key名称
    （可以自定义实现）