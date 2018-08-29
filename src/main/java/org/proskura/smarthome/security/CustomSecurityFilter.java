package org.proskura.smarthome.security;

import org.proskura.smarthome.sirvice.SecurityService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;

public class CustomSecurityFilter extends GenericFilterBean {
    private final SecurityService securityService;

    public CustomSecurityFilter(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Authentication authentication = null;
        String token = getTokenFromRequest(servletRequest);
        if (Objects.nonNull(token)) {
           authentication = securityService.getAuthFromToken(token).orElseThrow(RuntimeException::new);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getTokenFromRequest (ServletRequest servletRequest) {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        return httpServletRequest.getHeader("header");
    }
}
