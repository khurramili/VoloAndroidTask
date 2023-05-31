package com.volo.voloandroidtask.services.room

import com.volo.voloandroidtask.model.Room

class RoomRepository {
    fun createRoom(width: Int, height: Int): Room {
        return Room(width,height)
    }
}
