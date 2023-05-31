package com.volo.voloandroidtask.model

import junit.framework.TestCase.assertEquals
import org.junit.Test

class RoomTest {

    @Test
    fun `test data class properties`() {
        // Create an instance of the data class
        val room = Room(10, 20)

        // Assert the properties are set correctly
        assertEquals(10, room.width)
        assertEquals(20, room.height)
    }

    @Test
    fun `test data class equality`() {
        // Create two instances of the data class with the same property values
        val room1 = Room(5, 10)
        val room2 = Room(5, 10)

        // Assert that the two instances are equal
        assertEquals(room1, room2)
    }
}