package com.platform.machinelearningplatform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.machinelearningplatform.entity.TrainResult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.mapper
 * @Author: EnMing Zhang
 * @CreateTime: 2023-01-15  15:42
 * @Description: TODO
 * @Version: 1.0
 */
@Mapper
public interface TrainResultMapper extends BaseMapper<TrainResult> {
}
