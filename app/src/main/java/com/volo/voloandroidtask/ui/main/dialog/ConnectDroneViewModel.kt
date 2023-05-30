package com.volo.voloandroidtask.ui.main.dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConnectDroneViewModel : ViewModel() {
    val passwordValidationResult = MutableLiveData<Boolean>()

    fun validatePassword(password: String) {
        val isConnected = password == "volodrone"
        passwordValidationResult.value = isConnected
    }
}