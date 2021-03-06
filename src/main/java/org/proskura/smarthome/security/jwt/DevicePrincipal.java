package org.proskura.smarthome.security.jwt;

import lombok.Data;
import org.proskura.smarthome.domain.UserRole;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Data
public class DevicePrincipal implements UserDetails{
    private String id;
    private UserRole role;
    private Set<String> units;


    @Override
    public Collection<UserRole> getAuthorities() {
        return Collections.singletonList(role);
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return this.id;
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
