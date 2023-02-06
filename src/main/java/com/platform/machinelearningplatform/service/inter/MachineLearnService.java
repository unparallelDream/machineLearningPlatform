package com.platform.machinelearningplatform.service.inter;

import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.dto.MachineLearningData;
import com.platform.machinelearningplatform.entity.Params;
import com.platform.machinelearningplatform.entity.TrainResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.service.inter
 * @Author: EnMing Zhang
 * @CreateTime: 2023-01-10  10:57
 * @Description: TODO
 * @Version: 1.0
 */
public interface MachineLearnService {
    Result<String> storeLearnMessage(ArrayList<MachineLearningData> data, Long userId);
    Result<String> storeLearnData(ArrayList<TrainResult> data,int orders);
    Result<ArrayList<ArrayList<MachineLearningData>>> getLearnMessage(Long userId);

    Result<List<TrainResult>> getLearnData(int orders);
}
