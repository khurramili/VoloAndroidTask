package com.volo.voloandroidtask.services.gyroscope

interface MovementListener {

    fun updatedAxis(x:Float,y:Float,z:Float)
}