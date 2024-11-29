package com.example.jonathan.testble

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BLEViewModel(application: Application) : AndroidViewModel(application) {

    private val bleManager = BLEManager(application)

    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning

    val scanResults = bleManager.scanResults

    fun startScan() {
        _isScanning.value = true
        bleManager.startScan()
    }

    fun stopScan() {
        _isScanning.value = false
        bleManager.stopScan()
    }
}
