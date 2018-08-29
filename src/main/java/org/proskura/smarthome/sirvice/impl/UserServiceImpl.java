package org.proskura.smarthome.sirvice.impl;

import org.proskura.smarthome.domain.UserEntity;
import org.proskura.smarthome.repository.UserRepository;
import org.proskura.smarthome.sirvice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
