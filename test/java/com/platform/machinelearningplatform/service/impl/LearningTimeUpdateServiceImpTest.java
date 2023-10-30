package com.platform.machinelearningplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.entity.StudentMessage;
import com.platform.machinelearningplatform.mapper.StudentMessageMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class LearningTimeUpdateServiceImpTest {

    @Autowired
    private LearningTimeUpdateServiceImp learningTimeUpdateServiceImp;

    @Test
    void setStudentMessageMapper() {
        learningTimeUpdateServiceImp.setStudentMessageMapper(new StudentMessageMapper() {
            @Override
            public int insert(StudentMessage entity) {
                return 0;
            }

            @Override
            public int deleteById(Serializable id) {
                return 0;
            }

            @Override
            public int deleteById(StudentMessage entity) {
                return 0;
            }

            @Override
            public int deleteByMap(Map<String, Object> columnMap) {
                return 0;
            }

            @Override
            public int delete(Wrapper<StudentMessage> queryWrapper) {
                return 0;
            }

            @Override
            public int deleteBatchIds(Collection<?> idList) {
                return 0;
            }

            @Override
            public int updateById(StudentMessage entity) {
                return 0;
            }

            @Override
            public int update(StudentMessage entity, Wrapper<StudentMessage> updateWrapper) {
                return 0;
            }

            @Override
            public StudentMessage selectById(Serializable id) {
                return null;
            }

            @Override
            public List<StudentMessage> selectBatchIds(Collection<? extends Serializable> idList) {
                return null;
            }

            @Override
            public List<StudentMessage> selectByMap(Map<String, Object> columnMap) {
                return null;
            }

            @Override
            public Long selectCount(Wrapper<StudentMessage> queryWrapper) {
                return null;
            }

            @Override
            public List<StudentMessage> selectList(Wrapper<StudentMessage> queryWrapper) {
                return null;
            }

            @Override
            public List<Map<String, Object>> selectMaps(Wrapper<StudentMessage> queryWrapper) {
                return null;
            }

            @Override
            public List<Object> selectObjs(Wrapper<StudentMessage> queryWrapper) {
                return null;
            }

            @Override
            public <P extends IPage<StudentMessage>> P selectPage(P page, Wrapper<StudentMessage> queryWrapper) {
                return null;
            }

            @Override
            public <P extends IPage<Map<String, Object>>> P selectMapsPage(P page, Wrapper<StudentMessage> queryWrapper) {
                return null;
            }
        });
    }

    @Test
    void updateTime() {
        Result<String> stringResult = learningTimeUpdateServiceImp.updateTime(new StudentMessage(new StudentMessage(1,"lll","lll","lll","lll","lll","lll","lll","lll","lll")));
        System.out.println(stringResult);
    }
}