package com.platform.machinelearningplatform.dto;

import com.platform.machinelearningplatform.entity.TrainResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.dto
 * @Author: EnMing Zhang
 * @CreateTime: 2023-01-15  16:24
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainData {
    private int orders;
    private ArrayList<TrainResult> data;
}
