package com.volo.voloandroidtask.di

import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class AppModuleTest {

    @Test
    fun provideDroneRepository_shouldReturnNonNullInstance() {
        // Arrange
        val appModule = AppModule

        // Act
        val droneRepository = appModule.provideDroneRepository()

        // Assert
        assertNotNull(droneRepository)
    }

}