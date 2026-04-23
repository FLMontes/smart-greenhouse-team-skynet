import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { SensorReading } from '../models/entities/sensor-reading.entity';
import { SensorsRepository } from './sensors.repository';

@Module({
  imports: [TypeOrmModule.forFeature([SensorReading])], 
  providers: [SensorsRepository ],
  exports: [SensorsRepository],
})
export class RepositoriesModule {}