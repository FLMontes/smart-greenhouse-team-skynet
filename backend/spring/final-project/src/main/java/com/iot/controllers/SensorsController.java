package com.iot.controllers;

import com.iot.models.dto.CreateSensorDto;
import com.iot.models.dto.SensorResponseDto;
import com.iot.services.SensorsService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API for {@code /sensors}. Delegates to {@link SensorsService}; no business rules in this class.
 */
@RestController
@RequestMapping("/sensors")
public class SensorsController {

	private final SensorsService sensorsService;

	public SensorsController(SensorsService sensorsService) {
		this.sensorsService = sensorsService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SensorResponseDto> create(@Valid @RequestBody CreateSensorDto dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(sensorsService.create(dto));
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SensorResponseDto> list() {
		return sensorsService.findAll();
	}
}
