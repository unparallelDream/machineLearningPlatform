package com.platform.machinelearningplatform;

import com.google.gson.Gson;
import com.platform.machinelearningplatform.entity.StudentMessage;
import com.platform.machinelearningplatform.mapper.StudentMessageMapper;
import com.platform.machinelearningplatform.service.inter.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
class MachineLearningPlatformApplicationTests {
    @Autowired
    private LoginService loginService;
    @Autowired
    private StudentMessageMapper studentMessageMapper;
    @Resource
    private RedisTemplate redisTemplate;
    @Test
    void contextLoads() {
        System.out.println(new Gson().toJson(new StudentMessage()));
//        studentMessageMapper.deleteById(1);
    }
    @Test
    void testJedis(){
        //获取连接
        Jedis jedis = new Jedis("localhost",6379);
        jedis.set("123","123");
        log.info(jedis.get("123"));
        jedis.close();
        //执行操作
        //关闭连接
    }
    @Test
    void testTemplate(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("1","2");
        System.out.println();
        log.info(valueOperations.get("1").toString());

    }
    @Test
    void testMail(){
        loginService.sendMsg("3271129698@qq.com","123","456");
    }

}
