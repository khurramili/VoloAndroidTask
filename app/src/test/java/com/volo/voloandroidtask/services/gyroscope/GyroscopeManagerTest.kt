package com.volo.voloandroidtask.services.gyroscope

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class GyroscopeManagerTest {

    private lateinit var gyroscopeManager: GyroscopeManager
    private lateinit var context: Context
    private lateinit var sensorManager: SensorManager
    private lateinit var gyroscope: Sensor
    private lateinit var movementListener: MovementListener

    @Before
    fun setup() {
        context = Mockito.mock(Context::class.java)
        sensorManager = Mockito.mock(SensorManager::class.java)
        gyroscope = Mockito.mock(Sensor::class.java)
        movementListener = Mockito.mock(MovementListener::class.java)

        Mockito.`when`(context.getSystemService(Context.SENSOR_SERVICE)).thenReturn(sensorManager)
        Mockito.`when`(sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)).thenReturn(gyroscope)

        gyroscopeManager = GyroscopeManager(context, movementListener)
        gyroscopeManager.startListening()
    }


    @Test
    fun testStartListening() {
        // Prepare
        Mockito.`when`(gyroscopeManager.isListening).thenReturn(false)

        // Act
        gyroscopeManager.startListening()

        // Assert
        assert(gyroscopeManager.isListening)
    }

    @Test
    fun testStopListening() {
        // Prepare
        Mockito.`when`(gyroscopeManager.isListening).thenReturn(true)

        // Act
        gyroscopeManager.stopListening()

        // Assert
        assert(!gyroscopeManager.isListening)
    }

}