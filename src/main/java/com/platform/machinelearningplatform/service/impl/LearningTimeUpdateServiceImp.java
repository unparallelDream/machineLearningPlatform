package com.platform.machinelearningplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.entity.LearningTime;
import com.platform.machinelearningplatform.entity.StudentMessage;
import com.platform.machinelearningplatform.mapper.LearningTimeMapper;
import com.platform.machinelearningplatform.mapper.StudentMessageMapper;
import com.platform.machinelearningplatform.service.inter.LearningTimeUpdateService;
import com.platform.machinelearningplatform.utils.UserMessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.service.impl
 * @Author: EnMing Zhang
 * @CreateTime: 2023-01-27  16:14
 * @Description: TODO
 * @Version: 1.0
 */
@Service
public class LearningTimeUpdateServiceImp extends ServiceImpl<LearningTimeMapper, LearningTime> implements LearningTimeUpdateService {
    private StudentMessageMapper studentMessageMapper;

    @Autowired
    public void setStudentMessageMapper(StudentMessageMapper studentMessageMapper) {
        this.studentMessageMapper = studentMessageMapper;
    }

    @Override
    @Transactional
    public Result<String> updateTime(SecurityContext securityContext) {
        String account = UserMessageUtils.getAccountBySecurityContext(securityContext);
        StudentMessage one = studentMessageMapper.selectOne(new LambdaQueryWrapper<StudentMessage>().eq(StudentMessage::getAccount, account));
        if (one==null){
            return Result.error("该用户不存在，学习时间更新失败");
        }
        var studentId = one.getId();
        LearningTime learningTime = this.getOne(new LambdaQueryWrapper<LearningTime>().eq(LearningTime::getStudentId, studentId));
        if (learningTime == null) {
            this.save(LearningTime.builder().studentId(studentId).build());
            return Result.<String>success(null).successMsg("更新时间成功");
        }
        //时间增加十分钟
        this.updateById(LearningTime.builder().id(learningTime.getId()).studentId(studentId).time(learningTime.getTime() + 10).build());
        return Result.<String>success(null).successMsg("更新时间成功");
    }
}
