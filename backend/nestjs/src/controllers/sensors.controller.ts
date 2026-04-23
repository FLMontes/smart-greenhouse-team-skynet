import { Controller, Post, Body, Get } from '@nestjs/common';
import { SensorsService } from '../services/sensors.service';
import { CreateSensorDto } from '../models/dto/create-sensor.dto';

@Controller('sensors')
export class SensorsController {
  constructor(private readonly sensorsService: SensorsService) {}

  @Post()
  async create(@Body() dto: CreateSensorDto) {
    return this.sensorsService.create(dto);
  }
}