package com.platform.machinelearningplatform.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.dto.StudentDetail;
import com.platform.machinelearningplatform.dto.StudentDetails;
import com.platform.machinelearningplatform.dto.StudentMessageData;
import com.platform.machinelearningplatform.entity.Levels;
import com.platform.machinelearningplatform.entity.Params;
import com.platform.machinelearningplatform.entity.StudentMessage;
import com.platform.machinelearningplatform.entity.TrainResult;
import com.platform.machinelearningplatform.mapper.StudentMessageMapper;
import com.platform.machinelearningplatform.service.impl.ManageServiceImp;
import com.platform.machinelearningplatform.service.inter.QueryDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.controller
 * @Author: EnMing Zhang
 * @CreateTime: 2022-12-18  14:11
 * @Description: TODO
 * @Version: 1.0
 */
@RestController
@Slf4j
@RequestMapping("/manager")
public class ManageController {
    @Autowired
    public void setMessageMapper(StudentMessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    private StudentMessageMapper messageMapper;
    @Autowired
    private ManageServiceImp manageServiceImp;

    @GetMapping("/queryStudent")
    @Transactional
    public Result<Page<StudentMessage>> selectByPage(int page, int pageSize, String studentName) {
        var pageInfo = new Page<StudentMessage>(page, pageSize);
        var wrapper = new LambdaQueryWrapper<StudentMessage>();
        wrapper.like(studentName != null, StudentMessage::getStudentName, studentName);
        wrapper.orderByAsc(StudentMessage::getStudentClass);
        messageMapper.selectPage(pageInfo, wrapper);
        return Result.success(pageInfo);
    }

    @Autowired
    private QueryDataService queryDataService;

    @GetMapping("/queryStudentLearningInfo")
    public Result<Page<StudentMessageData>> selectStudentMessageDataByPage(int page, int pageSize, String studentName) {
        return queryDataService.getStudentData(page, pageSize, studentName);
    }

    @GetMapping("/getTrainingData")
    public Result<Page<StudentDetails>> getDetails(int page, int pageSize, String account) {
        Result<List<StudentDetail>> result = manageServiceImp.getStudentDetails(StudentMessage.builder().account(account).build());
        if (result.getCode() == 0)
            return Result.error(result.getMsg());
        var list = result.getData();
        Page<StudentDetails> p = new Page<>(page, pageSize);
        p.setTotal(list.size());
        var from = Math.max((page - 1) * pageSize, 0);
        var to = Math.min(page * pageSize, list.size());
        if (from >= to) {
            return Result.error("数据不存在");
        }
        p.setRecords(list.subList(from, to).stream().map(e -> {
            List<Levels> levels = e.getLevels();
            int size = levels.size();
            return StudentDetails.builder().orders(e.getOrders()).time(e.getTime()).userId(e.getUserId())
                    .zero(size > 0 ? levels.get(0) : null)
                    .one(size > 1 ? levels.get(1) : null)
                    .two(size > 2 ? levels.get(2) : null)
                    .three(size > 3 ? levels.get(3) : null)
                    .four(size > 4 ? levels.get(4) : null)
                    .five(size > 5 ? levels.get(5) : null)
                    .six(size > 6 ? levels.get(6) : null)
                    .seven(size > 7 ? levels.get(7) : null).build();
        }).toList());
        return Result.success(p);
    }

    @GetMapping("/getParamsById")
    public Result<Params> getParamsByID(Long id) {
        return manageServiceImp.getParamsByLevelsId(id);
    }

    @GetMapping("/getTrainResult")
    public Result<List<TrainResult>> getTrainResultByLevelId(Integer orders) {
        return manageServiceImp.getTrainResultByLevelId(orders);
    }

    @GetMapping("/excel")
    public Result<String> createExcelFile() {
        manageServiceImp.createExcelFile();
        return Result.success("文件生成成功");
    }

    @GetMapping("/static/{fileName}")
    public ResponseEntity staticResources(@PathVariable String fileName) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("../machineLearningPlatform/src/main/resources/static/" + fileName);
        byte[] bytes = fileInputStream.readAllBytes();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(fileName, StandardCharsets.UTF_8) +"\"")
                .body(bytes);
    }
}
