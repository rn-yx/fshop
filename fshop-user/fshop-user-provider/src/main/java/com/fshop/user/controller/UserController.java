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
package com.fshop.user.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.fshop.user.service.IUserService;
import com.fshop.user.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.fshop.component.core.result.GlobalRet;
import com.fshop.user.su.model.vo.UserVO;

import java.io.Serializable;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.RestController;
import com.fshop.component.core.mvc.BaseController;

/**
 * Description: 用户表控制器
 *
 * @author rannuo1010@gmail.com
 * @date 2020-08-26
 */
@Api(tags = {"用户表服务接口"})
@RestController
@AllArgsConstructor
@RequestMapping("/su/user")
public class UserController extends BaseController {

    private final IUserService userService;


    @ApiOperation(value = "根据ID查询用户表")
    @GetMapping("/{id}")
    public GlobalRet<UserVO> getUserById(@PathVariable(value = "id") Serializable id) {
        return success();
    }

    @ApiOperation(value = "新增用户表")
    @PostMapping
    public GlobalRet add(@Valid @RequestBody User user) {
        return success();
    }

    @ApiOperation(value = "根据ID删除用户表")
    @DeleteMapping("/{id}")
    public GlobalRet delete(@PathVariable(value = "id") Serializable id) {
        return success();
    }

    @ApiOperation(value = "更新用户表")
    @PutMapping
    public GlobalRet update(@Valid @RequestBody User user){
        return success();
    }

}
