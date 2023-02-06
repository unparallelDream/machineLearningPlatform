package com.platform.machinelearningplatform.dto;

import com.platform.machinelearningplatform.entity.StudentMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.dto
 * @Author: EnMing Zhang
 * @CreateTime: 2023-02-04  19:52
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentMessageData {
    private String account;
    private String studentName;
    private String studentNumber;
    private String studentClass;
    private Integer count;
    private Long learningTime;
    public StudentMessageData(StudentMessage message){
        this.account=message.getAccount();
        this.studentName=message.getStudentName();
        this.studentNumber=message.getStudentNumber();
        this.studentClass=message.getStudentClass();
    }
}
