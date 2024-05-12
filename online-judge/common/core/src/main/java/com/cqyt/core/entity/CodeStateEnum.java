package com.cqyt.core.entity;

public enum CodeStateEnum {

    /**
     * 成功状态码 2xx
     */
    SUCCESS(200, "请求成功", true),

    /**
     * 客户端错误状态码 4xx
     */
    AUTH_UNAUTHORIZED(401, "权限模块：用户未授权", false),
    AUTH_FORBIDDEN(402, "权限模块：令牌已过期或验证不正确!", false),
    AUTH_TOKEN_NOT_NULL(403, "权限模块：令牌不能为空", false),
    AUTH_LOGIN_EXPIRE(404, "权限模块：登录状态已过期，请重新登录", false),
    AUTH_TOKEN_FAIL(405, "权限模块：令牌验证失败，请尝试重新登录", false),
    AUTH_FLUSH_FAIL(406,"权限模块: 权限刷新失败，请重新登录", false),
    AUTH_NO_PERMISSION(407,"权限模块: 没有接口的访问权限!", false),
    AUTH_REQUEST_LIMIT(408,"接口限流: 收到请求过多!请稍后再试!", false),

    /**
     * 服务器错误状态码 5xx
     */
    ERROR(500, "系统内部错误", false),

    /**
     * redis相关状态码 6xx
     */

    /**
     * 微信解码异常
     * */
    ERROR_NOT_INFO(60000, "错误，没有解码数据", false),
    ERROR_DECODE(60001, "解码失败: 参数已失效!", false);


    public final Integer code;
    public final String message;
    public final Boolean success;

    CodeStateEnum(Integer code, String message, Boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }
}
