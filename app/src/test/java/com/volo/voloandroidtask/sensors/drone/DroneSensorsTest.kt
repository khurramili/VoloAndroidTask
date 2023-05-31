package com.volo.voloandroidtask.sensors.drone

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.volo.voloandroidtask.model.Drone
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Before


class DroneSensorsTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: DroneSensors

    @Before
    fun setup() {
        repository = DroneSensors()
    }

    @Test
    fun `test moveDrone method`() {
        // Initial state
        val initialDrone = repository.getCurrentDrone().value
        assertEquals(Drone(0f, 0f, 0f), initialDrone)

        // Move the drone
        repository.moveDrone(10f, 20f, 30f)

        // Check the updated drone position
        val updatedDrone = repository.getCurrentDrone().value
        assertEquals(Drone(10f, 20f, 30f), updatedDrone)
    }

    @Test
    fun `test getCurrentDrone method`() {
        // Initial state
        val initialDrone = repository.getCurrentDrone().value
        assertEquals(Drone(0f, 0f, 0f), initialDrone)

        // Set a new drone position
        repository.setDroneCurrentPoistion(10f, 20f, 30f)

        // Check the updated drone position
        val updatedDrone = repository.getCurrentDrone().value
        assertEquals(Drone(10f, 20f, 30f), updatedDrone)
    }

    @Test
    fun `test setDroneCurrentPoistion method`() {
        // Initial state
        val initialDrone = repository.getCurrentDrone().value
        assertEquals(Drone(0f, 0f, 0f), initialDrone)

        // Set a new drone position
        repository.setDroneCurrentPoistion(10f, 20f, 30f)

        // Check the updated drone position
        val updatedDrone = repository.getCurrentDrone().value
        assertEquals(Drone(10f, 20f, 30f), updatedDrone)
    }
}


