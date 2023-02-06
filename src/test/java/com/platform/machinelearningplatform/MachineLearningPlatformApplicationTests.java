package com.platform.machinelearningplatform;

import com.google.gson.Gson;
import com.platform.machinelearningplatform.entity.StudentMessage;
import com.platform.machinelearningplatform.service.inter.LoginService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
class MachineLearningPlatformApplicationTests {
    @Autowired
    private LoginService loginService;
    @Resource
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        System.out.println(new Gson().toJson(new StudentMessage()));

    }

    @Test
    void testJedis() {
        //获取连接
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.set("123", "123");
        log.info(jedis.get("123"));
        jedis.close();
        //执行操作
        //关闭连接
    }

    @Test
    void testTemplate() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("1", "2");
        System.out.println();
        log.error(valueOperations.get("1").toString());

    }

    @Test
    void testMail() {
        loginService.sendMsg("3271129698@qq.com", "123", "456");
    }

    @Test
    void testLombok() {
        StudentMessage build = StudentMessage.builder().studentClass("2102").studentEmail("123").build();
        log.info(build.toString());
    }

}