package com.iot.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlgorithmDefinition {
    private String name;
    private String description;
    private String inputSource;
    private String inputData;
    private String outputName;
    private String outputUnit;
}