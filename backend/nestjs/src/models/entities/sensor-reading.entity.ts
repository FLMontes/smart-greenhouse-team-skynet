import { Entity, Column, PrimaryGeneratedColumn, CreateDateColumn } from 'typeorm';

// This entity represents a sensor reading in the database, this entity is an example and you can remove it if you don't need it. You can create other entities as needed for your application.
@Entity('sensor_readings')
export class SensorReading {
  @PrimaryGeneratedColumn('increment')
  id: number;

  @Column()
  sensorId: string;

  @Column('float')
  temperature: number;

  @Column('float')
  humidity: number;

  @CreateDateColumn()
  createdAt: Date;
}