package com.platform.machinelearningplatform.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.dto
 * @Author: EnMing Zhang
 * @CreateTime: 2023-02-06  11:14
 * @Description: TODO
 * @Version: 1.0
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExcelData {
    @ExcelProperty("账号")
    private String account;
    @ExcelProperty("姓名")
    private String studentName;
    @ExcelProperty("学号")
    private String studentNumber;
    @ExcelProperty("邮箱")
    private String studentEmail;
    @ExcelProperty("电话")
    private String studentPhoneNumber;
    @ExcelProperty("班级")
    private String studentClass;
    @ExcelProperty("学校")
    private String studentSchool;
    @ExcelProperty("性别")
    private String studentSex;
    @ExcelProperty("学习时长(minute)")
    private Long time;
    @ExcelProperty("训练次数")
    private Integer count;
}
