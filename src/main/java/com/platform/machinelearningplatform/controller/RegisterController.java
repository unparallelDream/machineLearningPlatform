package com.platform.machinelearningplatform.controller;

import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.entity.StudentMessage;
import com.platform.machinelearningplatform.service.inter.RegisterService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.controller
 * @Author: EnMing Zhang
 * @CreateTime: 2022-11-13  17:28
 * @Description: TODO
 * @Version: 1.0
 */
@RestController
@RequestMapping("/student")
@Api("用户注册")
public class RegisterController {
    @Autowired
    public void setRegisterService(RegisterService registerService) {
        this.registerService = registerService;
    }

    private RegisterService registerService;

    @PostMapping("/register")
    public Result<String> register(@RequestBody StudentMessage studentMessage) {
        return registerService.register(studentMessage);
    }
}
