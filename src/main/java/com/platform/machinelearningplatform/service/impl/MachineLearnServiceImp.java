package com.platform.machinelearningplatform.service.impl;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.dto.MachineLearningData;
import com.platform.machinelearningplatform.entity.Levels;
import com.platform.machinelearningplatform.entity.Params;
import com.platform.machinelearningplatform.entity.TrainResult;
import com.platform.machinelearningplatform.mapper.LevelsMapper;
import com.platform.machinelearningplatform.mapper.ParamsMapper;
import com.platform.machinelearningplatform.mapper.TrainResultMapper;
import com.platform.machinelearningplatform.service.inter.MachineLearnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.service.impl
 * @Author: EnMing Zhang
 * @CreateTime: 2023-01-10  10:58
 * @Description: TODO
 * @Version: 1.0
 */
@Service
@Slf4j
public class MachineLearnServiceImp implements MachineLearnService {
    @Autowired
    public void setParamsMapper(ParamsMapper paramsMapper) {
        this.paramsMapper = paramsMapper;
    }

    @Autowired
    public void setLevelsMapper(LevelsMapper levelsMapper) {
        this.levelsMapper = levelsMapper;
    }

    @Autowired
    public void setTrainResultMapper(TrainResultMapper trainResultMapper) {
        this.trainResultMapper = trainResultMapper;
    }

    private ParamsMapper paramsMapper;
    private LevelsMapper levelsMapper;
    private TrainResultMapper trainResultMapper;

    @Override
    @Transactional
    public Result<String> storeLearnMessage(ArrayList<MachineLearningData> data, Long userId) {
        ArrayList<Levels> levels = new ArrayList<>();
        ArrayList<Params> params = new ArrayList<>();
        int uuid = (int) UUID.randomUUID().getLeastSignificantBits();
        SnowflakeGenerator snowflakeGenerator = new SnowflakeGenerator();
        for (int i = 0; i < data.size(); i++) {
            long l = snowflakeGenerator.next();
            Params options = data.get(i).getOptions();
            levels.add(Levels.builder().userId(userId).id(l).orders(uuid).type(data.get(i).getName()).build());
            options.setType(data.get(i).getName());
            options.setLevelId(l);
            params.add(options);
        }
        levels.forEach(e -> this.levelsMapper.insert(e));
        params.forEach(e -> this.paramsMapper.insert(e));
        return Result.success(String.valueOf(uuid)).successMsg("添加成功");
    }

    @Override
    @Transactional
    public Result<String> storeLearnData(ArrayList<TrainResult> data,int orders) {
        int i=0;
        for (TrainResult e : data) {
            e.setOrders(orders);
            e.setIndexI(i);
            i++;
            trainResultMapper.insert(e);
        }
        return Result.<String>success(null).successMsg("添加成功");
    }

    @Override
    @Transactional
    public Result<ArrayList<ArrayList<MachineLearningData>>> getLearnMessage(Long userId) {
        var wrapperParams = new LambdaQueryWrapper<Params>();
        var wrapperLevels = new LambdaQueryWrapper<Levels>();
        List<Levels> levels = levelsMapper.selectList(wrapperLevels.eq(Levels::getUserId, userId));
        var uuids = new ArrayList<Integer>();
        var data = new ArrayList<ArrayList<MachineLearningData>>();
        levels.forEach(e -> {
            if (!uuids.contains(e.getOrders()))
                uuids.add(e.getOrders());
        });
        for (int i = 0; i < uuids.size(); i++) {
            data.add(new ArrayList<>());
        }
//
//
//
        return Result.success(data).successMsg("添加成功");
    }

    @Override
    public Result<List<TrainResult>> getLearnData(int orders) {
        var wrapper = new LambdaQueryWrapper<TrainResult>();
        wrapper.eq(TrainResult::getOrders,orders);
        List<TrainResult> trainResults = trainResultMapper.selectList(wrapper);
        trainResults.forEach(e->{
            e.setId(null);
        });
        return Result.success(trainResults).successMsg("查询训练数据成功");
    }
}
