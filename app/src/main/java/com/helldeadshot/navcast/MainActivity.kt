package com.helldeadshot.navcast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.helldeadshot.navcast.ui.screens.NavCastScreen
import com.helldeadshot.navcast.ui.theme.NavCastTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavCastTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavCastScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
