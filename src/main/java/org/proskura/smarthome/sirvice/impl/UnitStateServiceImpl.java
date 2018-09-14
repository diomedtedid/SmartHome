package org.proskura.smarthome.sirvice.impl;

import org.proskura.smarthome.domain.UnitStateEntity;
import org.proskura.smarthome.repository.UnitStateRepository;
import org.proskura.smarthome.sirvice.UnitStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnitStateServiceImpl implements UnitStateService{

    @Autowired
    private UnitStateRepository unitStateRepository;

    @Override
    public List<UnitStateEntity> saveUnitState(List<UnitStateEntity> unitStates) {
        return unitStates.stream().map(this::saveUnitState).collect(Collectors.toList());
    }

    @Override
    public UnitStateEntity saveUnitState(UnitStateEntity unitState) {
        return unitStateRepository.save(unitState);
    }
}
