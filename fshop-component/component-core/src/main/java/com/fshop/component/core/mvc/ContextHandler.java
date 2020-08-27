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
package com.fshop.component.core.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description：上下文处理器
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
public class ContextHandler {
    private static final Logger log = LoggerFactory.getLogger(ContextHandler.class);

    public static final String CURRENT_USER = "C_U";
    public static final String CURRENT_USER_ID = "C_U_I";
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String REFRESH_TOKEN = "REFRESH_TOKEN";

    /**
     * InheritableThreadLocal用于子线程能够拿到父线程往ThreadLocal里设置的值
     */
    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<>();

    private ContextHandler() {
    }

    /**
     * 获取当前登录用户ID
     * 若获取用户为null，则抛出BizException（BizException异常会被异常处理模块自动捕获并返回错误响应给前端）
     *
     * @return 获取用户信息的对象
     */
    public static long getCurrentUserId() {
//        Object currentUserId = get(CURRENT_USER_ID);
//        if (currentUserId == null) {
//            throw new BizException(GlobalExCode.UNAUTHORIZED);
//        }
//        return (long) currentUserId;
        return 1L;
    }

    public static void set(String key, Object value) {
        Map<String, Object> map = get();
        map.put(key, value);
        THREAD_LOCAL.set(map);
    }

    public static Object get(String key) {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (map == null) {
            map = new ConcurrentHashMap<>(16);
            THREAD_LOCAL.set(map);
        }
        return map.get(key);
    }

    public static Map<String, Object> get() {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (map == null) {
            map = new ConcurrentHashMap<>(16);
            THREAD_LOCAL.set(map);
        }
        return map;
    }

    /**
     * 移除->释放资源
     */
    public static void remove() {
        THREAD_LOCAL.remove();
    }

    /**
     * 移除指定数据
     */
    public static void remove(String key) {
        THREAD_LOCAL.get().remove(key);
    }
}
