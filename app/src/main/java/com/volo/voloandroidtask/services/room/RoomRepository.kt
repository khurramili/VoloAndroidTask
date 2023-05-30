package com.volo.voloandroidtask.services.room

class RoomRepository {
    fun createRoom(width: Int, height: Int): Room {
        return Room(width,height)
    }
}
