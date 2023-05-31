package com.volo.voloandroidtask.services.drone

import androidx.lifecycle.LiveData
import com.volo.voloandroidtask.model.Drone

interface DroneListeners {
    fun moveDrone(x: Float, y: Float, z: Float)
    fun getCurrentDrone(): LiveData<Drone?>
    fun setDroneCurrentPoistion(x: Float, y: Float, z: Float)
}