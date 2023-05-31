package com.volo.voloandroidtask.services.gyroscope

interface MovementListener {
    fun updatedAxis(x: Float, y: Float, z: Float)
    fun rotateRight(z: Float)
    fun rotateLeft(z: Float)
}