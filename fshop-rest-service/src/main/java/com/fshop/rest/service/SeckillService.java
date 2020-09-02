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
package com.fshop.rest.service;

import com.fshop.order.model.bo.SeckillRetBO;
import com.fshop.rest.model.query.SeckillQuery;

/**
 * Description：订单服务接口
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
public interface SeckillService {

    /**
     * 秒杀
     *
     * @param fshopQuery
     * @return
     */
    SeckillRetBO seckill(SeckillQuery fshopQuery);

    /**
     * 检查订单的状态
     *
     * @param userId
     * @param productId
     * @return
     */
    SeckillRetBO getSeckillRet(long userId, long productId);

    /**
     * 获取秒杀验证码
     *
     * @param userId
     * @return
     */
    String getVerifyCode(long userId);

    /**
     * 验证秒杀验证码
     *
     * @param userId
     * @param verifyCode
     * @return
     */
    boolean checkVerifyCode(long userId, String verifyCode);

    /**
     * 获取秒杀path
     *
     * @param userId
     * @param productId
     * @return
     */
    String getSeckillPath(long userId, long productId);

    /**
     * 验证秒杀path
     *
     * @param userId
     * @param productId
     * @param pathId
     * @return
     */
    boolean verifyPath(long userId, long productId, String pathId);
}
