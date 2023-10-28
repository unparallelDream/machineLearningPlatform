package com.platform.machinelearningplatform.config;

import com.platform.machinelearningplatform.filter.JwtAuthenticationTokenFilter;
import com.platform.machinelearningplatform.handler.AccessDeniedHandlerImpl;
import com.platform.machinelearningplatform.handler.AuthenticationEntryPointerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.config
 * @Author: EnMing Zhang
 * @CreateTime: 2022-11-13  11:18
 * @Description: TODO
 * @Version: 1.0
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    public void setJwtAuthenticationTokenFilter(JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter) {
        this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
    }

    @Autowired
    public void setAuthenticationEntryPointer(AuthenticationEntryPointerImpl authenticationEntryPointer) {
        this.authenticationEntryPointer = authenticationEntryPointer;
    }

    @Autowired
    public void setAccessDeniedHandler(AccessDeniedHandlerImpl accessDeniedHandler) {
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Resource
    public void setAuthenticationConfiguration(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }

    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    private AuthenticationEntryPointerImpl authenticationEntryPointer;

    private AccessDeniedHandlerImpl accessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(@Autowired HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //session的创建规则
        http
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/student/login").permitAll()
                .antMatchers("/student/register").anonymous()
                .antMatchers("/student/loginEmail").permitAll()
                .antMatchers("/student/send/**").permitAll()
                .antMatchers("/student/updateAvatar").permitAll()
                .antMatchers("/static/**", "/index").hasAuthority("ROLE_root")
                .antMatchers("/manager/getTrainingData").authenticated()
                .antMatchers("/manager/getTrainResult").authenticated()
                .antMatchers("/manager/getParamsById").authenticated()
                .antMatchers("/manager/**").hasAuthority("ROLE_root")
//                .permitAll()
                .antMatchers("/markdown/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/doc.html").permitAll()
                .antMatchers("/webSocket/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/swagger-resources").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
//                .antMatchers("/**").permitAll()
                .and()
                // 除上面外的所有请求全部需要鉴权认证
                .authorizeRequests()
                .anyRequest().authenticated()
//                .and()
//                .httpBasic()
        ;
        //认证过滤器
        http
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //认证权限处理
        http
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPointer).accessDeniedHandler(accessDeniedHandler);
        //认证cors
        http
                .cors();
        return http.build();
    }

    @Resource
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
