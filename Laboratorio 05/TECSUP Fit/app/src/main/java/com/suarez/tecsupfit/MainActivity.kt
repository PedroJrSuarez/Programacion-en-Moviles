package com.suarez.tecsupfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.suarez.tecsupfit.ui.theme.TecsupFitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TecsupFitTheme {
                TecsupFitApp()
            }
        }
    }
}
