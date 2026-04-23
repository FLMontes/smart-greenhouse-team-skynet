package com.iot.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request body for POST /sensors (ESP32 / clients).
 *
 * <p>Fields: {@code sensorId}, {@code temperature}, {@code humidity}. JSON uses camelCase; Jackson
 * maps to these Java properties.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSensorDto {

	@NotBlank private String sensorId;

	@NotNull private Double temperature;

	@NotNull private Double humidity;
}
