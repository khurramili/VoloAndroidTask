package com.volo.voloandroidtask.di

import com.volo.voloandroidtask.services.drone.DroneRepository
import com.volo.voloandroidtask.services.room.RoomRepository
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
    fun provideDroneRepository(): DroneRepository {
        return DroneRepository()
    }

    @Singleton
    @Provides
    fun provideRoomRepository(): RoomRepository {
        return RoomRepository()
    }
}