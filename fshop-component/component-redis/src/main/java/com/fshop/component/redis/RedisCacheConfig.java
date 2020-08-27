/*
 * Copyright [2020] [rannuo]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fshop.component.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Description：Redis缓存配置
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
@EnableCaching
@Configuration
@Import(value = {RedisCacheProperties.class})
public class RedisCacheConfig extends CachingConfigurerSupport {

	@Resource
	private RedisCacheProperties redisCacheProperties;

	/**
	 * 缓存管理配置
	 *
	 * @param connectionFactory 连接工厂
	 * @return CacheManager
	 */
	@Bean
	public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		// 获取redis配置参数
		RedisCacheProperties.Redis redis = redisCacheProperties.getRedis();
		// 生成一个默认配置，对缓存进行自定义配置
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
		config = config
				// 设置缓存的默认过期时间，也是使用Duration设置
				.entryTtl(redis.getTimeToLive())
				// 计算前缀
				.computePrefixWith((redis.getKeyPrefix() != null && redis.isUseKeyPrefix()) ?
						(cacheName) -> cacheName.concat(":").concat(redis.getKeyPrefix()).concat(":")
						: CacheKeyPrefix.simple())
				// key序列化
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringSerializer()))
				// 值序列化
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonSerializer()));
		if (!redis.isCacheNullValues()) {
			config = config.disableCachingNullValues();
		}
		// 创建redisCacheManagerBuilder
		RedisCacheManager.RedisCacheManagerBuilder redisCacheManagerBuilder = RedisCacheManager
				.builder(connectionFactory)
				.cacheDefaults(config)
				.transactionAware();
		Map<String, Duration> cacheNamesMap = redisCacheProperties.getCacheNamesMap();
		if (cacheNamesMap != null && cacheNamesMap.size() > 0) {
			// 设置一个初始化的缓存空间set集合
			Set<String> cacheNames = new HashSet<>();
			// 对每个缓存空间应用不同的配置
			Map<String, RedisCacheConfiguration> configMap = new HashMap<>(cacheNamesMap.size());
			for (Map.Entry<String, Duration> entry : cacheNamesMap.entrySet()) {
				cacheNames.add(entry.getKey());
				configMap.put(entry.getKey(), config.entryTtl(entry.getValue()));
			}
			redisCacheManagerBuilder.initialCacheNames(cacheNames).withInitialCacheConfigurations(configMap);
		}
		// 使用自定义的缓存配置初始化一个cacheManager
		return redisCacheManagerBuilder.build();
	}

	/**
	 * redisTemplate
	 *
	 * @return RedisTemplate
	 */
	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connectionFactory);
		// 设置value的序列化规则和 key的序列化规则
		redisTemplate.setKeySerializer(stringSerializer());
		redisTemplate.setValueSerializer(jackson2JsonSerializer());
		redisTemplate.setHashKeySerializer(jackson2JsonSerializer());
		redisTemplate.setHashValueSerializer(jackson2JsonSerializer());
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}


	private static StringRedisSerializer stringSerializer() {
		return new StringRedisSerializer();
	}

	private static Jackson2JsonRedisSerializer<?> jackson2JsonSerializer() {
		// 使用Jackson2JsonRedisSerialize 替换默认序列化
		Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
		// 解决查询缓存转换异常的问题
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
		// 返回
		return jackson2JsonRedisSerializer;
	}

}
