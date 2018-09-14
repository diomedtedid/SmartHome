package org.proskura.smarthome.repository;

import org.proskura.smarthome.domain.DeviceEntity;
import org.proskura.smarthome.domain.UnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UnitRepository extends JpaRepository<UnitEntity, Long> {
    Optional<UnitEntity> findByUnitNameAndAndDevice (String unitName, DeviceEntity deviceEntity);
    List<UnitEntity> findByDevice (DeviceEntity deviceEntity);
}
