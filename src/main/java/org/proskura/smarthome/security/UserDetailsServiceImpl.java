package org.proskura.smarthome.security;

import org.proskura.smarthome.domain.CredentialsEntity;
import org.proskura.smarthome.repository.CredentialRepository;
import org.proskura.smarthome.security.token.TokenPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

//Обязательный класс, который создает классы имплементированные от UserDetails. В нашем случае TokenPrincipal
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    private CredentialRepository credentialRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        CredentialsEntity credential = credentialRepository.findByUserEntity_Username(s)
                .orElseThrow(() -> new UsernameNotFoundException("User not Found"));

        TokenPrincipal tokenPrincipal = new TokenPrincipal();
        tokenPrincipal.setId(credential.getUserEntity().getId());
        tokenPrincipal.setUsername(credential.getUserEntity().getUsername());
        tokenPrincipal.setRole(credential.getUserEntity().getRole());
        tokenPrincipal.setPassword(credential.getPassword());

        logger.info(String.format("User with name %s is logged at %s", tokenPrincipal.getUsername(), LocalDateTime.now()));

        return tokenPrincipal;
    }

}
