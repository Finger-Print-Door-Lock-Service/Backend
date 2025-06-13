package com.physicalcomputing.fingerprintdoorlock.repository;

import com.physicalcomputing.fingerprintdoorlock.domain.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
}
