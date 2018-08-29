package org.proskura.smarthome.sirvice.impl;

import org.proskura.smarthome.security.Principal;
import org.proskura.smarthome.security.UserAuthentication;
import org.proskura.smarthome.sirvice.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    public Optional<Authentication> getAuthFromToken(String token) {
        Authentication authentication = null;
        if (token.equals("hui")) {
            Principal principal = (Principal) userDetailsService.loadUserByUsername("user");
            authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        }
        return Optional.ofNullable(authentication);

    }
}
