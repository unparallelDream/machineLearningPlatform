package com.platform.machinelearningplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.entity.StudentMessage;
import com.platform.machinelearningplatform.entity.StudentPicture;
import com.platform.machinelearningplatform.mapper.StudentMessageMapper;
import com.platform.machinelearningplatform.mapper.StudentPictureMapper;
import com.platform.machinelearningplatform.service.inter.QueryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.service.impl
 * @Author: EnMing Zhang
 * @CreateTime: 2022-11-28  16:38
 * @Description: TODO
 * @Version: 1.0
 */
@Service
public class QueryDataServiceImp extends ServiceImpl<StudentPictureMapper, StudentPicture> implements QueryDataService {
    @Autowired
    public void setStudentMessageMapper(StudentMessageMapper studentMessageMapper) {
        this.studentMessageMapper = studentMessageMapper;
    }

    private StudentMessageMapper studentMessageMapper;

    @Override
    public Result<String> getAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        return Result.success(principal.getUsername()).successMsg("获取账号成功");
    }

    @Override
    public MultipartFile getPicture(String account) {
//        String account = (String) getAccount().getMap().get("account");
        LambdaQueryWrapper<StudentMessage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudentMessage::getAccount, account);

        StudentMessage studentMessage = studentMessageMapper.selectOne(wrapper);
        LambdaQueryWrapper<StudentPicture> wrapperPicture = new LambdaQueryWrapper<>();
        wrapperPicture.eq(StudentPicture::getId, studentMessage.getId());
        List<StudentPicture> list = list(wrapperPicture);
        if (list.isEmpty()) {
            log.error("文件为空");
            return null;
        }
        return new MockMultipartFile(list.get(0).getFileName(), list.get(0).getPicture());
    }

    @Override
    public Result<Map> getStudentInfo(String account) {
        var wrapper = new LambdaQueryWrapper<StudentMessage>();
        wrapper.eq(StudentMessage::getAccount, account);
        StudentMessage one = studentMessageMapper.selectOne(wrapper);
        one.setPassword(null);
        var result = Result.<Map>success(null).successMsg("获取信息成功");
        result.getMap().put("account",one.getAccount());
        result.getMap().put("studentEmail",one.getStudentEmail());
        result.getMap().put("studentClass",one.getStudentClass());
        result.getMap().put("studentName",one.getStudentName());
        result.getMap().put("studentNumber",one.getStudentNumber());
        result.getMap().put("studentPhoneNumber",one.getStudentPhoneNumber());
        result.getMap().put("studentSchool",one.getStudentSchool());
        result.getMap().put("studentSex",one.getStudentSex());
        result.setData(result.getMap());
        return result;
    }

    @Override
    public Result<String> setStudentInfo(StudentMessage studentMessage) {
        var wrapper = new LambdaQueryWrapper<StudentMessage>();
        wrapper.eq(StudentMessage::getAccount,studentMessage.getAccount());
        StudentMessage message = studentMessageMapper.selectOne(wrapper);
        if (Objects.isNull(message)){
            return Result.error("不存在该account:"+studentMessage.getAccount());
        }
        var wrap = new LambdaUpdateWrapper<StudentMessage>();
        wrap.eq(StudentMessage::getAccount,studentMessage.getAccount());
        studentMessage.setPassword(message.getPassword());
        studentMessage.setId(message.getId());
        studentMessage.setDeleted(message.getDeleted());
        this.studentMessageMapper.update(studentMessage,wrap);
        return Result.<String>success(null).successMsg("修改信息成功");
    }
}
