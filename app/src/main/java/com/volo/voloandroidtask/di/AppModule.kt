package com.volo.voloandroidtask.di

import com.volo.voloandroidtask.sensors.drone.DroneSensor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDroneRepository(): DroneSensor {
        return DroneSensor()
    }

}