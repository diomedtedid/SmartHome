package org.proskura.smarthome.repository;

import org.proskura.smarthome.domain.TokenEntity;
import org.proskura.smarthome.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    Optional<TokenEntity> findByUser_Id (Long userId);
    Optional<TokenEntity> findByToken (String token);
}
