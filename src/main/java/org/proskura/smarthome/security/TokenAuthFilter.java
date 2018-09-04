package org.proskura.smarthome.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class TokenAuthFilter extends AbstractAuthenticationProcessingFilter {
    public static final String AUTHORIZATION_TYPE = "Bearer";

    public TokenAuthFilter(@Autowired AuthenticationManager authenticationManager) {
        super("/**");
        setAuthenticationManager(authenticationManager);
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        return super.requiresAuthentication(request, response)
                && StringUtils.startsWithIgnoreCase(authHeader, AUTHORIZATION_TYPE);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        String token = extractToken(authHeader);


        AuthorisationToken authorisationToken = AuthorisationToken.of(token);

        return getAuthenticationManager().authenticate(authorisationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }

    private String extractToken(String authHeader) {
        return authHeader.substring(AUTHORIZATION_TYPE.length() + 1);
    }

}
