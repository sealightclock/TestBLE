package com.example.jonathan.testble

import android.Manifest
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

private const val TAG = "TBLE: BleScanView"

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun BleScanner() {
    Log.d(TAG, "BleScanner")

    val context = LocalContext.current
    var isScanning by remember { mutableStateOf(false) }
    val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    val bluetoothAdapter = bluetoothManager.adapter
    var scannedDevices by remember { mutableStateOf<List<ScanResult>>(emptyList()) }

    val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            result?.let {
                scannedDevices = scannedDevices + it
            }
        }
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.v(TAG, "BleScanner: launcher: isGranted == true")

            // Permission granted, start scanning
            if (isScanning) {
                Log.v(TAG, "BleScanner: launcher: isGranted == true: startScan...")

                bluetoothAdapter.bluetoothLeScanner?.startScan(scanCallback)
            }
        } else {
            // Permission denied, handle accordingly
            Log.w(TAG, "BleScanner: launcher: isGranted == false")
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            Log.d(TAG, "Button: onClick")

            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH_SCAN
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Log.v(TAG, "Button: onClick: PERMISSION_GRANTED")

                isScanning = !isScanning
                if (isScanning) {
                    Log.v(TAG, "Button: onClick: PERMISSION_GRANTED: startScan")

                    bluetoothAdapter.bluetoothLeScanner?.startScan(scanCallback)
                } else {
                    Log.v(TAG, "Button: onClick: PERMISSION_GRANTED: stopScan")

                    bluetoothAdapter.bluetoothLeScanner?.stopScan(scanCallback)
                }
            } else {
                Log.v(TAG, "Button: onClick: Request permission BLUETOOTH_SCAN")

                launcher.launch(Manifest.permission.BLUETOOTH_SCAN)
            }
        }) {
            Text(if (isScanning) "Stop Scanning" else "Start Scanning")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(scannedDevices) { device ->
                Card(modifier = Modifier.padding(8.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Device Name: ${device.device.name ?: "Unknown"}")
                        Text(text = "Device Address: ${device.device.address}")
                    }
                }
            }
        }
    }
}
