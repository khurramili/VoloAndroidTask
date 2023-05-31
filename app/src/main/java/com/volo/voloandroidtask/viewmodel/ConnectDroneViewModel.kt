package com.volo.voloandroidtask.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.volo.voloandroidtask.constants.Constants

class ConnectDroneViewModel : ViewModel() {
    val passwordValidationResult = MutableLiveData<Boolean>()
    fun validatePassword(password: String) {
        val isConnected = password == Constants.wifiPassword
        passwordValidationResult.value = isConnected
    }
}