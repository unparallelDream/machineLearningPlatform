package com.platform.machinelearningplatform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.machinelearningplatform.entity.Message;
import org.apache.ibatis.annotations.Mapper;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.mapper
 * @Author: EnMing Zhang
 * @CreateTime: 2023-01-20  11:11
 * @Description: TODO
 * @Version: 1.0
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {
}
