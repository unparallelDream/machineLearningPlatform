package com.platform.machinelearningplatform.service.inter;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.entity.StudentMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.service.inter
 * @Author: EnMing Zhang
 * @CreateTime: 2022-11-13  12:39
 * @Description: TODO
 * @Version: 1.0
 */
public interface LoginService extends IService<StudentMessage> {
    Result<String> loginStudent(StudentMessage studentMessage);
    Result<String> loginStudentEmail(String studentEmail,String code);
    void sendMsg(String mail, String subject, String context);
}
