package com.example.jonathan.testble.view

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jonathan.testble.viewmodel.BLEViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
// Do not remove context!
fun MultiPermissionScreen(context: Context, bleViewModel: BLEViewModel) {
    val permissions = listOf(
        android.Manifest.permission.BLUETOOTH_SCAN,
        android.Manifest.permission.BLUETOOTH_CONNECT,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )
    val multiplePermissionsState = rememberMultiplePermissionsState(permissions)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            multiplePermissionsState.allPermissionsGranted -> {
                Text("All permissions granted!")
                Spacer(modifier = Modifier.height(8.dp))
                BLEScannerScreen(bleViewModel)
            }
            multiplePermissionsState.shouldShowRationale -> {
                Text("Permissions are needed for the app to function properly.")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { multiplePermissionsState.launchMultiplePermissionRequest() }) {
                    Text("Request Permissions")
                }
            }
            else -> {
                Text("Permissions denied or not requested.")
                Spacer(modifier = Modifier.height(8.dp))
                // Go to request again:
                Button(onClick = { multiplePermissionsState.launchMultiplePermissionRequest() }) {
                    Text("Grant Permissions")
                }

                // Or, direct the user to the Settings page:
                //OpenSettingsButton(context)
            }
        }
    }
}
