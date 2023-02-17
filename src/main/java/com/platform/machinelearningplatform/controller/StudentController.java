package com.platform.machinelearningplatform.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.entity.LearningTime;
import com.platform.machinelearningplatform.entity.StudentMessage;
import com.platform.machinelearningplatform.mapper.LearningTimeMapper;
import com.platform.machinelearningplatform.mapper.StudentMessageMapper;
import com.platform.machinelearningplatform.utils.UserMessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.controller
 * @Author: EnMing Zhang
 * @CreateTime: 2023-02-13  20:27
 * @Description: TODO
 * @Version: 1.0
 */
@RestController
@RequestMapping("/student")
@Slf4j
public class StudentController {
    @Autowired
    private LearningTimeMapper learningTimeMapper;
    @Autowired
    private StudentMessageMapper studentMessageMapper;

    @GetMapping("/learningTime")
    public Result<Long> getLearningTime() {
        String account = UserMessageUtils.getAccountBySecurityContext(SecurityContextHolder.getContext());
        return Result.success(Optional
                .ofNullable(account)
                .map(e -> studentMessageMapper.selectOne(new LambdaQueryWrapper<StudentMessage>().eq(StudentMessage::getAccount, account)))
                .map(StudentMessage::getId)
                .map((e) -> learningTimeMapper
                        .selectOne(new LambdaQueryWrapper<LearningTime>()
                                .eq(LearningTime::getStudentId, e)))
                .map(LearningTime::getTime)
                .orElse(0L));
    }
}
