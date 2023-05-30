package com.volo.voloandroidtask.services.room

class RoomRepository {
    fun createRoom(height: Int, width: Int, depth: Int): Room {
        return Room(height, width, depth)
    }
}
