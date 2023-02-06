package com.platform.machinelearningplatform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.machinelearningplatform.entity.Message;
import com.platform.machinelearningplatform.mapper.MessageMapper;
import com.platform.machinelearningplatform.service.inter.MessageService;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.service.impl
 * @Author: EnMing Zhang
 * @CreateTime: 2023-01-20  11:44
 * @Description: TODO
 * @Version: 1.0
 */
@Component
public class MessageImp  extends ServiceImpl<MessageMapper, Message> implements MessageService {
    public MessageMapper getBaseMapper(){
        return this.baseMapper;
    }
}
