package com.platform.machinelearningplatform.service.inter;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.entity.StudentMessage;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.service.inter
 * @Author: EnMing Zhang
 * @CreateTime: 2022-11-13  17:32
 * @Description: TODO
 * @Version: 1.0
 */
public interface RegisterService extends IService<StudentMessage> {
    Result<String> register(StudentMessage message);
}
