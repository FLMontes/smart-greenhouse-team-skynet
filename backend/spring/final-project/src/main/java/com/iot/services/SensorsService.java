package com.iot.services;

import com.iot.models.dto.CreateSensorDto;
import com.iot.models.dto.SensorResponseDto;
import com.iot.models.entities.SensorReading;
import com.iot.repositories.SensorsRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Business logic for ingesting and listing sensor readings.
 *
 * <p>Persists via {@link SensorsRepository} and returns {@link SensorResponseDto} only — never JPA entities.
 */
@Service
public class SensorsService {

	private final SensorsRepository sensorsRepository;

	public SensorsService(SensorsRepository sensorsRepository) {
		this.sensorsRepository = sensorsRepository;
	}

	@Transactional
	public SensorResponseDto create(CreateSensorDto dto) {
		SensorReading entity = SensorReading.builder()
				.sensorId(dto.getSensorId())
				.temperature(dto.getTemperature())
				.humidity(dto.getHumidity())
				.build();
		SensorReading saved = sensorsRepository.save(entity);
		return SensorResponseDto.fromEntity(saved);
	}

	@Transactional(readOnly = true)
	public List<SensorResponseDto> findAll() {
		return sensorsRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).stream()
				.map(SensorResponseDto::fromEntity)
				.toList();
	}
}
