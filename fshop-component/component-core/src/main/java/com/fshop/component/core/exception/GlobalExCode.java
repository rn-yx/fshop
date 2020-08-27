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
package com.fshop.component.core.exception;

/**
 * Description: 全局编码枚举类
 * ---------------------------------------------------------
 * 编码定义格式说明：
 * 1.基础网络异常保持通用,编号1000以下；
 * 2.通用异常（4位）：前二位（模块）+ 后两位（序号）
 * 3.业务异常（5位）：前三位（模块）+ 后两位（序号）
 * ----------------------------------------------------------
 *
 * @author 然诺
 * @date 2020/2/22
 */
public enum GlobalExCode implements IExceptionCode {
    // 系统相关
    SYSTEM_BUSY(-1, "系统繁忙，请稍候再试"),
    SYSTEM_TIMEOUT(-2, "系统超时，请稍候再试"),
    REQUEST_REPEAT(-3, "请勿重复提交"),
    TOKEN_EXPIRED(-4, "token已过期"),

    // 网络交互相关
    BAD_REQUEST(400, "请求的参数个数或格式不符合要求"),
    INVALID_ARGUMENT(400, "请求的参数不正确"),
    UNAUTHORIZED(401, "没有权限"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "请求的地址错误"),
    METHOD_NOT_ALLOWED(405, "不允许的请求方法"),
    NOT_ACCEPTABLE(406, "不接受的请求"),
    CONFLICT(409, "资源冲突"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持的Media Type"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),
    REQUEST_TIMEOUT(504, "请求服务超时"),
    ENCODING_ERROR(600, "编码错误"),
    METHOD_ARGUMENT_TYPE_MISMATCH(601, "请求参数类型错误"),
    METHOD_ARGUMENT_COUNT_MISMATCH(602, "请求参数缺失错误"),
    METHOD_ARGUMENT_BIND_EX(603, "请求参数有误，绑定异常"),
    METHOD_REQUEST_PART_MISSING(604, "所需的请求参数缺失数据"),
    METHOD_HTTP_MESSAGE_NOT_READABLE_EX(605, "请求参数有误，Http消息不可读"),

    // 数据库相关
    DB_ADD_FAIL(1000, "保存数据失败"),
    DB_REMOVE_FAIL(1001, "软删除失败"),
    DB_DELETE_FAIL(1002, "删除失败"),
    DB_UPDATE_FAIL(1003, "更新失败"),
    DB_QUERY_FAIL(1004, "查询失败"),
    DB_DUPLICATE_KEY_EXCEPTION(1005, "DB重复键（索引）异常"),
    DB_DATA_INTEGRITY_VIOLATION_EX(1006, "数据完整性违规异常（可能是字段缺少或错误）"),

    // 操作相关
    ACTION_FAILURE(1100, "操作失败"),
    CALLING_REMOTE_SERVICE_FAIL(1101, "远程服务调用失败"),
    ACCESS_SOURCE_NOT_EXIST(1102, "请求访问资源不存在"),
    ILLEGAL_OPERATE(1103, "非法操作"),

    VERIFY_CODE_ERROR(1200, "验证码不正确"),
    VERIFY_CODE_EXPIRED(1201, "验证码已失效"),


    // 用户相关
    USER_IS_EXIST(10000, "用户已存在"),
    USER_USERNAME_OR_PASSWORD_ERROR(10001, "用户名或密码错误"),
    USER_IS_FROZEN(10002, "用户已冻结"),
    USER_IS_UNAVAILABLE(10003, "用户不可用"),
    USER_PASSWORD_ERROR(10004, "用户密码不正确"),
    USER_NOT_REGISTERED(10005, "用户未注册"),
    USER_EMAIL_IS_INCORRECT(10005, "用户邮箱不正确"),
    USER_PASSWORD_RESET_LINK_IS_INVALID(10006, "重置密码链接无效"),
    USER_PASSWORD_RESET_LINK_IS_ERROR(10007, "链接错误,无法找到匹配用户,请重新申请找回密码"),
    USER_PASSWORD_RESET_LINK_IS_EXPIRED(10008, "链接已经过期,请重新申请找回密码"),
    USER_PASSWORD_RESET_LINK_IS_INCORRECT(10009, "链接不正确,是否已经过期了?重新申请吧"),
    USER_IS_NOT_EXIST(10010, "用户不存在"),
    USER_ILLEGAL_LOGIN_OPERATION(10013, "非法的登录操作"),
    TOKEN_AUTHENTICATION_FAIL(10100, "token认证失败！"),

    // 角色相关
    ROLE_IS_UNAVAILABLE(10200, "角色不可用"),
    ROLE_NOT_EXIST(10201, "角色不存在"),
    ROLE_ASSOCIATED_USERS_CANNOT_DELETE(10202, "角色有关联用户，不能删除"),
    ROLE_ASSOCIATED_PERMISSIONS_CANNOT_DELETE(10203, "角色有关联权限，不能删除");

    private int status;
    private String message;

    GlobalExCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static GlobalExCode fromStatus(int httpStatus) {
        for (GlobalExCode errorCode : values()) {
            if (httpStatus == errorCode.getStatus()) {
                return errorCode;
            }
        }
        return INTERNAL_SERVER_ERROR;
    }

    public static GlobalExCode fromMessage(String message) {
        if (message != null) {
            for (GlobalExCode errorCode : values()) {
                if (message.equals(errorCode.getMessage())) {
                    return errorCode;
                }
            }
        }
        return INTERNAL_SERVER_ERROR;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getCode() {
        return name();
    }

}
