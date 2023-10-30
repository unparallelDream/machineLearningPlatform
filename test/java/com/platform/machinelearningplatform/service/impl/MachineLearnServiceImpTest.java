package com.platform.machinelearningplatform.service.impl;

import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.dto.MachineLearningData;
import com.platform.machinelearningplatform.entity.TrainResult;
import com.platform.machinelearningplatform.mapper.LevelsMapper;
import com.platform.machinelearningplatform.mapper.ParamsMapper;
import com.platform.machinelearningplatform.mapper.TrainResultMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MachineLearnServiceImpTest {
    @Autowired
    private MachineLearnServiceImp machineLearnServiceImp;

    @Autowired
    private ParamsMapper paramsMapper;

    @Autowired
    private LevelsMapper levelsMapper;

    @Autowired
    private TrainResultMapper trainResultMapper;

    @Test
    void setParamsMapper() {
        machineLearnServiceImp.setParamsMapper(paramsMapper);
    }

    @Test
    void setLevelsMapper() {
        machineLearnServiceImp.setLevelsMapper(levelsMapper);
    }

    @Test
    void setTrainResultMapper() {
        machineLearnServiceImp.setTrainResultMapper(trainResultMapper);
    }

    @Test
    void storeLearnMessage() {
        Result<String> stringResult = machineLearnServiceImp.storeLearnMessage(new ArrayList<>(), Long.valueOf(1));
        System.out.println(stringResult);
    }

    @Test
    void storeLearnData() {
        Result<String> stringResult = machineLearnServiceImp.storeLearnData(new ArrayList<>(), 1);
        System.out.println(stringResult);
    }

    @Test
    void getLearnMessage() {
        Result<ArrayList<ArrayList<MachineLearningData>>> learnMessage = machineLearnServiceImp.getLearnMessage(Long.valueOf(1111));
        System.out.println(learnMessage);
    }

    @Test
    void getLearnData() {
        Result<List<TrainResult>> learnData = machineLearnServiceImp.getLearnData(1);
        System.out.println(learnData);
    }
}