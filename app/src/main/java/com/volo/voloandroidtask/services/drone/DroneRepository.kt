package com.volo.voloandroidtask.services.drone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.volo.voloandroidtask.model.Drone
import javax.inject.Inject

class DroneRepository @Inject constructor(): DroneListeners{

    var drone: MutableLiveData<Drone?> = MutableLiveData(Drone(0f,0f,0f))

    override fun moveDrone(x: Float, y: Float, z: Float) {
        val updatedDrone = Drone(x, y, z)
        drone.value = updatedDrone
    }
    override fun getCurrentDrone(): LiveData<Drone?> {
        return drone
    }
    override fun setDroneCurrentPoistion(x: Float, y: Float, z: Float) {
        val updatedDrone = Drone(x, y, z)
        drone.value = updatedDrone
    }
}