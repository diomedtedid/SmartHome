package org.proskura.smarthome.security;

import org.proskura.smarthome.domain.CredentialsEntity;
import org.proskura.smarthome.domain.UserEntity;
import org.proskura.smarthome.domain.UserRole;
import org.proskura.smarthome.repository.CredentialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

//Обязательный класс, который создает классы имплементированные от UserDetails. В нашем случае Principal
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    private CredentialRepository credentialRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        CredentialsEntity credential = credentialRepository.findByUserEntity_Username(s)
                .orElseThrow(() -> new UsernameNotFoundException("User not Found"));

        Principal principal = new Principal();
        principal.setId(credential.getUserEntity().getId());
        principal.setUsername(credential.getUserEntity().getUsername());
        principal.setRole(credential.getUserEntity().getRole());
        principal.setPassword(credential.getPassword());

        logger.info(String.format("User with name %s is logged at %s", principal.getUsername(), LocalDateTime.now()));

        return principal;
    }

}
