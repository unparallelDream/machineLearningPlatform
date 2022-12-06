package com.platform.machinelearningplatform.handler;

import com.google.gson.Gson;
import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @BelongsProject: security01
 * @BelongsPackage: com.zem.security01.handler
 * @Author: EnMing Zhang
 * @CreateTime: 2022-11-07  09:08
 * @Description: TODO
 * @Version: 1.0
 */
@Component
@Slf4j
public class AuthenticationEntryPointerImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Result responseResult = Result.error("登录失败，请重新登录",HttpStatus.UNAUTHORIZED.value());
        String s = new Gson().toJson(responseResult);
        log.error(authException.getMessage());
        WebUtils.renderString(response,s);
    }
}
