package com.suarez.lavacampus.ui.estudiante

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.suarez.lavacampus.model.Rol
import com.suarez.lavacampus.ui.components.BotonLava
import com.suarez.lavacampus.ui.components.CampoTexto
import com.suarez.lavacampus.ui.components.TemaLava
import com.suarez.lavacampus.viewmodel.SesionViewModel

/** Un solo Composable para ambos roles */
@Composable
fun LoginScreen(vm: SesionViewModel) {
    var rol by remember { mutableStateOf(Rol.CLIENTE) }
    var correo by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    val error by vm.error.collectAsState()
    val oscuro by vm.modoOscuro.collectAsState()

    // El tema cambia de color según la pestaña (turquesa / violeta)
    TemaLava(rol, oscuro) {
        Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
            Column(
                Modifier.fillMaxSize().statusBarsPadding().imePadding().padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.CenterVertically)
            ) {
                Text("LavaCampus", style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Text("Lavandería universitaria", style = MaterialTheme.typography.bodyMedium)

                TabRow(selectedTabIndex = if (rol == Rol.CLIENTE) 0 else 1) {
                    Tab(selected = rol == Rol.CLIENTE, onClick = { rol = Rol.CLIENTE; vm.limpiarError() },
                        text = { Text("Estudiante") })
                    Tab(selected = rol == Rol.ADMIN, onClick = { rol = Rol.ADMIN; vm.limpiarError() },
                        text = { Text("Administrador") })
                }

                CampoTexto(correo, { correo = it }, "Correo", Modifier.fillMaxWidth())
                CampoTexto(clave, { clave = it }, "Contraseña", Modifier.fillMaxWidth(), esClave = true)

                error?.let {
                    Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
                }

                BotonLava("Ingresar", { vm.login(correo, clave, rol) }, Modifier.fillMaxWidth())

                Text(
                    "Demo: ana@correo.test / 1234  ·  admin@lavacampus.test / admin",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
