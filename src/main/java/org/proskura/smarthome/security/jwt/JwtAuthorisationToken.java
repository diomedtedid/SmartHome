package org.proskura.smarthome.security.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtAuthorisationToken  extends UsernamePasswordAuthenticationToken {

    private JwtAuthorisationToken(String jwtToken) {
        super(jwtToken, null);
    }

    public static JwtAuthorisationToken of (String token) {
        return new JwtAuthorisationToken(token);
    }

    public String getToken() {
        return (String) getPrincipal();
    }
}
