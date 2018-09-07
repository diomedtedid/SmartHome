package org.proskura.smarthome.sirvice;

import org.proskura.smarthome.dto.UsernamePasswordDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;

public interface AuthService {
    default UserDetails getAuthPrincipal() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    String generateAccessToken (UsernamePasswordDto usernamePassword);
    String generateDeviceToken ();
}
