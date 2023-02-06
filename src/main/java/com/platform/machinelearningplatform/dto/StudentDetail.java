package com.platform.machinelearningplatform.dto;

import com.platform.machinelearningplatform.entity.Levels;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.dto
 * @Author: EnMing Zhang
 * @CreateTime: 2023-02-05  11:07
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDetail {
    private Long userId;
    private int orders;
    private List<Levels> levels;
    private LocalDateTime time;
}
