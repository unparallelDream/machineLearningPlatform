package com.platform.machinelearningplatform.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.dto.ExcelData;
import com.platform.machinelearningplatform.dto.StudentDetail;
import com.platform.machinelearningplatform.entity.*;
import com.platform.machinelearningplatform.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.service.impl
 * @Author: EnMing Zhang
 * @CreateTime: 2023-02-05  11:06
 * @Description: TODO
 * @Version: 1.0
 */
@Service
@Transactional
public class ManageServiceImp {
    @Autowired
    private LevelsMapper levelsMapper;
    @Autowired
    private StudentMessageMapper studentMessageMapper;

    public Result<List<StudentDetail>> getStudentDetails(StudentMessage message) {
        if (message == null || message.getAccount() == null) {
            return Result.error("参数为空，请提供参数");
        }
        StudentMessage one = studentMessageMapper.selectOne(new LambdaQueryWrapper<StudentMessage>().eq(StudentMessage::getAccount, message.getAccount()));
        if (one == null || one.getId() == null) {
            return Result.error("查询结果不存在");
        }
        Long id = one.getId();
        List<Levels> levels = levelsMapper.selectList(new LambdaQueryWrapper<Levels>().eq(Levels::getUserId, id).orderByAsc(Levels::getId));
        if (levels.isEmpty())
            return Result.error("该用户没有训练记录");
        ArrayList<StudentDetail> studentDetails = new ArrayList<>();
        levels.forEach(e -> {
            List<StudentDetail> list = studentDetails.stream().filter(p -> p.getOrders() == e.getOrders()).toList();
            ArrayList<Levels> str = new ArrayList<>();
            str.add(e);
            if (list.isEmpty()) {
                studentDetails.add(StudentDetail.builder().orders(e.getOrders()).time(e.getCreateTime()).userId(e.getUserId()).levels(str).build());
            } else {
                list.get(0).getLevels().add(e);
            }
        });
        studentDetails.forEach(e -> {
            e.getLevels().sort((o1, o2) -> (int) (o1.getId() - o2.getId()));
        });
        return Result.success(studentDetails);
    }

    @Autowired
    private ParamsMapper paramsMapper;

    public Result<Params> getParamsByLevelsId(Long id) {
        if (id == null)
            return Result.error("id不存在");
        Params one = paramsMapper.selectOne(new LambdaQueryWrapper<Params>().eq(Params::getLevelId, id));
        return one == null ? Result.error("训练参数不存在") : Result.success(one);
    }

    @Autowired
    private TrainResultMapper trainResultMapper;

    public Result<List<TrainResult>> getTrainResultByLevelId(Integer orders) {
        if (orders == null)
            return Result.error("orders不存在");
        List<TrainResult> list = trainResultMapper.selectList(new LambdaQueryWrapper<TrainResult>().eq(TrainResult::getOrders, orders).orderByAsc(TrainResult::getIndexI));
        return Result.success(list);
    }

    @Autowired
    private LearningTimeMapper learningTimeMapper;

    public void createExcelFile(String path) {
        List<StudentMessage> list = studentMessageMapper.selectList(new LambdaQueryWrapper<>());
        List<LearningTime> learningTimes = learningTimeMapper.selectList(new LambdaQueryWrapper<>());
        List<ExcelData> excelData = list.stream().map(e -> {
                    Long time = null;
                    List<LearningTime> times = learningTimes.stream().filter(j -> Objects.equals(j.getStudentId(), e.getId())).toList();
                    if (!times.isEmpty()) {
                        time = times.get(0).getTime();
                    }

                    List<Levels> levels = levelsMapper.selectList(new LambdaQueryWrapper<Levels>().eq(Levels::getUserId, e.getId()));
            HashSet<Integer> integers = new HashSet<>();
            levels.forEach(p->{integers.add(p.getOrders());});
            return ExcelData.builder()
                            .account(e.getAccount())
                            .studentClass(e.getStudentClass())
                            .studentEmail(e.getStudentEmail())
                            .studentName(e.getStudentName())
                            .studentPhoneNumber(e.getStudentPhoneNumber())
                            .studentSchool(e.getStudentSchool())
                            .studentSex(e.getStudentSex())
                            .studentNumber(e.getStudentNumber())
                            .time(time).count(integers.size()).build();
                }
        ).toList();
        EasyExcel.write(path, ExcelData.class)
                .sheet("模板")
                .doWrite(() -> {
                    // 分页查询数据
                    return excelData;
                });
    }
}
