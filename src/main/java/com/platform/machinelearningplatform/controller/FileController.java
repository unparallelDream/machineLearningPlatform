package com.platform.machinelearningplatform.controller;

import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.service.inter.FileUploadService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
}
