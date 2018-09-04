package org.proskura.smarthome.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class AuthorisationToken extends UsernamePasswordAuthenticationToken {


    private AuthorisationToken(String token) {
        super(token, null);
    }

    public static AuthorisationToken of (String token) {
        return new AuthorisationToken(token);
    }

    public String getToken() {
        return (String) getPrincipal();
    }



}
