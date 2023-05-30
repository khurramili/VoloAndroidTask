package com.volo.voloandroidtask.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.volo.voloandroidtask.services.drone.Drone
import com.volo.voloandroidtask.services.room.Room
import com.volo.voloandroidtask.services.drone.DroneRepository
import com.volo.voloandroidtask.services.gyroscope.MovementListener
import com.volo.voloandroidtask.services.room.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val droneRepository: DroneRepository) :
    ViewModel() {

    val isDroneConnected: MutableLiveData<Boolean> = MutableLiveData(false)
    val isDroneFlying: MutableLiveData<Boolean> = MutableLiveData(false)

    private var posX: Float = 0f
    private var posY: Float = 0f
    private var posZ: Float = 0f

    @Inject
    lateinit var roomRepository: RoomRepository

    lateinit var movementListener: MovementListener

    private var room: Room? = null
    var drone: LiveData<Drone?> = droneRepository.drone

    fun fetchInfo() {
        room = roomRepository.createRoom(500, 500, 500)
        droneRepository.setDroneCurrentPoistion(250f, 250f, 250f)
    }

    fun moveDrone(x: Float, y: Float, z: Float) {
        Log.e("SensorDataViewModel", "$x $y $z")
        Log.e("DroneUpdated", drone.value.toString())

//        droneRepository.setDroneCurrentPoistion(50,50,50)


        val drone: Drone? = drone.value

        // Update the position
        posX = drone!!.x + (x * 200)
        posY = drone.y + (y * 200)
        posZ = drone.z + (z * 100)

        if (posX < 0) {
            posX = 0f
            // Handle collision with left screen boundary

        } else if (posX > room!!.width - drone.x) {
            posX = (room!!.width - drone.x)
            // Handle collision with right screen boundary

        }

        if (posY < 0) {
            posY = 0f
            // Handle collision with top screen boundary

        } else if (posY > room!!.height - drone.y) {
            posY = (room!!.height - drone.y).toFloat()
            // Handle collision with bottom screen boundary

        }

        droneRepository.moveDrone(posX, posY, posZ)

    }

    fun getRoomSize(): Room? {
        return room
    }

    fun moveRight() {
        val updatedRight = drone.value?.x?.plus(10)
        droneRepository.moveDrone(updatedRight!!, drone.value!!.y, drone.value!!.z)
        movementListener.updatedAxis(0.1f, 0f, 0f)
    }

    fun moveLeft() {
        val updatedLeft = drone.value?.x?.minus(10)
        droneRepository.moveDrone(updatedLeft!!, drone.value!!.y, drone.value!!.z)
        movementListener.updatedAxis(-0.1f, 0f, 0f)
    }

    fun moveUp() {
        val updateUp = drone.value?.y?.minus(10)
        droneRepository.moveDrone( drone.value!!.x,updateUp!!, drone.value!!.z)
        movementListener.updatedAxis(0f, -0.1f, 0f)
    }

    fun moveDown() {
        val updateDown = drone.value?.y?.plus(10)
        droneRepository.moveDrone( drone.value!!.x,updateDown!!, drone.value!!.z)
        movementListener.updatedAxis(0f, 0.1f, 0f)

    }

    fun rotateRight() {

    }

    fun rotateLeft() {

    }

}