package com.platform.machinelearningplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.entity.StudentMessage;
import com.platform.machinelearningplatform.mapper.StudentMessageMapper;
import com.platform.machinelearningplatform.service.inter.FileUploadService;
import com.platform.machinelearningplatform.service.inter.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.service.impl
 * @Author: EnMing Zhang
 * @CreateTime: 2022-11-13  17:34
 * @Description: TODO
 * @Version: 1.0
 */
@Service
public class RegisterServiceImpl extends ServiceImpl<StudentMessageMapper, StudentMessage> implements RegisterService {
    @Autowired
    public void setFileUploadService(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    private FileUploadService fileUploadService;

    @Override
    public Result<String> register(StudentMessage message) {
        Long id = message.getId();
        log.error(id.toString());
        message.setId(null);
        message.setPassword(new BCryptPasswordEncoder().encode(message.getPassword()));
        LambdaQueryWrapper<StudentMessage> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(StudentMessage::getAccount, message.getAccount()).or()
                .eq(StudentMessage::getStudentEmail, message.getStudentEmail()).or()
                .eq(StudentMessage::getStudentNumber, message.getStudentNumber());
        if (!list(wrapper).isEmpty())
            return Result.error("注册失败,账号学号或邮箱重复");
        wrapper.clear();
        boolean save = this.save(message);
        if (save) {
            wrapper.eq(StudentMessage::getAccount, message.getAccount());
            List<StudentMessage> list = this.list(wrapper);
            fileUploadService.queryFile(id, list.get(0));
            return Result.<String>success(null).successMsg("注册成功");
        } else
            return Result.error("注册失败");
    }
}
