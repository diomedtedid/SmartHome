package org.proskura.smarthome.repository;

import org.proskura.smarthome.domain.CredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialRepository extends JpaRepository<CredentialsEntity, Long> {
    Optional<CredentialsEntity> findByUserEntity_Username (String username);
}
