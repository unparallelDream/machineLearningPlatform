package com.platform.machinelearningplatform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.entity
 * @Author: EnMing Zhang
 * @CreateTime: 2023-01-10  10:45
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Params implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long levelId;
    private String type;
    private int kernelSize;
    private int filters;
    private int strides;
    private int poolSize;
    private String kernelInitializer;
    private String activation;
}
