package com.platform.machinelearningplatform.utils;

import com.platform.machinelearningplatform.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.utils
 * @Author: EnMing Zhang
 * @CreateTime: 2023-01-20  12:46
 * @Description: TODO
 * @Version: 1.0
 */
@Component
public class MapperUtils {
    public static MessageMapper getMapper() {
        return mapper;
    }
    private static MessageMapper mapper;
    @Autowired
    public void setMessageMapper(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
        mapper = messageMapper;
    }
    private MessageMapper messageMapper;
}
