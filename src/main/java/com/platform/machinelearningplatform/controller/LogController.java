package com.platform.machinelearningplatform.controller;

import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.entity.StudentMessage;
import com.platform.machinelearningplatform.service.inter.LoginService;
import com.platform.machinelearningplatform.service.inter.LogoutService;
import com.platform.machinelearningplatform.utils.ValidateCodeUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.controller
 * @Author: EnMing Zhang
 * @CreateTime: 2022-11-13  12:35
 * @Description: TODO
 * @Version: 1.0
 */
@RestController
@RequestMapping("/student")
@Api("登录")
@Slf4j
public class LogController {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private LoginService loginService;
    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }
    @Autowired
    public void setLogoutService(LogoutService logoutService) {
        this.logoutService = logoutService;
    }
    private LogoutService logoutService;

    @PostMapping("/login")
    public Result<String> login(@RequestBody StudentMessage studentMessage) {
        return loginService.loginStudent(studentMessage);
    }

    @DeleteMapping("/logout")
    public Result<String> logout(@RequestBody StudentMessage studentMessage) {
        return logoutService.logoutStudent(studentMessage);
    }

    @PostMapping("/loginEmail")
    public Result<String> loginEmail(@RequestBody Map<String,String> map) {
        return loginService.loginStudentEmail(map.get("studentEmail"), map.get("code"));
    }

    @GetMapping("/send/{email}")
    public Result<String> sendMsg(@PathVariable String email) {
        String code = ValidateCodeUtils.generateValidateCode4String(6);
        stringRedisTemplate.opsForValue().set(email, code, 5, TimeUnit.MINUTES);
        String subject = "登录验证码";
        String context = "欢迎使用机器学习平台\n您的登录验证码为: " + code + "\n有效时间为五分钟，请妥善保管。";
        loginService.sendMsg(email, subject, context);
        return Result.<String>success(null).successMsg("验证码发送成功");
    }
}
