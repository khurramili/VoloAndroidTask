package com.volo.voloandroidtask.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.volo.voloandroidtask.R
import com.volo.voloandroidtask.databinding.FragmentMainBinding
import com.volo.voloandroidtask.services.gyroscope.GyroscopeManager
import com.volo.voloandroidtask.services.gyroscope.MovementListener
import com.volo.voloandroidtask.ui.main.dialog.ConnectDrone
import com.volo.voloandroidtask.ui.main.dialog.ConnectDroneViewModel
import com.volo.voloandroidtask.utils.CollisionSound
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), MovementListener{

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private lateinit var viewModelConnectDrone: ConnectDroneViewModel
    private lateinit var collisionSound: CollisionSound
    private lateinit var gyroscopeManager: GyroscopeManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initialize()
        binding.connectWifi.setOnClickListener {
            showPasswordDialog()
        }

        binding.startStopButton.setOnClickListener {
            viewModel.isDroneFlying.value = viewModel.isDroneFlying.value == false
        }

        //Start listening  the values of gyroscope sensor when drone started to fly
        viewModel.isDroneFlying.observe(viewLifecycleOwner) {
            if (it) {
                binding.startStopButton.text = "Stop"
                if (::gyroscopeManager.isInitialized)
                    gyroscopeManager.startListening()
            } else {
                binding.startStopButton.text = "Start"
                if (::gyroscopeManager.isInitialized)
                    gyroscopeManager.stopListening()
            }
        }

        binding.switchController.setOnClickListener {
            switchController()
        }

        //get a notification from view model on collision
        viewModel.isCollided.observe(viewLifecycleOwner) {
            if (it) {
                collisionSound.start()
                binding.alertView.visibility = View.VISIBLE
            } else {
                collisionSound.stop()
                binding.alertView.visibility = View.GONE
            }
        }
    }

    //Initialize the viewModel before starting the process
    private fun initialize() {
        collisionSound = CollisionSound(requireContext(), R.raw.alert_sound)
        binding.mainViewModel = viewModel
        viewModel.movementListener = this
    }

    private fun switchController() {
        if (binding.switchController.isChecked) {
            if (::gyroscopeManager.isInitialized)
                gyroscopeManager.stopListening()
            binding.manualControllers.visibility = View.VISIBLE
        } else {
            binding.manualControllers.visibility = View.GONE
        }
    }

    private fun showPasswordDialog() {
        viewModelConnectDrone = ViewModelProvider(this)[ConnectDroneViewModel::class.java]

        val passwordDialog = ConnectDrone(requireContext(), viewModelConnectDrone)
        passwordDialog.show()

        viewModelConnectDrone.passwordValidationResult.observe(
            viewLifecycleOwner
        ) { isConnected ->
            if (isConnected) {
                binding.droneConnectTV.text = "Drone Connected"
                viewModel.isDroneConnected.value = true

                // Assuming Room is equals to our room size
                val roomWidth = resources.displayMetrics.widthPixels
                val roomHeight = resources.displayMetrics.heightPixels

                //assuming Drone location according to ImageView of drone in screen
                viewModel.intializeRoom(roomWidth, roomHeight)
                viewModel.initalizeDrone(
                    binding.droneIV.x,
                    binding.droneIV.y,
                    binding.droneIV.z
                )

                //update the UI when Drone Connected and initialize the sensor
                viewModel.isDroneConnected.observe(viewLifecycleOwner) {
                    gyroscopeManager = GyroscopeManager(requireContext(), this)
                    binding.startStopButton.visibility = View.VISIBLE
                    binding.roomSizeTV.text = "${viewModel.getRoomSize()}"
                }
                handleDroneMovement()
            } else {
                Toast.makeText(context, "Wrong Password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleDroneMovement() {

        //Update the UI when drone value change from listener
        viewModel.drone.observe(viewLifecycleOwner) {
            binding.droneLocationTV.text = it.toString()
            binding.droneIV.x = it?.x!!
            binding.droneIV.y = it.y
            binding.droneIV.z = it.z
        }
    }

    override fun updatedAxis(x: Float, y: Float, z: Float) {
        //send updated values from sensor to the drone instance
        viewModel.moveDrone(x, y, z)
        val angleInDegrees = Math.toDegrees(z.toDouble())
        binding.droneIV.animate().rotation(angleInDegrees.toFloat()).setDuration(200).start()

    }

    override fun rotateRight(z: Float) {
        val angleInDegrees = Math.toDegrees(z.toDouble())
        binding.droneIV.animate().rotation(angleInDegrees.toFloat()).setDuration(200).start()
    }

    override fun rotateLeft(z: Float) {
        val angleInDegrees = Math.toDegrees(z.toDouble())
        binding.droneIV.animate().rotation(angleInDegrees.toFloat()).setDuration(200).start()
    }
}