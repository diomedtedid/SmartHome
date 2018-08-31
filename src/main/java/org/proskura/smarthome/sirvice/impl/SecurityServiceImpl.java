package org.proskura.smarthome.sirvice.impl;

import org.proskura.smarthome.domain.CredentialsEntity;
import org.proskura.smarthome.domain.TokenEntity;
import org.proskura.smarthome.domain.TokenType;
import org.proskura.smarthome.repository.CredentialRepository;
import org.proskura.smarthome.repository.TokenRepository;
import org.proskura.smarthome.security.AuthenticationToken;
import org.proskura.smarthome.security.Principal;
import org.proskura.smarthome.sirvice.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private CredentialRepository credentialRepository;


    @Override
    public Optional<Authentication> getAuthFromToken(String token) {
        TokenEntity tokenEntity = tokenRepository.findByToken(token);
        Principal principal = (Principal) userDetailsService.loadUserByUsername(tokenEntity.getUser().getUsername());
        Authentication authentication = new AuthenticationToken(principal, tokenEntity.getToken());
        return Optional.ofNullable(authentication);

    }

    @Override
    public String getToken(String username, String password) {
        String token;
        CredentialsEntity credentialsEntity = credentialRepository.findByUserEntity_Username(username).orElseThrow(RuntimeException::new);

        if (credentialsEntity.getPassword().equals(password)) {
            token = UUID.randomUUID().toString();
            TokenEntity tokenEntity = new TokenEntity();
            tokenEntity.setType(TokenType.ACCESS_TOKEN);
            tokenEntity.setUser(credentialsEntity.getUserEntity());
            tokenEntity.setToken(token);
            tokenEntity.setExpirationDate(LocalDateTime.now().plusDays(14));
            tokenRepository.save(tokenEntity);
        } else {
            throw  new RuntimeException();
        }

        return token;
    }
}
