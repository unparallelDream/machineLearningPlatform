package com.platform.machinelearningplatform.service.inter;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.entity.StudentPicture;
import org.springframework.web.multipart.MultipartFile;

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
}
