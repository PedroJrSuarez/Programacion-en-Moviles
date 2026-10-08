package com.suarez.saludpluscitas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.suarez.saludpluscitas.navigation.AppNavigation
import com.suarez.saludpluscitas.ui.theme.SaludPlusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SaludPlusTheme { AppNavigation() } }
    }
}
