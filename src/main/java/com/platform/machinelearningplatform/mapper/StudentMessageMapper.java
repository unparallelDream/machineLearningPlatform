package com.platform.machinelearningplatform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.machinelearningplatform.entity.StudentMessage;
import org.apache.ibatis.annotations.Mapper;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.mapper
 * @Author: EnMing Zhang
 * @CreateTime: 2022-11-13  12:29
 * @Description: TODO
 * @Version: 1.0
 */
@Mapper
public interface StudentMessageMapper extends BaseMapper<StudentMessage> {
}
