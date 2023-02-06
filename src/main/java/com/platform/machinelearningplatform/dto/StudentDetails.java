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
 * @CreateTime: 2023-02-05  17:31
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDetails {
    private Long userId;
    private int orders;
    private Levels one;
    private Levels two;
    private Levels three;
    private Levels four;
    private Levels five;
    private Levels six;
    private Levels seven;
    private Levels zero;
    private LocalDateTime time;
}
