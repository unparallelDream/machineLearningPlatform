package com.platform.machinelearningplatform.controller;

import com.platform.machinelearningplatform.service.impl.MarkdownQueryServiceImp;
import com.platform.machinelearningplatform.service.inter.MarkdownQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.controller
 * @Author: EnMing Zhang
 * @CreateTime: 2023-01-03  13:09
 * @Description: TODO
 * @Version: 1.0
 */
@RestController
public class MarkdownQueryController {
    @Autowired
    public void setMarkdownQueryService(MarkdownQueryService markdownQueryService) {
        this.markdownQueryService = markdownQueryService;
    }
    private MarkdownQueryService markdownQueryService;
@GetMapping("/markdown")
public String queryMarkdown(@RequestParam("name")String name){
    return markdownQueryService.queryMarkdown(name);
}
}
