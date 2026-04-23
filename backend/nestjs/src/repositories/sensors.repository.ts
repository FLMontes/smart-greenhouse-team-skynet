import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { SensorReading } from '../models/entities/sensor-reading.entity';
import { CreateSensorDto } from '../models/dto/create-sensor.dto';

@Injectable()
export class SensorsRepository {
  constructor(
    @InjectRepository(SensorReading)
    private readonly repo: Repository<SensorReading>,
  ) {}

  async save(dto: CreateSensorDto): Promise<SensorReading> {
    const entity = this.repo.create(dto);
    return this.repo.save(entity);
  }
}