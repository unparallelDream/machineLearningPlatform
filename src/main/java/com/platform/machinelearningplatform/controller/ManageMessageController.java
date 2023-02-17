package com.platform.machinelearningplatform.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.entity.Message;
import com.platform.machinelearningplatform.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.controller
 * @Author: EnMing Zhang
 * @CreateTime: 2023-02-15  17:19
 * @Description: TODO
 * @Version: 1.0
 */
@RestController
@RequestMapping("/manager/message")
public class ManageMessageController {
    @Autowired
    private MessageMapper messageMapper;
    @GetMapping()
    public Result<Page<Message>> getMessage(String account,Integer page,Integer pageSize){
        return Optional.ofNullable(account).map(e->{
            Page<Message> p = new Page<>(page, pageSize);
            return messageMapper.selectPage(p,new LambdaQueryWrapper<Message>().like(Message::getFromUser,e));
        }).map(Result::success).orElse(Result.error("不存在数据"));
    }
    @DeleteMapping()
    public Result<String> deleteMessagesByMessages(@RequestBody List<Message> messages){
        return Optional.ofNullable(messages).map(e->{
            messageMapper.deleteBatchIds(messages);
            return Result.success("删除成功");
        }).orElse(Result.error("删除失败"));
    }
}
