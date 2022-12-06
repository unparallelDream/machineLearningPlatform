package com.platform.machinelearningplatform.service.inter;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.entity.StudentMessage;
import com.platform.machinelearningplatform.entity.StudentPicture;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.service.inter
 * @Author: EnMing Zhang
 * @CreateTime: 2022-11-28  16:36
 * @Description: TODO
 * @Version: 1.0
 */
public interface QueryDataService extends IService<StudentPicture> {
    Result<String>  getAccount();
    MultipartFile getPicture(String account);
    Result<Map> getStudentInfo(String account);
    Result<String> setStudentInfo(StudentMessage studentMessage);
}
