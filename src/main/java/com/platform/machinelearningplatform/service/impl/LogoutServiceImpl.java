package com.platform.machinelearningplatform.service.impl;

import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.entity.StudentMessage;
import com.platform.machinelearningplatform.service.inter.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.service.impl
 * @Author: EnMing Zhang
 * @CreateTime: 2022-11-14  22:07
 * @Description: TODO
 * @Version: 1.0
 */
@Service
public class LogoutServiceImpl implements LogoutService {
    @Resource
   private RedisTemplate redisTemplate;
    @Override
    @Transactional
    public Result<String> logoutStudent(StudentMessage studentMessage) {
        if (studentMessage==null&&!redisTemplate.hasKey("login:"+studentMessage.getAccount())){
            return Result.error("退出登录失败");
        }
        redisTemplate.delete("login:"+studentMessage.getAccount());
        return Result.<String>success(null).successMsg("退出登录成功");
    }
}
