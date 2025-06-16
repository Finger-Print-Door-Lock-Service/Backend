package com.physicalcomputing.fingerprintdoorlock.service.LogService;

import com.physicalcomputing.fingerprintdoorlock.domain.dto.LogDTO;

import java.util.List;

public interface LogService {
    void saveLog(LogDTO logDTO);
    List<LogDTO> getLogsByDeviceId(int deviceIdForMqtt);
}
