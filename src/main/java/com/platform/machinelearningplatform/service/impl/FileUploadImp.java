package com.platform.machinelearningplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.entity.StudentMessage;
import com.platform.machinelearningplatform.entity.StudentPicture;
import com.platform.machinelearningplatform.mapper.StudentPictureMapper;
import com.platform.machinelearningplatform.service.inter.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    @Override
    public Result<String> fileUpLoad(MultipartFile file,Long id) {
        try {
            this.save(StudentPicture.builder().id(id).picture(file.getBytes()).fileName(file.getName()).build());
            return Result.<String>success(null).successMsg("图片上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("图片上传错误，请重试");
        }
    }

    @Override
    public void queryFile(Long id, StudentMessage message) {
        this.update(new LambdaUpdateWrapper<StudentPicture>().eq(StudentPicture::getId,id).set(StudentPicture::getId,message.getId()));
    }
}
