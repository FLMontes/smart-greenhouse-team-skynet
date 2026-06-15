package com.iot.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}