package com.iot.models.dto;

import com.iot.models.entities.SensorReading;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response shape for GET/POST /sensors (camelCase JSON).
 *
 * <p>Use {@link #fromEntity(SensorReading)} to map from persistence — do not expose entities on the wire.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorResponseDto {

	private Integer id;
	private String sensorId;
	private Double temperature;
	private Double humidity;
	private Instant createdAt;

	public static SensorResponseDto fromEntity(SensorReading entity) {
		return SensorResponseDto.builder()
				.id(entity.getId())
				.sensorId(entity.getSensorId())
				.temperature(entity.getTemperature())
				.humidity(entity.getHumidity())
				.createdAt(entity.getCreatedAt())
				.build();
	}
}
