package com.volo.voloandroidtask.ui.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.volo.voloandroidtask.model.Drone
import com.volo.voloandroidtask.model.Room
import com.volo.voloandroidtask.sensors.drone.DroneSensors
import com.volo.voloandroidtask.sensors.gyroscope.MovementListener
import com.volo.voloandroidtask.constants.Constants
import com.volo.voloandroidtask.utils.CollisionUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val droneSensors: DroneSensors) :
    ViewModel() {

    val isDroneConnected: MutableLiveData<Boolean> = MutableLiveData(false)
    val isDroneFlying: MutableLiveData<Boolean> = MutableLiveData(false)
    val isCollided: MutableLiveData<Boolean> = MutableLiveData(false)

    var room: Room? = null
    var drone: LiveData<Drone?> = droneSensors.drone

    lateinit var movementListener: MovementListener

    fun intializeRoom(roomWidth: Int, roomHeight: Int) {
        room = Room(roomWidth, roomHeight)
    }

    fun initalizeDrone(droneX: Float, droneY: Float, droneZ: Float) {
        droneSensors.setDroneCurrentPoistion(droneX, droneY, droneZ)
    }

    fun moveDrone(x: Float, y: Float, z: Float) {
        val drone: Drone? = drone.value

        // Update the position
        val posX = drone!!.x + (x * Constants.thresholdX)
        val posY = drone.y + (y * Constants.thresholdY)
        val posZ = drone.z + (z * Constants.thresholdZ)

        if (room?.let { CollisionUtil.checkCollision(posX, posY, it) } == true) {
            isCollided.value = true
        } else {
            isCollided.value = false
            droneSensors.moveDrone(posX, posY, posZ)
        }
    }

    fun getRoomSize(): Room? {
        return room
    }

    fun moveRight() {
        val updatedRight = drone.value?.x?.plus(10)
        if (room?.let { CollisionUtil.checkCollision(updatedRight!!, drone.value?.y!!, it) } == true) {
            isCollided.value = true
        } else {
            isCollided.value = false
            updatedRight?.let { droneSensors.moveDrone(it, drone.value!!.y, drone.value!!.z) }
            movementListener.updatedAxis(0.1f, 0f, 0f)
        }
    }

    fun moveLeft() {
        val updatedLeft = drone.value?.x?.minus(10)
        if (room?.let { CollisionUtil.checkCollision(updatedLeft!!, drone.value?.y!!, it) } == true) {
            isCollided.value = true
        } else {
            isCollided.value = false
            updatedLeft?.let { droneSensors.moveDrone(it, drone.value!!.y, drone.value!!.z) }
            movementListener.updatedAxis(-0.1f, 0f, 0f)
        }
    }

    fun moveUp() {
        val updateUp = drone.value?.y?.minus(10)
        if (room?.let { CollisionUtil.checkCollision(drone.value?.x!!, updateUp!!, it) } == true) {
            isCollided.value = true
        } else {
            isCollided.value = false
            updateUp?.let { droneSensors.moveDrone(drone.value!!.x, it, drone.value!!.z) }
            movementListener.updatedAxis(0f, -0.1f, 0f)
        }

    }

    fun moveDown() {
        val updateDown = drone.value?.y?.plus(10)
        if (room?.let { CollisionUtil.checkCollision(drone.value?.x!!, updateDown!!, it) } == true) {
            isCollided.value = true
        } else {
            isCollided.value = false
            updateDown?.let { droneSensors.moveDrone(drone.value!!.x, it, drone.value!!.z) }
            movementListener.updatedAxis(0f, 0.1f, 0f)
        }
    }

    fun rotateRight() {
        movementListener.rotateRight(1f)
    }

    fun rotateLeft() {
        movementListener.rotateLeft(-1f)
    }
}