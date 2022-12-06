package com.platform.machinelearningplatform.entity;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.entity
 * @Author: EnMing Zhang
 * @CreateTime: 2022-11-27  17:03
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Api("图片上传信息")
public class StudentPicture implements Serializable {
    private final static Long serialVersionUID=1L;
    private Long id;
    private byte[] picture;
    private String fileName;
}
