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
package com.fshop.product.mapper;

import com.fshop.product.entity.Picture;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.io.Serializable;

/**
 * Description: 商品图片表 Mapper 接口
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
public interface PictureMapper extends BaseMapper<Picture> {

    /**
     * 根据ID查询商品图片表
     *
     * @param id 商品图片表ID
     * @return 商品图片表对象
     */
    Picture selectPictureById(Serializable id);

}
