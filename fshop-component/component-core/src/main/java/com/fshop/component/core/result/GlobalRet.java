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
package com.fshop.component.core.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fshop.component.core.constant.GlobalConstants;
import com.fshop.component.core.exception.IExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * Description：通用响应对象
 *
 * @author 然诺
 * @date 2020/2/22
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "通用响应对象")
public class GlobalRet<T> implements Serializable {

    @ApiModelProperty(value = "状态码", required = true)
    private Integer code = GlobalConstants.SUCCESS;

    @ApiModelProperty(value = "描述信息", required = true)
    private String msg = GlobalConstants.SUCCESS_MSG;

    @ApiModelProperty(value = "结果集(泛型)")
    private T data;

    @ApiModelProperty(value = "异常信息")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ExRet error = null;

    public GlobalRet() {
    }

    /**
     * 构造函数
     *
     * @param data 结果集(泛型)
     */
    public GlobalRet(T data) {
        this.data = data;
    }


    public GlobalRet(Throwable e) {
        this.msg = e.getMessage();
        this.code = GlobalConstants.FAIL;
    }

    public GlobalRet(IExceptionCode code) {
        if (code != null) {
            this.code = code.getStatus();
            this.msg = code.getMessage();
        }
    }

    /**
     * 成功时候的调用
     */
    public static <T> GlobalRet<T> success() {
        return new GlobalRet<>();
    }

    /**
     * 成功时候的调用（带数据）
     */
    public static <T> GlobalRet<T> success(T data) {
        return new GlobalRet<>(data);
    }

    /**
     * 成功时候的调用（标记、带数据）
     */
    public static <T> GlobalRet<T> success(boolean flag, T data) {
        if (flag) {
            return new GlobalRet<>(data);
        } else {
            GlobalRet<T> ret = new GlobalRet<>();
            ret.setCode(GlobalConstants.FAIL);
            ret.setMsg(GlobalConstants.FAIL_MSG);
            return ret;
        }
    }

    /**
     * 失败时候的调用
     */
    public static <T> GlobalRet<T> failure() {
        return failure(GlobalConstants.FAIL, GlobalConstants.FAIL_MSG);
    }

    /**
     * 失败时候的调用
     */
    public static <T> GlobalRet<T> failure(int status, String msg) {
        GlobalRet<T> ret = new GlobalRet<>();
        ret.setCode(status);
        ret.setMsg(msg);
        return ret;
    }

    /**
     * 失败时候的调用
     */
    public static <T> GlobalRet<T> failure(IExceptionCode iExceptionEnum) {
        return new GlobalRet<>(iExceptionEnum);
    }

    /**
     * 成功
     *
     * @return true/false
     */
    public boolean ok() {
        return GlobalConstants.SUCCESS == this.code;
    }

    /**
     * 提取服务调用返回结果，若返回失败，则抛出异常
     *
     * @param s
     * @param <X>
     * @return
     * @throws X
     */
    public static  <X extends Throwable> void isFailThrow(GlobalRet ret, Supplier<? extends X> s) throws X {
        if (ret == null || !ret.ok()) {
            throw s.get();
        }
    }

    /**
     * 提取服务调用返回结果，若返回失败，则抛出异常
     *
     * @param s
     * @param <X>
     * @return
     * @throws X
     */
    public <X extends Throwable> T orElseThrow(Supplier<? extends X> s) throws X {
        if (this.ok()) {
            return this.getData();
        } else {
            throw s.get();
        }
    }

    /**
     * 提取服务调用返回结果，若返回失败，则抛出异常
     *
     * @param s
     * @param <X>
     * @return
     * @throws X
     */
    public static <T, X extends Throwable> T orElseThrow(GlobalRet<T> ret, Supplier<? extends X> s) throws X {
        if (ret != null && ret.ok()) {
            return ret.getData();
        } else {
            throw s.get();
        }
    }

}
