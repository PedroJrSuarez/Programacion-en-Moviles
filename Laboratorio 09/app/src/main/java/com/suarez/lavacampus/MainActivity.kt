package com.suarez.lavacampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.suarez.lavacampus.ui.navigation.NavLava

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // NavLava aplica LavaTheme según el rol y el modo oscuro guardado
        setContent { NavLava() }
    }
}
