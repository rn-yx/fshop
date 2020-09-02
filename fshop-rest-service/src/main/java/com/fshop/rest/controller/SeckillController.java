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
package com.fshop.rest.controller;

import com.fshop.component.core.exception.GlobalExCode;
import com.fshop.component.core.mvc.BaseController;
import com.fshop.component.core.mvc.ContextHandler;
import com.fshop.component.core.result.GlobalRet;
import com.fshop.order.model.bo.SeckillRetBO;
import com.fshop.rest.model.query.SeckillQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.fshop.rest.service.SeckillService;

import javax.validation.Valid;

/**
 * Description：秒杀控制器
 *
 * @author rannuo1010@gmail.com
 * @date 2020/2/22
 */
@Api(tags = "秒杀服务接口")
@RestController
@RequestMapping("/seckill")
public class SeckillController extends BaseController {

    @Autowired
    private SeckillService fshopService;

    @ApiOperation(value = "获取秒杀验证码")
    @GetMapping("/code")
    public GlobalRet getVerifyCode() {
        return success(fshopService.getVerifyCode(ContextHandler.getCurrentUserId()));
    }

    @ApiOperation(value = "获取真实秒杀地址")
    @ApiImplicitParams({
		    @ApiImplicitParam(name = "verifyCode", value = "验证码", required = true),
		    @ApiImplicitParam(name = "productId", value = "商品ID", required = true, example = "1")
    })
    @GetMapping("/path")
    public GlobalRet getSeckillPath(@RequestParam String verifyCode, @RequestParam long productId) {
        long currentUserId = ContextHandler.getCurrentUserId();
        if (!fshopService.checkVerifyCode(currentUserId, verifyCode)) {
            return failure(GlobalExCode.VERIFY_CODE_ERROR);
        }
        return success(fshopService.getSeckillPath(currentUserId, productId));
    }

    @ApiOperation(value = "秒杀下单")
    @PostMapping("/{pathId}")
    public GlobalRet<SeckillRetBO> seckill(@PathVariable String pathId, @Valid @RequestBody SeckillQuery fshopQuery) {
//        // 验证path
//        boolean isPass = fshopService.verifyPath(ContextHandler.getCurrentUserId(), fshopQuery.getProductId(), pathId);
//        if(!isPass){
//            return failure(GlobalExCode.ILLEGAL_OPERATE);
//        }
        // 执行秒杀业务
        return success(fshopService.seckill(fshopQuery));
    }

    @ApiOperation(value = "查询秒杀下单结果")
    @ApiImplicitParam(name = "productId", value = "商品ID", required = true, example = "1")
    @GetMapping("/result")
    public GlobalRet<SeckillRetBO> getSeckillRet(@RequestParam long productId) {
        return success(fshopService.getSeckillRet(ContextHandler.getCurrentUserId(), productId));
    }

}
