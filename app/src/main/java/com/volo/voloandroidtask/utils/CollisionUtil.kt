package com.volo.voloandroidtask.utils

import com.volo.voloandroidtask.constants.Constants
import com.volo.voloandroidtask.model.Room

object CollisionUtil {

   fun checkCollision(posX: Float, posY: Float, room: Room): Boolean {
        return if (posX < 100 || posX > (room.width - Constants.collisionAvoidDistance)) {
            true
        } else posY < 100 || posY > (room.height - Constants.collisionAvoidDistance)
    }
}