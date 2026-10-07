package com.suarez.saludplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.suarez.saludplus.navigation.AppNavigation
import com.suarez.saludplus.ui.theme.SaludPlusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SaludPlusTheme { AppNavigation() } }
    }
}
