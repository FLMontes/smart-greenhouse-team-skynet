package com.iot.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlgorithmResult {
    private String algorithm;
    private String description;
    private String inputSource;
    private String inputData;
    private String outputName;
    private float value;
    private String unit;
    private LocalDateTime timestamp;
}