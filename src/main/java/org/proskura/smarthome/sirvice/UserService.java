package org.proskura.smarthome.sirvice;

import org.proskura.smarthome.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserService {
    UserEntity findUserByUsername (String username);
}
