import { Module } from '@nestjs/common';
import { SensorsService } from './sensors.service';
import { RepositoriesModule } from '../repositories/repositories.module';

@Module({
  imports: [RepositoriesModule],
  providers: [SensorsService],
  exports: [SensorsService],
})
export class ServicesModule {}