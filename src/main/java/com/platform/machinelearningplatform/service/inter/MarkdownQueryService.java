package com.platform.machinelearningplatform.service.inter;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.machinelearningplatform.entity.Markdown;
import org.springframework.transaction.annotation.Transactional;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.service.inter
 * @Author: EnMing Zhang
 * @CreateTime: 2023-01-03  12:52
 * @Description: TODO
 * @Version: 1.0
 */
public interface MarkdownQueryService extends IService<Markdown> {
    String queryMarkdown(String name);
}
