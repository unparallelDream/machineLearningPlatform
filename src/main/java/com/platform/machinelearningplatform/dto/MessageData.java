package com.platform.machinelearningplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.dto
 * @Author: EnMing Zhang
 * @CreateTime: 2023-01-20  11:13
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageData implements Serializable {
    private String from;
    private String text;
    private LocalDateTime createTime;
}
