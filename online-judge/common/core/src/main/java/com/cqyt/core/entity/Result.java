package com.cqyt.core.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Result {

    private Integer code;

    private Boolean success;

    private String message;

    private Map<Object, Object> data = new HashMap<>();

    public static Result ok(){
        Result result = new Result();
        result.code = 200;
        result.success = true;
        return result;
    }

    public static Result error(){
        Result result = new Result();
        result.code = 500;
        result.success = false;
        return result;
    }

    public Result code(Integer code){
        this.code = code;
        return this;
    }

    public Result success(Boolean success){
        this.success = success;
        return this;
    }

    public Result message(String message){
        this.message = message;
        return this;
    }

    public Result data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public Result data(Object object){
        this.data.put("default", object);
        return this;
    }

    public Result enumData(CodeStateEnum codeStateEnum){
        this.code = codeStateEnum.code;
        this.success = codeStateEnum.success;
        this.message = codeStateEnum.message;
        return this;
    }
}
