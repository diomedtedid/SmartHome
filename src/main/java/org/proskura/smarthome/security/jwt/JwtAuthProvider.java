package org.proskura.smarthome.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthProvider  implements AuthenticationProvider {

    private final JwtService jwtService;

    public JwtAuthProvider(@Autowired JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthorisationToken token = (JwtAuthorisationToken) authentication;
        DevicePrincipal devicePrincipal = jwtService.parseUserDetails(token.getToken());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(devicePrincipal, null, devicePrincipal.getAuthorities());

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthorisationToken.class.isAssignableFrom(authentication);
    }
}
