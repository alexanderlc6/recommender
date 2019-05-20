package com.sp.cf.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sp.cf.common.redis.RedisBean;
import com.sp.cf.common.redis.RedisClusterConfig;
import com.sp.cf.common.redis.RedisClusterProperties;
import com.sp.cf.common.redis.RedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author lifeia
 *         缓存配置管理
 */
@Configuration
@EnableCaching
public class CacheConfiguration extends CachingConfigurerSupport {

    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }

    /**
     * 生成key的策略
     *
     * @return
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName().split("\\$\\$",2)[0] + ":");
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(":" + obj.toString());
            }
            return sb.toString();
        };
    }

    /**
     * 管理缓存
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        //设置缓存过期时间
        // rcm.setDefaultExpiration(60);//秒
        return new RedisCacheManager(redisTemplate);
    }

    /**
     * RedisTemplate配置
     *
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Autowired
    private RedisClusterConfig redisClusterConfig;

    @Autowired
    private RedisClusterProperties redisClusterProperties;

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        /*JedisPoolConfig poolCofig = new JedisPoolConfig();
        poolCofig.setMaxIdle(Integer.parseInt(redisClusterProperties.getMaxIdle()));
        poolCofig.setMaxWaitMillis(Long.parseLong(redisClusterProperties.getTimeout()));
        poolCofig.setMaxTotal(Integer.parseInt(redisClusterProperties.getMaxActive()));
        poolCofig.setMinIdle(Integer.parseInt(redisClusterProperties.getMinIdle()));

        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
        clusterConfiguration.setMaxRedirects(4);
        clusterConfiguration.setClusterNodes(redisClusterConfig.redisNodeList());
        return new JedisConnectionFactory(clusterConfiguration,poolCofig);*/

        //redis单机设置
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
        redisConnectionFactory.setHostName(redisProperties.getHost());
        redisConnectionFactory.setPort(this.redisProperties.getPort());
        redisConnectionFactory.setPassword(this.redisProperties.getPassword());
        redisConnectionFactory.setUsePool(true);
        redisConnectionFactory.setPoolConfig(new JedisPoolConfig());
        return redisConnectionFactory;
    }

    /**
     * REDIS 缓存操作工具类
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public RedisBean redisBean(RedisTemplate redisTemplate) {
        return new RedisBean(redisTemplate);
    }

}
