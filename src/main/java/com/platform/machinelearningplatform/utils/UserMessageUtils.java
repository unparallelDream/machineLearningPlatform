package com.platform.machinelearningplatform.utils;

import com.platform.machinelearningplatform.service.LoginMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;

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
        log.error(securityContext.getAuthentication().getPrincipal().toString());
        LoginMessage loginMessage = (LoginMessage) securityContext.getAuthentication().getPrincipal();
        String account = loginMessage.getUsername();
        return account;
    }
}
