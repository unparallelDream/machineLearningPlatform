package com.platform.machinelearningplatform.filter;

import com.platform.machinelearningplatform.handler.AccessDeniedHandlerImpl;
import com.platform.machinelearningplatform.handler.AuthenticationEntryPointerImpl;
import com.platform.machinelearningplatform.service.LoginMessage;
import com.platform.machinelearningplatform.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * @BelongsProject: security01
 * @BelongsPackage: com.zem.security01.filter
 * @Author: EnMing Zhang
 * @CreateTime: 2022-11-05  18:40
 * @Description: TODO
 * @Version: 1.0
 */
@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    public void setAuthenticationEntryPointer(AuthenticationEntryPointerImpl authenticationEntryPointer) {
        this.authenticationEntryPointer = authenticationEntryPointer;
    }
    @Autowired
    public void setAccessDeniedHandler(AccessDeniedHandlerImpl accessDeniedHandler) {
        this.accessDeniedHandler = accessDeniedHandler;
    }
    @Resource
    private RedisTemplate redisTemplate;
    private AuthenticationEntryPointerImpl authenticationEntryPointer;
    private AccessDeniedHandlerImpl accessDeniedHandler;
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        try {
            //获取token
            String token = request.getHeader("Authorization");
            if (!StringUtils.hasText(token)){
                log.error("没有token");
                filterChain.doFilter(request,response);
                return;
            }
            //解析token
            String userId;
            try {
                Claims claims = JwtUtil.parseJWT(token);
                userId = "login:"+claims.getSubject();
            } catch (Exception e) {
                log.error("解析token错误");
                filterChain.doFilter(request,response);
                return;
            }
//            验证并获取用户信息
            if (!redisTemplate.hasKey(userId)){
                log.error("用户信息错误");
                filterChain.doFilter(request,response);
                return;
            }
            LoginMessage loginMessage = (LoginMessage) redisTemplate.opsForValue().get(userId);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginMessage,null,loginMessage.getAuthorities());
            //存入security上下文
            SecurityContextHolder.getContext().setAuthentication(authentication);
            doFilter(request,response,filterChain);
        } catch (AuthenticationException e) {
            authenticationEntryPointer.commence(request,response,e);
        } catch (AccessDeniedException e) {
            accessDeniedHandler.handle(request,response,e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
