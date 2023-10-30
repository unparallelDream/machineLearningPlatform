package com.platform.machinelearningplatform.service.impl;

import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.entity.StudentMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class LoginServiceImplTest {

    @Autowired
    private LoginServiceImpl loginService;

    @Test
    void loginStudent() {
        Result<String> stringResult = loginService.loginStudent(new StudentMessage(1,"lll","lll","lll","lll","lll","lll","lll","lll","lll"));
    }

    @Test
    void loginStudentEmail() {
        Result<String> stringResult = loginService.loginStudentEmail("lll","1");
    }

    @Test
    void sendMsg() {
        loginService.sendMsg("llll","llll","llll");
    }

    @Test
    void updateStudent() {
        Result<String> stringResult = loginService.updateStudent(new StudentMessage(1,"lll","lll","lll","lll","lll","lll","lll","lll","lll"));
    }

    @Test
    void deleteStudent() {
        Result<String> stringResult = loginService.deleteStudent("lll");
    }
}