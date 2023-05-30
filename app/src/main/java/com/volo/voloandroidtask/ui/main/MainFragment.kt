package com.volo.voloandroidtask.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
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
class MainFragment : Fragment(), MovementListener {

    private lateinit var gyroscopeManager: GyroscopeManager

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()
    private lateinit var viewModelConnectDrone: ConnectDroneViewModel

    private var posX: Float = 0f
    private var posY: Float = 0f

    private lateinit var collisionSound: CollisionSound

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
    }

    private fun initialize() {
        collisionSound = CollisionSound(requireContext(), R.raw.alert_sound)
        viewModel.movementListener = this
    }

    private fun switchController() {
        if (binding.switchController.isChecked) {
            if (::gyroscopeManager.isInitialized)
                gyroscopeManager.stopListening()
            binding.manualControllers.visibility = View.VISIBLE
        } else {
            if (::gyroscopeManager.isInitialized)
                gyroscopeManager.startListening()
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
                viewModel.fetchInfo()
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
        viewModel.drone.observe(viewLifecycleOwner) {
            Log.e("Drone", it.toString())
            binding.droneLocationTV.text = it.toString()
        }
    }

    override fun updatedAxis(x: Float, y: Float, z: Float) {
//        viewModel.moveDrone(x, y, z)

        // Update the position
        posX = binding.droneIV.x + (x * 200) //
        posY = binding.droneIV.y + (y * 100)

        // Check collision with screen boundaries
        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels

        if (posX < 0) {
            posX = 0f
            // Handle collision with left screen boundary
            collisionSound.start()

            Log.e("Movement","Left")
        } else if (posX > screenWidth - binding.droneIV.width) {
            posX = (screenWidth - binding.droneIV.width).toFloat()
            // Handle collision with right screen boundary
            collisionSound.start()
            Log.e("Movement","Right")
        }

        if (posY < 0) {
            posY = 0f
            // Handle collision with top screen boundary
            collisionSound.start()
            Log.e("Movement","up")
        } else if (posY > screenHeight - binding.droneIV.height) {
            posY = (screenHeight - binding.droneIV.height).toFloat()
            // Handle collision with bottom screen boundary
            collisionSound.start()
            Log.e("Movement","Down")

        }

        // Update the ImageView position
        binding.droneIV.x = posX
        binding.droneIV.y = posY

        val angleInDegrees = Math.toDegrees(z.toDouble())
        binding.droneIV.rotation = angleInDegrees.toFloat()
    }

}