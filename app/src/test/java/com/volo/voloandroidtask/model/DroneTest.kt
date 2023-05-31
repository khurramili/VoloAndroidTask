package com.volo.voloandroidtask.model

// Test class
import org.junit.Test
import org.junit.Assert.assertEquals

class DroneTest {

    @Test
    fun `test data class properties`() {
        // Create an instance of the data class
        val drone = Drone(10.0f, 20.0f, 30.0f)

        // Assert the properties are set correctly
        assertEquals(10.0f, drone.x)
        assertEquals(20.0f, drone.y)
        assertEquals(30.0f, drone.z)
    }

    @Test
    fun `test data class equality`() {
        // Create two instances of the data class with the same property values
        val drone1 = Drone(5.0f, 10.0f, 15.0f)
        val drone2 = Drone(5.0f, 10.0f, 15.0f)

        // Assert that the two instances are equal
        assertEquals(drone1, drone2)
    }

    @Test
    fun `test data class update`() {
        // Create an instance of the data class
        val drone = Drone(0.0f, 0.0f, 0.0f)

        // Update the property values
        drone.x = 5.0f
        drone.y = 10.0f
        drone.z = 15.0f

        // Assert the updated values
        assertEquals(5.0f, drone.x)
        assertEquals(10.0f, drone.y)
        assertEquals(15.0f, drone.z)
    }
}