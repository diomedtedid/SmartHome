package org.proskura.smarthome.repository;

import org.proskura.smarthome.domain.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {
    Optional<DeviceEntity> findByDeviceId (String deviceId);
}
