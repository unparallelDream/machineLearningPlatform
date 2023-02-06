package com.platform.machinelearningplatform.handler;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.handler
 * @Author: EnMing Zhang
 * @CreateTime: 2023-01-19  21:23
 * @Description: TODO
 * @Version: 1.0
 */

import com.platform.machinelearningplatform.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 自定义异常处理
 * @author: DT
 * @date: 2021/4/19 21:51
 * @version: v1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义的业务异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public Result<String> bizExceptionHandler(HttpServletRequest req, Exception e) throws Exception {
        if (e instanceof BadCredentialsException||e instanceof AccessDeniedException){
            throw e;
        }
        e.printStackTrace();
        return Result.error("服务器发生错误:"+e.getClass());
    }

}

