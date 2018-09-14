package org.proskura.smarthome.repository;

import org.proskura.smarthome.domain.UnitStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UnitStateRepository extends JpaRepository<UnitStateEntity, Long>, JpaSpecificationExecutor<UnitStateEntity> {
}
