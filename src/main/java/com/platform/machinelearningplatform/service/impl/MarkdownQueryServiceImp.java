package com.platform.machinelearningplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.machinelearningplatform.entity.Markdown;
import com.platform.machinelearningplatform.mapper.MarkdownMapper;
import com.platform.machinelearningplatform.service.inter.MarkdownQueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.service.impl
 * @Author: EnMing Zhang
 * @CreateTime: 2023-01-03  12:52
 * @Description: TODO
 * @Version: 1.0
 */
@Service
public class MarkdownQueryServiceImp extends ServiceImpl<MarkdownMapper,Markdown> implements MarkdownQueryService {

    @Override
    @Transactional
    public String queryMarkdown(String name) {
        var wrapper = new LambdaQueryWrapper<Markdown>();
        wrapper.eq(Markdown::getName,name);
        List<Markdown> list = this.list(wrapper);
        if (list==null|| list.size() == 0){
            return null;
        }
        return list.get(0).getContent();
    }
}
