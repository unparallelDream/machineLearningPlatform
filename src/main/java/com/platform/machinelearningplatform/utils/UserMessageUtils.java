package com.platform.machinelearningplatform.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.platform.machinelearningplatform.entity.StudentMessage;
import com.platform.machinelearningplatform.mapper.StudentMessageMapper;
import com.platform.machinelearningplatform.service.LoginMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;

import java.util.Optional;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.utils
 * @Author: EnMing Zhang
 * @CreateTime: 2023-01-27  16:33
 * @Description: TODO
 * @Version: 1.0
 */
@Slf4j
public class UserMessageUtils {

    public static String getAccountBySecurityContext(SecurityContext securityContext){
        LoginMessage loginMessage = (LoginMessage) securityContext.getAuthentication().getPrincipal();
        String account = loginMessage.getUsername();
        return account;
    }
    public static Long getIdBySecurityContext(SecurityContext securityContext, StudentMessageMapper studentMessageMapper){

        return Optional.of(securityContext)
                .map(UserMessageUtils::getAccountBySecurityContext)
                .map(e->studentMessageMapper.selectOne(new LambdaQueryWrapper<StudentMessage>().eq(StudentMessage::getAccount,e)))
                .map(e->e.getId()).orElse(null);
    }
    public static Long getIdByAccount(String account,StudentMessageMapper studentMessageMapper){
        return Optional.of(account)
                .map(e->studentMessageMapper.selectOne(new LambdaQueryWrapper<StudentMessage>().eq(StudentMessage::getAccount,e)))
                .map(e->e.getId()).orElse(null);
    }
}
