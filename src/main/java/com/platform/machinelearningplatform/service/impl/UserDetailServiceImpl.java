package com.platform.machinelearningplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.machinelearningplatform.entity.StudentMessage;
import com.platform.machinelearningplatform.mapper.StudentMessageMapper;
import com.platform.machinelearningplatform.service.LoginMessage;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.service.impl
 * @Author: EnMing Zhang
 * @CreateTime: 2022-11-13  12:55
 * @Description: TODO
 * @Version: 1.0
 */
@Service
@Slf4j
@PropertySource({"classpath:application.yml"})
public class UserDetailServiceImpl extends ServiceImpl<StudentMessageMapper, StudentMessage> implements UserDetailsService {
    @Value("${id:123}")
    private Long id;
    @Value("${account:root}")
    private String account;
    @Value("${password:123456}")
    private String password;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username!=null&&username.equals(account)){
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            return new LoginMessage(StudentMessage.builder().account(account).password(bCryptPasswordEncoder.encode(password)).id(id).build(),account,password);
        }
        LambdaQueryWrapper<StudentMessage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudentMessage::getAccount,username);
        List<StudentMessage> list = list(wrapper);
        if (list.isEmpty())
            return new LoginMessage(new StudentMessage(),account,password);
        return new LoginMessage(list.get(0),account,password);
    }
}
