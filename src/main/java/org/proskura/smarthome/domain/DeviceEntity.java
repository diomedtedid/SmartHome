package org.proskura.smarthome.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "device")
@Data
public class DeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String deviceId;
}
