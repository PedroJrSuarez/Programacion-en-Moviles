package com.tecsup.campusgps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.tecsup.campusgps.data.LugaresRepositoryLocal
import com.tecsup.campusgps.data.UbicacionRepositoryGoogle
import com.tecsup.campusgps.ui.PantallaMapa
import com.tecsup.campusgps.viewmodel.CampusViewModelFactory
import com.tecsup.campusgps.viewmodel.MapaViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MapaViewModel by viewModels {
        CampusViewModelFactory(
            ubicacionRepo = UbicacionRepositoryGoogle(applicationContext),
            lugaresRepo = LugaresRepositoryLocal()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    PantallaMapa(vm = viewModel)
                }
            }
        }
    }
}
