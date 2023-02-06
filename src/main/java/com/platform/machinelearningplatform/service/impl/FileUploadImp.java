package com.platform.machinelearningplatform.service.impl;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.lang.generator.SnowflakeGenerator;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.entity.StudentMessage;
import com.platform.machinelearningplatform.entity.StudentPicture;
import com.platform.machinelearningplatform.mapper.StudentMessageMapper;
import com.platform.machinelearningplatform.mapper.StudentPictureMapper;
import com.platform.machinelearningplatform.service.inter.FileUploadService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.service.impl
 * @Author: EnMing Zhang
 * @CreateTime: 2022-11-27  17:08
 * @Description: TODO
 * @Version: 1.0
 */
@Slf4j
@Service
public class FileUploadImp extends ServiceImpl<StudentPictureMapper, StudentPicture> implements FileUploadService {
    @Autowired
    public void setMapper(StudentMessageMapper mapper) {
        this.mapper = mapper;
    }

    private StudentMessageMapper mapper;

    @Override
    @Transactional
    @SneakyThrows
    public Result<String> fileUpLoad(MultipartFile file, Long id) {
        try {
            StudentMessage message = mapper.selectOne(new LambdaQueryWrapper<StudentMessage>().eq(StudentMessage::getTempId,id));
            if (message==null){
                return Result.error("不存在对应ID的学生信息");
            }
            this.save(StudentPicture.builder().id(message.getId()).picture(Base64Encoder.encode(file.getBytes()).getBytes()).fileName(file.getName()).build());


            return Result.<String>success(null).successMsg("图片上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("图片上传错误，请重试");
        }
    }


}
