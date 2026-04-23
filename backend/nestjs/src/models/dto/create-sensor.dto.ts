import { IsString, IsNumber } from 'class-validator';

export class CreateSensorDto {
  @IsString()
  sensorId: string;

  @IsNumber()
  temperature: number;

  @IsNumber()
  humidity: number;
}