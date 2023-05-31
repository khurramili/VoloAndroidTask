package com.volo.voloandroidtask.view.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.volo.voloandroidtask.databinding.ConnectDroneDialogBinding
import com.volo.voloandroidtask.viewmodel.ConnectDroneViewModel

class ConnectDrone(private val context: Context, private val viewModel: ConnectDroneViewModel) {
    private lateinit var dialog: AlertDialog

    fun show() {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val binding = ConnectDroneDialogBinding.inflate(inflater)


        binding.connectDrone.setOnClickListener {
            val password = binding.passwordEditText.text.toString()
            viewModel.validatePassword(password)
            dialog.dismiss()
        }

        binding.notConnect.setOnClickListener {
            dialog.dismiss()
        }

        builder.setView(binding.root)
        dialog = builder.create()
        dialog.show()
    }
}
