package com.volo.voloandroidtask.ui.main.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.volo.voloandroidtask.databinding.ConnectDroneDialogBinding

class ConnectDrone(private val context: Context, private val viewModel: ConnectDroneViewModel) {
    private lateinit var dialog: AlertDialog


    fun show() {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val binding = ConnectDroneDialogBinding.inflate(inflater)

        val passwordEditText = binding.passwordEditText
        val positiveButton = binding.connectDrone
        val negativeButton = binding.notConnect

        positiveButton.setOnClickListener {
            val password = passwordEditText.text.toString()
            viewModel.validatePassword(password)
            dialog.dismiss()
        }

        negativeButton.setOnClickListener {
            dialog.dismiss()
        }

        builder.setView(binding.root)
        dialog = builder.create()
        dialog.show()
    }
}
