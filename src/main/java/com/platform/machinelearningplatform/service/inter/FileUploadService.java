package com.platform.machinelearningplatform.service.inter;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.entity.StudentMessage;
import com.platform.machinelearningplatform.entity.StudentPicture;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Objects;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.service.inter
 * @Author: EnMing Zhang
 * @CreateTime: 2022-11-27  17:05
 * @Description: TODO
 * @Version: 1.0
 */
public interface FileUploadService extends IService<StudentPicture> {
    Result<String> fileUpLoad(MultipartFile file,Long id);
    void queryFile(Long id, StudentMessage message);
}
