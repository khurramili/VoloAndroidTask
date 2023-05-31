package com.volo.voloandroidtask.sensors.gyroscope

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class GyroscopeSensorTest {

    private lateinit var gyroscopeSensor: GyroscopeSensor
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

        gyroscopeSensor = GyroscopeSensor(context, movementListener)
        gyroscopeSensor.startListening()
    }


    @Test
    fun testStartListening() {
        // Prepare
        Mockito.`when`(gyroscopeSensor.isListening).thenReturn(false)

        // Act
        gyroscopeSensor.startListening()

        // Assert
        assert(gyroscopeSensor.isListening)
    }

    @Test
    fun testStopListening() {
        // Prepare
        Mockito.`when`(gyroscopeSensor.isListening).thenReturn(true)

        // Act
        gyroscopeSensor.stopListening()

        // Assert
        assert(!gyroscopeSensor.isListening)
    }

}