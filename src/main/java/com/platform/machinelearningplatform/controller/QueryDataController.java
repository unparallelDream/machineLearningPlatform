package com.platform.machinelearningplatform.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.dto.MachineLearningData;
import com.platform.machinelearningplatform.dto.TrainData;
import com.platform.machinelearningplatform.entity.StudentMessage;
import com.platform.machinelearningplatform.entity.TrainResult;
import com.platform.machinelearningplatform.mapper.StudentMessageMapper;
import com.platform.machinelearningplatform.service.LoginMessage;
import com.platform.machinelearningplatform.service.impl.QueryDataServiceImp;
import com.platform.machinelearningplatform.service.impl.UserDetailServiceImpl;
import com.platform.machinelearningplatform.service.inter.MachineLearnService;
import com.platform.machinelearningplatform.service.inter.QueryDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.config
 * @Author: EnMing Zhang
 * @CreateTime: 2022-11-28  16:34
 * @Description: TODO
 * @Version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/student")
public class QueryDataController {
    @Autowired
    public void setQueryDataService(QueryDataService queryDataService) {
        this.queryDataService = queryDataService;
    }

    private MachineLearnService machineLearnService;

    @Autowired
    public void setMachineLearnService(MachineLearnService machineLearnService) {
        this.machineLearnService = machineLearnService;
    }

    private QueryDataService queryDataService;
    @GetMapping("/getAccount")
    public Result<String> getAccount() {
        return queryDataService.getAccount();
    }

    @GetMapping("/getAvatar")
    public Result<String> getHeader(@RequestParam String account) {
        return queryDataService.getPicture(account);
    }

    @GetMapping("/getStudentInfo")
    public Result<Map> getStudentInfo() {
        return queryDataService.getStudentInfo((getAccount().getData()));
    }

    @PutMapping("/editInfo")
    public Result<String> setStudentInfo(@RequestBody StudentMessage message) {
        return queryDataService.setStudentInfo(message);
    }

    @PostMapping("/saveModelFloors")
    public Result<String> addLearningModel(@RequestBody ArrayList<MachineLearningData> data) {
        LoginMessage principal = (LoginMessage) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        return machineLearnService.storeLearnMessage(data,queryDataService.getStudentId(username));
    }
    @PostMapping("/saveTrainResult")
    public Result<String> addLearningData(@RequestBody TrainData trainData){
        return machineLearnService.storeLearnData(trainData.getData(), trainData.getOrders());
    }
    @GetMapping("/getTrainResult")
    public Result<List<TrainResult>> getLearningData(@RequestParam int orders){
        return machineLearnService.getLearnData(orders);
    }
}
