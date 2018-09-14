package org.proskura.smarthome.domain;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "unit_state")
@Data
public class UnitStateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "unit_id", referencedColumnName = "deviceId", nullable = false)
    private UnitEntity unit;
    @Column
    private String value;
    @Column
    private String action;
    @Column
    private LocalDateTime time;
}
