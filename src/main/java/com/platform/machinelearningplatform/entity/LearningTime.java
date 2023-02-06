package com.platform.machinelearningplatform.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.entity
 * @Author: EnMing Zhang
 * @CreateTime: 2023-01-27  16:05
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LearningTime {
    private Long id;
    private Long time;
    private Long studentId;
}
