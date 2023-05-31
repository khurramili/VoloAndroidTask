package com.volo.voloandroidtask.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.volo.voloandroidtask.model.Drone
import com.volo.voloandroidtask.model.Room
import com.volo.voloandroidtask.services.drone.DroneRepository
import com.volo.voloandroidtask.services.gyroscope.MovementListener
import com.volo.voloandroidtask.services.room.RoomRepository
import com.volo.voloandroidtask.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val droneRepository: DroneRepository) :
    ViewModel() {

    val isDroneConnected: MutableLiveData<Boolean> = MutableLiveData(false)
    val isDroneFlying: MutableLiveData<Boolean> = MutableLiveData(false)
    val isCollided: MutableLiveData<Boolean> = MutableLiveData(false)

    private var posX: Float = 0f
    private var posY: Float = 0f
    private var posZ: Float = 0f

    @Inject
    lateinit var roomRepository: RoomRepository
    lateinit var movementListener: MovementListener
    private var room: Room? = null
    var drone: LiveData<Drone?> = droneRepository.drone


    fun intializeRoom(roomWidth: Int, roomHeight: Int) {
        room = roomRepository.createRoom(roomWidth, roomHeight)
    }

    fun initalizeDrone(droneX: Float, droneY: Float, droneZ: Float) {
        droneRepository.setDroneCurrentPoistion(droneX, droneY, droneZ)
    }

    fun moveDrone(x: Float, y: Float, z: Float) {
        val drone: Drone? = drone.value

        // Update the position
        posX = drone!!.x + (x * Constants.thresholdX)
        posY = drone.y + (y * Constants.thresholdY)
        posZ = drone.z + (z * Constants.thresholdZ)


        if (checkCollision(posX, posY)) {
            isCollided.value = true
        } else {
            isCollided.value = false
            droneRepository.moveDrone(posX, posY, posZ)
        }
    }

    fun getRoomSize(): Room? {
        return room
    }

    fun moveRight() {
        val updatedRight = drone.value?.x?.plus(10)
        if (checkCollision(updatedRight!!, drone.value?.y!!)) {
            isCollided.value = true
        } else {
            isCollided.value = false
            droneRepository.moveDrone(updatedRight, drone.value!!.y, drone.value!!.z)
            movementListener.updatedAxis(0.1f, 0f, 0f)
        }
    }

    fun moveLeft() {
        val updatedLeft = drone.value?.x?.minus(10)
        if (checkCollision(updatedLeft!!, drone.value?.y!!)) {
            isCollided.value = true
        } else {
            isCollided.value = false
            droneRepository.moveDrone(updatedLeft, drone.value!!.y, drone.value!!.z)
            movementListener.updatedAxis(-0.1f, 0f, 0f)
        }
    }

    fun moveUp() {
        val updateUp = drone.value?.y?.minus(10)
        if (checkCollision(drone.value?.x!!, updateUp!!)) {
            isCollided.value = true
        } else {
            isCollided.value = false
            droneRepository.moveDrone(drone.value!!.x, updateUp, drone.value!!.z)
            movementListener.updatedAxis(0f, -0.1f, 0f)
        }

    }

    fun moveDown() {
        val updateDown = drone.value?.y?.plus(10)
        if (checkCollision(drone.value?.x!!, updateDown!!)) {
            isCollided.value = true
        } else {
            isCollided.value = false
            droneRepository.moveDrone(drone.value!!.x, updateDown, drone.value!!.z)
            movementListener.updatedAxis(0f, 0.1f, 0f)
        }
    }

    fun rotateRight() {
        movementListener.rotateRight(1f)
    }

    fun rotateLeft() {
        movementListener.rotateLeft(-1f)
    }

    private fun checkCollision(posX: Float, posY: Float): Boolean {
        return if (posX < 100 || posX > (room!!.width - Constants.collisionAvoidDistance)) {
            true
        } else posY < 100 || posY > (room!!.height - Constants.collisionAvoidDistance)
    }

}