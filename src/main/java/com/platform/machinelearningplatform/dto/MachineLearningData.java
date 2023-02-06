package com.platform.machinelearningplatform.dto;

import com.platform.machinelearningplatform.entity.Params;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.dto
 * @Author: EnMing Zhang
 * @CreateTime: 2023-01-10  11:05
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineLearningData {
    private String name;
    private Params options;
}
