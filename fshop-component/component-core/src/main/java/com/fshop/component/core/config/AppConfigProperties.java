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
package com.fshop.component.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * Description：系统配置
 *
 * @author 然诺
 * @date 2020/2/22
 */
@Getter
@Setter
@Validated
@Component
@ConfigurationProperties(prefix = "spring.application")
public class AppConfigProperties {
	/**
	 * 是否向接口调用者输出异常信息
	 * {
	 *       "name": "spring.application.output-exception",
	 *       "type": "java.lang.Boolean",
	 *       "defaultValue": false,
	 *       "description": "是否向接口调用者输出异常信息."
	 *     },
	 */
	private boolean outputException = false;
	/**
	 * 是否输出异常堆栈
	 */
	private boolean outputExceptionStackTrace = true;
	/**
	 * 编码
	 */
	private String encoding = "UTF-8";
	/**
	 * 时区
	 */
	private String timeZone = "Asia/Shanghai";
	/**
	 * 日期格式
	 */
	private String dateFormat = "yyyy-MM-dd'T'HH:mm:ss:SSSZ";
	/**
	 * 线程池属性
	 */
	private AppConfigProperties.ThreadPoolProperties threadPool = new AppConfigProperties.ThreadPoolProperties();
	/**
	 * 如有大文件通过restTemplate传输,请设置为false
	 */
	private Boolean bufferRequestBody = true;

	/**
	 * 是否拦截拦截请求方法
	 */
	private boolean interceptRequestMethod = false;

	/**
	 * 忽略拦截拦截请求方法的路径
	 */
	private String[] interceptRequestMethodIgnoreRules;

	/**
	 * Description：线程池配置
	 *
	 * @author rannuo1010@gmail.com
	 * @date 2020/2/22
	 */
	@Getter
	@Setter
	static class ThreadPoolProperties {
		/**
		 * 线程优先级
		 */
		private Integer threadPriority = Thread.NORM_PRIORITY;

		/**
		 * 线程名称前缀
		 */
		private String threadNamePrefix = "defaultThreadPool";

		/**
		 * 核心线程数
		 */
		private Integer corePoolSize = 3;

		/**
		 * 最大线程数(默认:CPU数量+1)
		 */
		private Integer maxPoolSize = Runtime.getRuntime().availableProcessors() + 1;

		/**
		 * 配置队列大小
		 */
		private Integer queueCapacity = 99999;

		/**
		 * 线程空闲后的最大存活时间
		 */
		private Integer keepAliveSeconds = 60;

		/**
		 * 线程池中任务的等待时间（超时强制删除）
		 */
		private Integer awaitTerminationSeconds = 60;
	}

}
