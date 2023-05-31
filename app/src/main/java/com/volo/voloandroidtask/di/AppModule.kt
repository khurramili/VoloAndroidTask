package com.volo.voloandroidtask.di

import com.volo.voloandroidtask.sensors.drone.DroneSensors
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
    fun provideDroneRepository(): DroneSensors {
        return DroneSensors()
    }

}