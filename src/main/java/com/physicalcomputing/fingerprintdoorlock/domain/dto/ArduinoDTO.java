package com.physicalcomputing.fingerprintdoorlock.domain.dto;

import lombok.*;

public class ArduinoDTO {

    // 검증 자체는 esp32에서 해줘야됨, 안그러면 복잡해지고 머리 아픔
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class  DeviceRegisterDTO{
        private  String macAddress;
        private  String name;
        private  String email;
        private  String password;
        private int deviceIdForMqtt;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MemberRegisterDTO{
        private String name;
        private String email;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MemberInfoOnDeviceDTO{
        private String name;
        private String email;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MemberIdOnDeviceDTO{
        private int memberIdOnDevice;
        private int deviceIdForMqtt;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MemberInfoCombinedDTO{
        private String name;
        private String email;
        private int memberIdOnDevice;
    }
}
