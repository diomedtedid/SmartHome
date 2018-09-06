package org.proskura.smarthome.domain;

import org.springframework.security.core.GrantedAuthority;

//имплементируем GrantedAuthority для задания ролей пользователя
public enum UserRole implements GrantedAuthority {
    USER,
    DEVICE;

    @Override
    public String getAuthority() {
        return "ROLE_" + name() ;
    }
}
