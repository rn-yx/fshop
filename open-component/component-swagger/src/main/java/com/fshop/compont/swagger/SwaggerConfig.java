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
package com.fshop.compont.swagger;

import com.google.common.collect.Lists;
import com.google.common.net.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * Description：Swagger2 配置类
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
@Configuration
@EnableSwagger2
//@Profile({"local", "dev", "test", "prod"})
@ConditionalOnProperty(name = "swagger.enable", havingValue = "true")
public class SwaggerConfig {

	/**
	 * 扫描基础包
	 */
	@Value("${swagger.base-package}")
	private String basePackage;
	/**
	 * API标题
	 */
	@Value("${swagger.title}")
	private String title;
	/**
	 * API描述
	 */
	@Value("${swagger.description}")
	private String description;
	/**
	 * API版本
	 */
	@Value("${swagger.version}")
	private String version;
	/**
	 * API联系人姓名
	 */
	@Value("${swagger.contact.name}")
	private String contactName;
	/**
	 * API联系人网址
	 */
	@Value("${swagger.contact.url}")
	private String contactUrl;
	/**
	 * API联系人邮箱
	 */
	@Value("${swagger.contact.email}")
	private String contactEmail;

	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage(basePackage))
				.paths(PathSelectors.any())
				.build()
				.alternateTypeRules()
				.securitySchemes(securitySchemes())
				.securityContexts(securityContexts());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title(title)
				.description(description)
				.contact(new Contact(contactName, contactUrl, contactEmail))
				.version(version)
				.build();
	}

	private List<ApiKey> securitySchemes() {
		return Lists.newArrayList(new ApiKey(HttpHeaders.AUTHORIZATION, HttpHeaders.AUTHORIZATION, "header"));
	}

	private List<SecurityContext> securityContexts() {
		return Lists.newArrayList(SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex("^(?!auth).*$"))
				.build()
		);
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Lists.newArrayList(new SecurityReference(HttpHeaders.AUTHORIZATION, authorizationScopes));
	}
}
