package com.iot.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alert {
    private Long id;
    private String type;
    private String message;
    private String severity;
    private boolean active;
    private Integer relatedMeasurementId;
    private LocalDateTime timestamp;
}