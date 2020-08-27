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
package com.fshop.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fshop.order.entity.WarehouseStock;
import org.apache.ibatis.annotations.Param;

/**
 * Description: 仓库商品库存表 Mapper 接口
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
public interface WarehouseStockMapper extends BaseMapper<WarehouseStock> {

    /**
     * 扣减库存数量
     *
     * @param productId
     * @param productNum
     */
    void deductStockNum(@Param("productId") long productId, @Param("productNum") int productNum);

    /**
     * 根据商品ID查询库存数量
     *
     * @param productId
     * @return
     */
    int selectStockNumByProductId(long productId);

}
