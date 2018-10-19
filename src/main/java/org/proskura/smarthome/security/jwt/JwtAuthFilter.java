package org.proskura.smarthome.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static org.proskura.smarthome.security.SecurityConstant.JWT_TOKEN_PREFIX;

public class JwtAuthFilter extends AbstractAuthenticationProcessingFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthFilter.class);


    public JwtAuthFilter(@Autowired AuthenticationManager authenticationManager) {
        super("/device");
        setAuthenticationManager(authenticationManager);
    }

    //Метод проверяет, должен ли фильтр попытаться выполнить аутентификацию, или передать ее выполнение дальше
    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        return super.requiresAuthentication(request, response);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(authHeader)) {
            authHeader = "anonymous";
        }
        LOGGER.info("Authentication Authorization header extracted '{}'", authHeader);

        String token = extractToken(authHeader);

        JwtAuthorisationToken authRequest = JwtAuthorisationToken.of(token);
        Authentication authResult = getAuthenticationManager()
                    .authenticate(authRequest);

        return authResult;

        
    }

    private String extractToken(String authHeader) {
        return authHeader.substring(JWT_TOKEN_PREFIX.length() + 1);
    }

    /**
     * Make sure the rest of the filterchain is satisfied
     *
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);

        // As this authentication is in HTTP header, after success we need to continue the request normally
        // and return the response as if the resource was not secured at all
        chain.doFilter(request, response);
    }
}
