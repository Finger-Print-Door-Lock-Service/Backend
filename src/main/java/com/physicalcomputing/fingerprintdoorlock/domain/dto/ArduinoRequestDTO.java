package com.physicalcomputing.fingerprintdoorlock.domain.dto;

import lombok.*;

public class ArduinoRequestDTO {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DeviceRegisterDTO {
        private int deviceNumForMqtt;
    }
}
