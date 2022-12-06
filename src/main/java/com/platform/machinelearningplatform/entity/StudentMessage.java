package com.platform.machinelearningplatform.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import springfox.documentation.annotations.ApiIgnore;

import java.io.Serializable;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.entity
 * @Author: EnMing Zhang
 * @CreateTime: 2022-11-13  11:40
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("学生信息")
public class StudentMessage implements Serializable {
    private final static Long serialVersionUID=1L;
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String account;
    private String password;
    private String studentName;
    private String studentNumber;
    private String studentEmail;
    private String studentPhoneNumber;
    private String studentClass;
    private String studentSchool;
    private String studentSex;
    @TableLogic
    //    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;
}
