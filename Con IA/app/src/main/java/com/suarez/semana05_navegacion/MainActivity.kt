package com.suarez.semana05_navegacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.suarez.semana05_navegacion.navigation.AppNavigation
import com.suarez.semana05_navegacion.ui.theme.NavLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavLabTheme {
                AppNavigation()
            }
        }
    }
}
