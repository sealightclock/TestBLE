package com.example.jonathan.testble

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    private val bleViewModel: BLEViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                BLEScannerScreen(bleViewModel)
            }
        }
    }
}

@Composable
fun BLEScannerScreen(viewModel: BLEViewModel) {
    val isScanning by viewModel.isScanning.collectAsState()
    val scanResults by viewModel.scanResults.observeAsState(emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = {
            if (isScanning) {
                viewModel.stopScan()
            } else {
                viewModel.startScan()
            }
        }) {
            Text(if (isScanning) "Stop Scan" else "Start Scan")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(scanResults) { result ->
                BLEDeviceItem(result)
            }
        }
    }
}

@Composable
fun BLEDeviceItem(scanResult: android.bluetooth.le.ScanResult) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Device Name: ${scanResult.device.name ?: "Unknown"}")
            Text(text = "Device Address: ${scanResult.device.address}")
        }
    }
}
