package org.proskura.smarthome.repository;

import org.proskura.smarthome.domain.TokenEntity;
import org.proskura.smarthome.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    TokenEntity findByUser_Id (Long userId);
    TokenEntity findByToken (String token);
}
