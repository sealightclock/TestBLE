package com.example.jonathan.testble

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.ViewModelProvider
import com.example.jonathan.testble.view.MultiPermissionScreen
import com.example.jonathan.testble.viewmodel.BLEViewModel

class MainActivity : ComponentActivity() {
    private lateinit var bleViewModel: BLEViewModel

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bleViewModel = ViewModelProvider(this).get(BLEViewModel::class.java)

        setContent {
            MaterialTheme {
                MultiPermissionScreen(this, bleViewModel)
            }
        }
    }
}
