package org.proskura.smarthome.sirvice.impl;

import org.proskura.smarthome.domain.CredentialsEntity;
import org.proskura.smarthome.domain.TokenType;
import org.proskura.smarthome.domain.UserEntity;
import org.proskura.smarthome.dto.UsernamePasswordDto;
import org.proskura.smarthome.repository.CredentialRepository;
import org.proskura.smarthome.sirvice.AuthService;
import org.proskura.smarthome.sirvice.DeviceService;
import org.proskura.smarthome.sirvice.TokenService;
import org.proskura.smarthome.sirvice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private CredentialRepository credentialRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private DeviceService deviceService;


    @Override
    public String generateAccessToken(UsernamePasswordDto usernamePassword) {
        CredentialsEntity credentialsEntity = credentialRepository
                .findByUserEntity_Username(usernamePassword.getUsername())
                .orElseThrow(() -> new RuntimeException ("User not found"));

        validateUserPassword (credentialsEntity.getPassword(), usernamePassword.getPassword());

        UserEntity user = credentialsEntity.getUserEntity();


        return tokenService.createUserToken(user, TokenType.ACCESS_TOKEN);
    }

    @Override
    public String generateDeviceToken(Map<String, Object> body) {
        return tokenService.createJwtToken();
    }

    private void validateUserPassword (String incomingPassword, String storedPassword) {
        if (!storedPassword.equals(incomingPassword)) throw new BadCredentialsException("Bad Credentials");
    }
}
