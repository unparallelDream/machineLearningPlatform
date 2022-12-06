package com.platform.machinelearningplatform.service.inter;

import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.entity.StudentMessage;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.service.inter
 * @Author: EnMing Zhang
 * @CreateTime: 2022-11-14  22:06
 * @Description: TODO
 * @Version: 1.0
 */
public interface LogoutService {
    Result<String> logoutStudent(StudentMessage studentMessage);
}
