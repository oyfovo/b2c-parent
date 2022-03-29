package com.turing.b2c.content.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

/**
 * @author HHF-QAQ
 * @date 2021年12月17日 20:49
 * 在Spring Boot使用Cache的时候，默认使用的是**JDK**提供的序列化机制。
 * 为了存储方便，我们可以增加如下配置来将序列化机制改为使用**JSON**格式来进行转化。
 */
@Configuration
public class RedisConfig {
    /**
     * 配置一个RedisTemplate，为了操作Redis
     * 直接使用RedisTemplate时使用JSON进行序列化。
     * @author HHF-OVO
     * @date 2021/12/17 21:02
     * @param redisConnectionFactory
     * @return org.springframework.data.redis.core.RedisTemplate<java.lang.Object,java.lang.Object>
     */
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        //创建RedisTemplate模板
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        //设置连接工厂
        template.setConnectionFactory(redisConnectionFactory);
        //设置默认的序列化返回为JSON
        // 设置默认的Serialize，包含 keySerializer & valueSerializer
        GenericFastJsonRedisSerializer serializer = new GenericFastJsonRedisSerializer();
        template.setDefaultSerializer(serializer);
        return template;
    }
    
    /**
     * 可选的，为了使用注解@Cacheable，如果不用注解，直接使用template.opsForHash()就不需要此配置
     * 配置RedisCacheManager【缓存管理器】
     * @author HHF-OVO
     * @date 2021/12/17 21:13
     * @param redisConnectionFactory 
     * @return org.springframework.data.redis.cache.RedisCacheManager
     */
    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory){
        //使用缓存管理构建者对象
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericFastJsonRedisSerializer())));
        return builder.build();
    }


}

