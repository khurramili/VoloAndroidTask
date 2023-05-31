package com.volo.voloandroidtask.ui.viewmodel.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.volo.voloandroidtask.model.Drone
import com.volo.voloandroidtask.model.Room
import com.volo.voloandroidtask.sensors.drone.DroneSensors
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var droneSensors: DroneSensors

    @Mock
    private lateinit var droneObserver: Observer<Drone?>

    private lateinit var viewModel: MainViewModel


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(droneSensors)
    }

    @Test
    fun `initializeRoom should create room with given width and height`() {
        val roomWidth = 10
        val roomHeight = 8
        val expectedRoom = Room(roomWidth, roomHeight)

        viewModel.intializeRoom(roomWidth, roomHeight)

        assertEquals(expectedRoom, viewModel.room)
    }

    @Test
    fun `initializeDrone should set drone current position with given coordinates`() {
        val droneX = 2.0f
        val droneY = 4.5f
        val droneZ = 3.8f

        viewModel.initalizeDrone(droneX, droneY, droneZ)

        // Verify that the setDroneCurrentPoistion method is called with the correct parameters
        verify(droneSensors).setDroneCurrentPoistion(droneX, droneY, droneZ)
    }
}
