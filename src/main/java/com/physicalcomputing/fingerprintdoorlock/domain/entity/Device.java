package com.physicalcomputing.fingerprintdoorlock.domain.entity;

import com.physicalcomputing.fingerprintdoorlock.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "device")
@Builder
public class Device extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private long deviceId;

    @Column(name = "name")
    private String name;

    @Column(name = "mac_address")
    private String macAddress;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
}
