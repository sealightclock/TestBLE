package com.example.jonathan.testble

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme

class MainActivity : ComponentActivity() {
    private val bleViewModel: BLEViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MultiPermissionScreen(this, bleViewModel)
            }
        }
    }
}
