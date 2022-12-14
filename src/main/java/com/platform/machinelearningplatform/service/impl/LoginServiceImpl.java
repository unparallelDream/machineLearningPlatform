package com.platform.machinelearningplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.machinelearningplatform.common.Result;
import com.platform.machinelearningplatform.entity.StudentMessage;
import com.platform.machinelearningplatform.mapper.StudentMessageMapper;
import com.platform.machinelearningplatform.service.LoginMessage;
import com.platform.machinelearningplatform.service.inter.LoginService;
import com.platform.machinelearningplatform.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.service.impl
 * @Author: EnMing Zhang
 * @CreateTime: 2022-11-13  12:40
 * @Description: TODO
 * @Version: 1.0
 */
@Service
@Slf4j
public class LoginServiceImpl extends ServiceImpl<StudentMessageMapper, StudentMessage> implements LoginService {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private AuthenticationManager authenticationManager;
    @Override
    public Result<String> loginStudent(StudentMessage studentMessage) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(studentMessage.getAccount(), studentMessage.getPassword()));
        if (Objects.isNull(authenticate)){
            throw new BadCredentialsException("????????????????????????");
        }
        LoginMessage loginMessage=(LoginMessage)authenticate.getPrincipal();
        String id = loginMessage.getStudentMessage().getId().toString();
        String jwt = JwtUtil.createJWT(id);
        redisTemplate.opsForValue().set("login:"+id,loginMessage,7, TimeUnit.DAYS);
        return Result.<String>success(jwt).successMsg("????????????");
    }

    @Override
    public Result<String> loginStudentEmail(String studentEmail, String code) {
        if (stringRedisTemplate.opsForValue().get(studentEmail)==null){
            throw new BadCredentialsException("????????????????????????");
        }
        if (!Objects.equals(stringRedisTemplate.opsForValue().get(studentEmail),code)){
            throw new BadCredentialsException("?????????????????????");
        }
        LambdaQueryWrapper<StudentMessage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudentMessage::getStudentEmail,studentEmail);
        List<StudentMessage> list = list(wrapper);
        if (list.isEmpty()){
            throw new BadCredentialsException("?????????????????????????????????");
        }
        LoginMessage loginMessage = new LoginMessage(list.get(0));
        String id = loginMessage.getStudentMessage().getId().toString();
        String jwt = JwtUtil.createJWT(id);
        redisTemplate.opsForValue().set("login:"+id,loginMessage,7, TimeUnit.DAYS);
        return Result.success(jwt).successMsg("??????????????????");
    }

    @Value("${spring.mail.username}")
    private String from;
    @Resource
    private JavaMailSender mailSender;
    @Override
    public void sendMsg(String mail, String subject, String context) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setSubject(subject);
        mailMessage.setText(context);
        mailMessage.setTo(mail);
        //??????????????????
        mailSender.send(mailMessage);
    }
}
