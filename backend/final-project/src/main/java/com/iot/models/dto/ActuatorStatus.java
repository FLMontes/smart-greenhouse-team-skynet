package com.iot.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActuatorStatus {
    private boolean fanStatus;
    private boolean buzzerStatus;
    private boolean motorStatus;
    private boolean resistorStatus;
    private String rgbColorCommand;
    private int ledIntensityCommand;
    private Integer basedOnMeasurementId;
    private LocalDateTime timestamp;
    private boolean alarmMuted;
}