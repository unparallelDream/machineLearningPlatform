package com.platform.machinelearningplatform.config;

import com.platform.machinelearningplatform.filter.JwtAuthenticationTokenFilter;
import com.platform.machinelearningplatform.handler.AccessDeniedHandlerImpl;
import com.platform.machinelearningplatform.handler.AuthenticationEntryPointerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //??????csrf
                .csrf().disable()
                //?????????Session??????SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //session???????????????
        http
                .authorizeRequests()
                // ?????????????????? ??????????????????
                .antMatchers("/student/login").permitAll()
                .antMatchers("/student/register").anonymous()
                .antMatchers("/student/loginEmail").permitAll()
                .antMatchers("/student/send/**").permitAll()
                .antMatchers("/student/uploadAvatar").anonymous()//????????????
                .antMatchers("/static/**","/index").permitAll()
//                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/doc.html").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/swagger-resources").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
//                .antMatchers().permitAll()//????????????
                .and()
                // ???????????????????????????????????????????????????
                .authorizeRequests()
                .anyRequest().authenticated()
//                .and()
//                .httpBasic()
        ;
        //???????????????
        http
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //??????????????????
        http
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPointer).accessDeniedHandler(accessDeniedHandler);
        //??????cors
        http
                .cors();
        return http.build();
    }

    @Resource
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
        return authenticationManager;
    }
}
