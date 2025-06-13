package com.physicalcomputing.fingerprintdoorlock.domain.entity;

import com.physicalcomputing.fingerprintdoorlock.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;
}
