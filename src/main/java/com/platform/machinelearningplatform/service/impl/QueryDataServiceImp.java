package com.platform.machinelearningplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.dto.StudentMessageData;
import com.platform.machinelearningplatform.entity.*;
import com.platform.machinelearningplatform.mapper.*;
import com.platform.machinelearningplatform.service.inter.QueryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.service.impl
 * @Author: EnMing Zhang
 * @CreateTime: 2022-11-28  16:38
 * @Description: TODO
 * @Version: 1.0
 */
@Service
@PropertySource({"classpath:application.yml"})
public class QueryDataServiceImp extends ServiceImpl<StudentPictureMapper, StudentPicture> implements QueryDataService {
    @Autowired
    public void setStudentMessageMapper(StudentMessageMapper studentMessageMapper) {
        this.studentMessageMapper = studentMessageMapper;
    }

    private StudentMessageMapper studentMessageMapper;

    @Override
    @Transactional
    public Result<String> getAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = null;
        try {
            principal = (UserDetails) authentication.getPrincipal();
        } catch (Exception e) {
            return Result.error("获取账号失败");
        }
        return Result.success(principal.getUsername()).successMsg("获取账号成功");
    }

    @Transactional
    @Override
    public Result<String> getPicture(String account) {
        LambdaQueryWrapper<StudentMessage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudentMessage::getAccount, account);
        StudentMessage studentMessage = studentMessageMapper.selectOne(wrapper);
        if (studentMessage == null) {
            return Result.error("学生信息不存在");
        }
        LambdaQueryWrapper<StudentPicture> wrapperPicture = new LambdaQueryWrapper<>();
        wrapperPicture.eq(StudentPicture::getStudentId, studentMessage.getId());
        List<StudentPicture> list = list(wrapperPicture);
        if (list.isEmpty()) {
            return Result.error("头像不存在");
        }
        return Result.success(list.get(0).getUrl());
    }

    @Override
    @Transactional
    public Result<Map> getStudentInfo(String account) {
        var wrapper = new LambdaQueryWrapper<StudentMessage>();
        wrapper.eq(StudentMessage::getAccount, account);
        StudentMessage one = studentMessageMapper.selectOne(wrapper);
        if (one == null) {
            return Result.error("获取信息失败");
        }
        one.setPassword(null);
        var result = Result.<Map>success(null).successMsg("获取信息成功");
        result.getMap().put("account", one.getAccount());
        result.getMap().put("studentEmail", one.getStudentEmail());
        result.getMap().put("studentClass", one.getStudentClass());
        result.getMap().put("studentName", one.getStudentName());
        result.getMap().put("studentNumber", one.getStudentNumber());
        result.getMap().put("studentPhoneNumber", one.getStudentPhoneNumber());
        result.getMap().put("studentSchool", one.getStudentSchool());
        result.getMap().put("studentSex", one.getStudentSex());
        result.setData(result.getMap());
        return result;
    }

    @Value("${account:root}")
    private String account;
    @Value("${id:123}")
    private Long id;

    @Override
    @Transactional
    public Long getStudentId(String account) {
        if (account.equals(this.account))
            return this.id;
        var wrapper = new LambdaQueryWrapper<StudentMessage>();
        wrapper.eq(StudentMessage::getAccount, account);
        StudentMessage one = studentMessageMapper.selectOne(wrapper);
        if (one == null) {
            return null;
        }
        return one.getId();
    }

    @Override
    @Transactional
    public Result<String> setStudentInfo(StudentMessage studentMessage) {
        var wrapper = new LambdaQueryWrapper<StudentMessage>();
        wrapper.eq(StudentMessage::getAccount, studentMessage.getAccount());
        StudentMessage message = studentMessageMapper.selectOne(wrapper);
        if (Objects.isNull(message)) {
            return Result.error("不存在该account:" + studentMessage.getAccount());
        }
        var wrap = new LambdaUpdateWrapper<StudentMessage>();
        wrap.eq(StudentMessage::getAccount, studentMessage.getAccount());
        studentMessage.setPassword(message.getPassword());
        studentMessage.setId(message.getId());
        studentMessage.setDeleted(message.getDeleted());
        this.studentMessageMapper.update(studentMessage, wrap);
        return Result.<String>success(null).successMsg("修改信息成功");
    }

    @Autowired
    private TrainResultMapper trainResultMapper;
    @Autowired
    private LearningTimeMapper learningTimeMapper;
    @Autowired
    private LevelsMapper levelsMapper;

    @Transactional
    @Override
    public Result<Page<StudentMessageData>> getStudentData(int page, int pageSize, String studentName) {
        Page<StudentMessage> studentMessagePage = new Page<>(page, pageSize);
        Page<StudentMessage> selectPage = studentMessageMapper.selectPage(studentMessagePage, new LambdaQueryWrapper<StudentMessage>().like(StudentMessage::getStudentName, studentName));
        List<StudentMessage> studentMessages = selectPage.getRecords();
        List<TrainResult> trainResults = trainResultMapper.selectList(new LambdaQueryWrapper<>());
        List<LearningTime> learningTimes = learningTimeMapper.selectList(new LambdaQueryWrapper<>());
        List<Levels> levels = levelsMapper.selectList(new LambdaQueryWrapper<>());
        Page<StudentMessageData> messageDataPage = new Page<>(page, pageSize);
        List<StudentMessageData> list = studentMessages.stream().map(e -> {
            HashSet<Integer> integers = new HashSet<>();
            StudentMessageData data = new StudentMessageData(e);
            List<LearningTime> times = learningTimes.stream().filter(j -> Objects.equals(j.getStudentId(), e.getId())).toList();
            LearningTime learningTime = times.isEmpty() ? null : times.get(0);
            List<TrainResult> results = trainResults.stream().filter(j -> {
                List<Levels> toList = levels.stream().filter(k -> k.getOrders() == j.getOrders()).toList();
                if (toList.isEmpty()) {
                    return false;
                }
                Long userId = toList.get(0).getUserId();
                if (Objects.equals(userId, e.getId())){
                    integers.add(j.getOrders());
                }
                return Objects.equals(userId, e.getId());
            }).toList();
            data.setCount(integers.size());
            data.setLearningTime(learningTime == null ? null : learningTime.getTime());
            return data;
        }).toList();
        messageDataPage.setRecords(list);
        messageDataPage.setTotal(selectPage.getTotal());
        return Result.success(messageDataPage);
    }
}
