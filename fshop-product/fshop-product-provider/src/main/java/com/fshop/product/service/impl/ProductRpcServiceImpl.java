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
package com.fshop.product.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.fshop.component.core.utils.BeanUtils;
import com.fshop.product.entity.Product;
import com.fshop.product.mapper.ProductMapper;
import com.fshop.product.rpc.ProductRpcService;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description: 商品 RPC 服务接口实现类
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
@Component
@Service(version = "1.0.0", interfaceClass = ProductRpcService.class)
public class ProductRpcServiceImpl implements ProductRpcService {

    @Resource
    private ProductMapper productMapper;

    @Override
    public List<ProductDTO> getOnShelfProducts(int productStatus) {
        List<Product> productList = productMapper.selectOnShelfProducts(productStatus);
        return CollectionUtils.isEmpty(productList) ? Lists.newArrayList() : BeanUtils.copyProperties(productList, ProductDTO.class);
    }
}
