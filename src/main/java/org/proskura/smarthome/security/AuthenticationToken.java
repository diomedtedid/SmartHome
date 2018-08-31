package org.proskura.smarthome.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class AuthenticationToken extends AbstractAuthenticationToken {
    private String token;
    private Principal principal;


    public AuthenticationToken(Principal principal, String token) {
        super(principal.getAuthorities());
        this.principal = principal;
        this.token = token;
        super.setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return this.token;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
