package com.platform.machinelearningplatform.service.inter;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.entity.LearningTime;
import org.springframework.security.core.context.SecurityContext;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.service.inter
 * @Author: EnMing Zhang
 * @CreateTime: 2023-01-27  16:12
 * @Description: TODO
 * @Version: 1.0
 */
public interface LearningTimeUpdateService extends IService<LearningTime> {
    Result<String> updateTime(SecurityContext securityContext);
}
