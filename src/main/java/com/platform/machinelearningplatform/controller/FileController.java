package com.platform.machinelearningplatform.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.entity.StudentPicture;
import com.platform.machinelearningplatform.mapper.StudentMessageMapper;
import com.platform.machinelearningplatform.mapper.StudentPictureMapper;
import com.platform.machinelearningplatform.utils.UserMessageUtils;
import io.swagger.annotations.Api;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.controller
 * @Author: EnMing Zhang
 * @CreateTime: 2022-11-27  17:00
 * @Description: TODO
 * @Version: 1.0
 */
@Api("文件上传")
@RestController
@RequestMapping("/student")
public class FileController {
    @Autowired
    private StudentMessageMapper studentMessageMapper;
    @Autowired
    private StudentPictureMapper studentPictureMapper;
    @PostMapping("/updateAvatar")
    @SneakyThrows
    public Result<String> fileUpLoad(@RequestBody Map<String,String> map) {
        Long id = UserMessageUtils.getIdByAccount(map.get("account"),studentMessageMapper);
        String url = map.get("url");
        if (id==null){
            return Result.error("学生信息不存在");
        }
        StudentPicture picture = studentPictureMapper.selectOne(new LambdaQueryWrapper<StudentPicture>().eq(StudentPicture::getStudentId, id));
        if (picture==null){
            studentPictureMapper.insert(StudentPicture.builder().studentId(id).url(url).build());
            return Result.success("文件插入成功");
        }else {
            return studentPictureMapper.update(StudentPicture.builder().studentId(id).url(url).build(),new LambdaQueryWrapper<StudentPicture>().eq(StudentPicture::getStudentId,id))!=0?Result.success("修改成功"):Result.error("修改失败");
        }
    }
}
