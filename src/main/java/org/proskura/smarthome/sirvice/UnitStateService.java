package org.proskura.smarthome.sirvice;

import org.proskura.smarthome.domain.UnitStateEntity;

import java.util.List;

public interface UnitStateService {
    List<UnitStateEntity> saveUnitState (List<UnitStateEntity> unitStates);
    UnitStateEntity saveUnitState (UnitStateEntity unitState);
}
