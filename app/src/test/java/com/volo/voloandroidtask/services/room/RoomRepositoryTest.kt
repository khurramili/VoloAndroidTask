package com.volo.voloandroidtask.services.room

import org.junit.Assert.assertEquals
import org.junit.Test

class RoomRepositoryTest {
    @Test
    fun `test createRoom method`() {
        // Arrange
        val repository = RoomRepository()
        val expectedHeight = 10
        val expectedWidth = 12

        // Act
        val room = repository.createRoom(expectedWidth, expectedHeight)

        // Assert
        assertEquals(expectedHeight, room.height)
        assertEquals(expectedWidth, room.width)
    }
}
