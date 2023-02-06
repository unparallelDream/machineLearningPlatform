package com.platform.machinelearningplatform.common;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.common
 * @Author: EnMing Zhang
 * @CreateTime: 2022-11-13  11:13
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class Result<T> {
    private Integer code; //编码：1成功，0和其它数字为失败

    private String msg; //错误信息

    private T data; //数据

    private Map<String,Object> map = new HashMap<>(); //动态数据

    public  Result<T> successMsg(String msg) {
        this.msg = msg;
        this.code = 1;
        return this;
    }

    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.msg = msg;
        result.code = 0;
        return result;
    }

    public static <T> Result<T> error(String msg, Integer code) {
        Result<T> result = new Result<>();
        result.msg = msg;
        result.code = code;
        return result;
    }

    public static <T>Result<T> success(T value) {
        Result<T> result = new Result<>();
        result.data=value;
        result.code = 1;
        return result;
    }
}
