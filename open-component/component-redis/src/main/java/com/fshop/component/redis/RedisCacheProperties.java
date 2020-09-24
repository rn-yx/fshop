/*
 * Copyright 2020 rannuo1010@gmail.com
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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Description：Redis 缓存配置参数
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
@Component
@ConfigurationProperties(prefix = "spring.core")
public class RedisCacheProperties {

	/**
	 * 缓存名称(key:缓存名称,value:缓存过期时间)
	 */
	private Map<String, Duration> cacheNamesMap = new LinkedHashMap<>();
	
	/**
	 * redis参数配置
	 */
	private final RedisCacheProperties.Redis redis = new RedisCacheProperties.Redis();
	
	public RedisCacheProperties() {
		redis.setTimeToLive(Duration.ZERO);
	}
	
	public Map<String, Duration> getCacheNamesMap() {
		return cacheNamesMap;
	}
	
	public void setCacheNamesMap(Map<String, Duration> cacheNamesMap) {
		this.cacheNamesMap = cacheNamesMap;
	}
	
	public Redis getRedis() {
		return redis;
	}
	
	public static class Redis {
		private Duration timeToLive;
		private boolean cacheNullValues = true;
		private String keyPrefix;
		private boolean useKeyPrefix = true;
		
		public Redis() {
		}
		
		public Duration getTimeToLive() {
			return this.timeToLive;
		}
		
		public void setTimeToLive(Duration timeToLive) {
			this.timeToLive = timeToLive;
		}
		
		public boolean isCacheNullValues() {
			return this.cacheNullValues;
		}
		
		public void setCacheNullValues(boolean cacheNullValues) {
			this.cacheNullValues = cacheNullValues;
		}
		
		public String getKeyPrefix() {
			return this.keyPrefix;
		}
		
		public void setKeyPrefix(String keyPrefix) {
			this.keyPrefix = keyPrefix;
		}
		
		public boolean isUseKeyPrefix() {
			return this.useKeyPrefix;
		}
		
		public void setUseKeyPrefix(boolean useKeyPrefix) {
			this.useKeyPrefix = useKeyPrefix;
		}
	}
	
}
