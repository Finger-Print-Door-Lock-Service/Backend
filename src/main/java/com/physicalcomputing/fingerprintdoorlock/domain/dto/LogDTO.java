package com.physicalcomputing.fingerprintdoorlock.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogDTO {
    int deviceIdForMqtt;
    int memberIdOnDevice;
    Boolean result;
    @JsonFormat(pattern = "yyyy-MM-dd'T'H:mm:ss.SSS")
    LocalDateTime timestamp;
}
