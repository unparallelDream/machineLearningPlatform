package com.platform.machinelearningplatform.service;

import com.platform.machinelearningplatform.entity.StudentMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.service
 * @Author: EnMing Zhang
 * @CreateTime: 2022-11-13  11:45
 * @Description: TODO
 * @Version: 1.0
 */
//@AllArgsConstructor
@Data
@AllArgsConstructor
@Slf4j
@PropertySource({"classpath:application.yml"})
public class LoginMessage implements UserDetails {
    public LoginMessage(StudentMessage studentMessage) {
        this.studentMessage = studentMessage;
    }
    private StudentMessage studentMessage;
    private String account;
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        ArrayList<GrantedAuthority> strings = new ArrayList<>();
        if (studentMessage.getAccount().equals(account)&&encoder.matches(password,studentMessage.getPassword())){
            strings.add((GrantedAuthority) () -> "ROLE_root");
            return strings;
        }
        return null;
    }

    @Override
    public String getPassword() {
        return studentMessage.getPassword();
    }

    @Override
    public String getUsername() {
        return studentMessage.getAccount();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
