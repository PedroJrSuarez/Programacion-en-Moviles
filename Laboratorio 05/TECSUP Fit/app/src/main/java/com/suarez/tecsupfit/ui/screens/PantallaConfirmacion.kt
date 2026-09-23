package com.suarez.tecsupfit.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.suarez.tecsupfit.data.ClaseGimnasio
import com.suarez.tecsupfit.navigation.Rutas
import com.suarez.tecsupfit.ui.components.IconoTeal
import com.suarez.tecsupfit.ui.theme.GrayCard
import com.suarez.tecsupfit.ui.theme.GreenCheck
import com.suarez.tecsupfit.ui.theme.TealLight
import com.suarez.tecsupfit.ui.theme.TealPrimary
import com.suarez.tecsupfit.ui.theme.TextDark
import com.suarez.tecsupfit.ui.theme.TextGray

@Composable
fun PantallaConfirmacion(
    clase: ClaseGimnasio,
    navController: NavController,
    onConfirmar: () -> Unit
) {
    LaunchedEffect(clase.id) {
        onConfirmar()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IconoTeal(
            icono = Icons.Filled.Check,
            tamano = 72.dp,
            tamanoIcono = 34.dp,
            fondo = TealLight,
            colorIcono = GreenCheck
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "¡Cupo reservado!",
            fontWeight = FontWeight.Bold,
            color = TextDark,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = clase.nombre,
            color = TextDark,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "Hoy, ${clase.horario} · ${clase.sala}",
            color = TextGray,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                navController.popBackStack(Rutas.Inicio.ruta, inclusive = false)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = TealPrimary,
                contentColor = Color.White
            )
        ) {
            Text("Volver al inicio")
        }

        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                navController.navigate(Rutas.Reservas.ruta) {
                    popUpTo(Rutas.Inicio.ruta)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = GrayCard,
                contentColor = TextDark
            )
        ) {
            Text("Ver mis reservas")
        }
    }
}
