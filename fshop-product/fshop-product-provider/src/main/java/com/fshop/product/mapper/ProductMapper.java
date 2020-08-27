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
package com.fshop.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fshop.product.entity.Product;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 商品Mapper 接口
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 根据ID查询商品表
     *
     * @param id 商品表ID
     * @return 商品表对象
     */
    Product selectProductById(Serializable id);

    /**
     * 根据状态查询查询商品列表
     *
     * @return
     */
    List<Product> selectOnShelfProducts(int productStatus);
}
