package com.iot.services;

import com.iot.repositories.SensorsRepository;
import org.springframework.stereotype.Service;

/**
 * Placeholder for analytics / thresholds / anomaly detection using {@link SensorsRepository}.
 *
 * <p>Students implement algorithms here; keep HTTP and DTO mapping out of this layer.
 */
@Service
public class ProcessingService {

	private final SensorsRepository sensorsRepository;

	public ProcessingService(SensorsRepository sensorsRepository) {
		this.sensorsRepository = sensorsRepository;
	}
}
