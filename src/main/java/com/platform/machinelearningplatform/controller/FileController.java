package com.platform.machinelearningplatform.controller;

import cn.hutool.core.codec.Base64Encoder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.entity.StudentMessage;
import com.platform.machinelearningplatform.entity.StudentPicture;
import com.platform.machinelearningplatform.mapper.StudentMessageMapper;
import com.platform.machinelearningplatform.mapper.StudentPictureMapper;
import com.platform.machinelearningplatform.service.inter.FileUploadService;
import com.platform.machinelearningplatform.utils.UserMessageUtils;
import io.swagger.annotations.Api;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.controller
 * @Author: EnMing Zhang
 * @CreateTime: 2022-11-27  17:00
 * @Description: TODO
 * @Version: 1.0
 */
@Api("文件上传")
@RestController
@RequestMapping("/student")
public class FileController {
    private FileUploadService fileUploadService;

    public FileUploadService getFileUploadService() {
        return fileUploadService;
    }

    @Autowired
    public void setFileUploadService(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }
    @PostMapping(value = "/uploadAvatar",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Result<String> fileUpload(@RequestParam MultipartFile file,@RequestParam Long id) {
        return fileUploadService.fileUpLoad(file, id);
    }
    @Autowired
    private StudentMessageMapper studentMessageMapper;
    @Autowired
    private StudentPictureMapper studentPictureMapper;
    @PostMapping("/updateAvatar")
    @SneakyThrows
    public Result<String> fileUpLoad(MultipartFile file) {
        StudentMessage one = studentMessageMapper.selectOne(new LambdaQueryWrapper<StudentMessage>().eq(StudentMessage::getAccount, UserMessageUtils.getAccountBySecurityContext(SecurityContextHolder.getContext())));
        if (one==null||one.getId()==null){
            return Result.error("学生信息不存在");
        }
        StudentPicture picture = studentPictureMapper.selectOne(new LambdaQueryWrapper<StudentPicture>().eq(StudentPicture::getId, one.getId()));
        if (picture==null){
            studentPictureMapper.insert(StudentPicture.builder().id(one.getId()).picture(Base64Encoder.encode(file.getBytes()).getBytes()).fileName(file.getName()).build());
        }else {
            studentPictureMapper.updateById(StudentPicture.builder().id(one.getId()).picture(Base64Encoder.encode(file.getBytes()).getBytes()).fileName(file.getName()).build());
        }
        return Result.success("文件修改成功");
    }
}
