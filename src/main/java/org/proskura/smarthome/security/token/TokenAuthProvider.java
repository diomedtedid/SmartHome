package org.proskura.smarthome.security.token;

import org.proskura.smarthome.domain.TokenEntity;
import org.proskura.smarthome.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthProvider implements AuthenticationProvider {

    private TokenRepository tokenRepository;

    private UserDetailsService userDetailsService;

    public TokenAuthProvider(
            @Autowired TokenRepository tokenRepository,
            @Autowired UserDetailsService userDetailsService) {

        this.tokenRepository = tokenRepository;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AuthorisationToken auth = (AuthorisationToken) authentication;
        TokenEntity tokenEntity = tokenRepository.findByToken(auth.getToken()).orElseThrow(() -> new BadCredentialsException("Bad token"));
        TokenPrincipal tokenPrincipal = (TokenPrincipal) userDetailsService.loadUserByUsername(tokenEntity.getUser().getUsername());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(tokenPrincipal, null, tokenPrincipal.getAuthorities());

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthorisationToken.class.isAssignableFrom(authentication);
    }
}
