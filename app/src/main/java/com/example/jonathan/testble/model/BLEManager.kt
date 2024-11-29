package com.example.jonathan.testble.model

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class BLEManager(context: Context) {

    private val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter: BluetoothAdapter = bluetoothManager.adapter
    private val scanner: BluetoothLeScanner? = bluetoothAdapter.bluetoothLeScanner

    private val _scanResults = MutableLiveData<List<ScanResult>>()
    val scanResults: LiveData<List<ScanResult>> = _scanResults

    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            _scanResults.value = (_scanResults.value ?: emptyList()) + result
        }

        override fun onBatchScanResults(results: List<ScanResult>) {
            _scanResults.value = (_scanResults.value ?: emptyList()) + results
        }

        override fun onScanFailed(errorCode: Int) {
            // Handle scan error
        }
    }

    @SuppressLint("MissingPermission")
    fun startScan() {
        scanner?.startScan(scanCallback)
    }

    @SuppressLint("MissingPermission")
    fun stopScan() {
        scanner?.stopScan(scanCallback)
    }
}
