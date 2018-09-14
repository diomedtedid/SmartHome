package org.proskura.smarthome.sirvice;

import org.proskura.smarthome.domain.DeviceEntity;
import org.proskura.smarthome.domain.UnitEntity;

import java.util.List;
import java.util.Optional;

public interface UnitService {
    UnitEntity getUnitByNameAndDevice (String unitName, DeviceEntity deviceEntity);
    List<UnitEntity> getAllUnitsByDevice (DeviceEntity deviceEntity);
    List<UnitEntity> createUnit (List<UnitEntity> unitEntities);
}
