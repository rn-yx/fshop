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
package com.fshop.component.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.ObjectUtils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description: Bean 工具类
 *
 * @author 然诺
 * @date 2020/2/22
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

	private static final Logger log = LoggerFactory.getLogger(BeanUtils.class);

	/**
	 * 对象属性拷贝
	 *
	 * @param source 拷贝对象
	 * @param clazz  目标类类型
	 * @param <T>    目标类泛型
	 * @return 目标类实例
	 */
	public static <T> T copyProperties(final Object source, final Class<T> clazz) {
		T target = null;
		try {
			target = clazz.newInstance();
			copyProperties(source, target);
		} catch (InstantiationException | IllegalAccessException e) {
			dealException(e);
		}
		return target;
	}

	/**
	 * 批量对象属性拷贝
	 *
	 * @param sourceList 拷贝对象集合
	 * @param clazz      目标类类型
	 * @param <E>        拷贝对象泛型
	 * @param <V>        目标类泛型
	 * @return 目标类实例集合
	 */
	public static <E, V> List<V> copyProperties(final List<E> sourceList, final Class<V> clazz) {
		return sourceList.stream().map(pojo -> copyProperties(pojo, clazz)).collect(Collectors.toList());
	}

	/**
	 * 对象非空属性拷贝
	 *
	 * @param source 拷贝对象
	 * @param clazz  目标类类型
	 * @param <T>    泛型
	 * @return 目标类的实例
	 */
	public static <T> T copyNotNullProperties(final Object source, final Class<T> clazz) {
		T target = null;
		try {
			target = clazz.newInstance();
			copyProperties(source, target, setIgnoreNull(source));
		} catch (InstantiationException | IllegalAccessException e) {
			dealException(e);
		}
		return target;
	}

	/**
	 * 忽略空属性
	 *
	 * @param source 拷贝对象
	 * @return 成员变量为空的名称字符数组
	 */
	private static String[] setIgnoreNull(Object source) {
		final BeanWrapper wrapper = new BeanWrapperImpl(source);
		PropertyDescriptor[] pds = wrapper.getPropertyDescriptors();
		Set<String> emptyNames = new HashSet<>();
		for (PropertyDescriptor pd : pds) {
			Object srcValue = wrapper.getPropertyValue(pd.getName());
			if (ObjectUtils.isEmpty(srcValue)) {
				emptyNames.add(pd.getName());
			}
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	/**
	 * 异常处理
	 *
	 * @param e 异常对象
	 */
	private static void dealException(Throwable e) {
		String msg = "Object property copy error";
		log.error(msg, e);
		throw new RuntimeException(msg);
	}

}