package com.platform.machinelearningplatform.service;

import com.platform.machinelearningplatform.entity.StudentMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @BelongsProject: machineLearningPlatform
 * @BelongsPackage: com.platform.machinelearningplatform.service
 * @Author: EnMing Zhang
 * @CreateTime: 2022-11-13  11:45
 * @Description: TODO
 * @Version: 1.0
 */
@AllArgsConstructor
@Data
public class LoginMessage implements UserDetails {
    private StudentMessage studentMessage;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
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
