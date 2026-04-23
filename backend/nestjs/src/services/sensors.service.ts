import { Injectable } from '@nestjs/common';
import { SensorsRepository } from '../repositories/sensors.repository';
import { CreateSensorDto } from '../models/dto/create-sensor.dto';

@Injectable()
export class SensorsService {
  constructor(private readonly sensorsRepository: SensorsRepository) {}

  async create(dto: CreateSensorDto) {
    return this.sensorsRepository.save(dto);
  }
}