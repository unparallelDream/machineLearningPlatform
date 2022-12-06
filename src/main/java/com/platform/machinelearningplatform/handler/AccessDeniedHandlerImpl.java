package com.platform.machinelearningplatform.handler;

import com.google.gson.Gson;
import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @BelongsProject: security01
 * @BelongsPackage: com.zem.security01.handler
 * @Author: EnMing Zhang
 * @CreateTime: 2022-11-07  09:18
 * @Description: TODO
 * @Version: 1.0
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Result responseResult = Result.error("权限不足",HttpStatus.FORBIDDEN.value());
        String s = new Gson().toJson(responseResult);
        WebUtils.renderString(response,s);
    }
}
