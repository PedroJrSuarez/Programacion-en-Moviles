package com.suarez.saludplus.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.suarez.saludplus.data.model.Cita
import com.suarez.saludplus.data.model.Especialidad
import com.suarez.saludplus.data.model.Medico
import com.suarez.saludplus.data.repository.Repositorio
import com.suarez.saludplus.navigation.Rutas
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatearFecha(iso: String, patron: String = "EEEE d 'de' MMMM"): String =
    LocalDate.parse(iso).format(DateTimeFormatter.ofPattern(patron, Locale("es", "PE")))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior(titulo: String, navController: NavController) {
    TopAppBar(
        title = { Text(titulo, fontWeight = FontWeight.Bold) },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
            }
        }
    )
}

@Composable
fun BotonPrincipal(texto: String, modifier: Modifier = Modifier, enabled: Boolean = true, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.fillMaxWidth().height(52.dp),
        shape = RoundedCornerShape(14.dp)
    ) { Text(texto, fontWeight = FontWeight.SemiBold) }
}

@Composable
fun BotonSecundario(texto: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(52.dp),
        shape = RoundedCornerShape(14.dp)
    ) { Text(texto, fontWeight = FontWeight.SemiBold) }
}

@Composable
fun CampoTexto(
    valor: String,
    onCambio: (String) -> Unit,
    etiqueta: String,
    modifier: Modifier = Modifier,
    password: Boolean = false,
    teclado: KeyboardType = KeyboardType.Text,
    error: Boolean = false
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onCambio,
        label = { Text(etiqueta) },
        singleLine = true,
        isError = error,
        visualTransformation = if (password) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = if (password) KeyboardType.Password else teclado),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun MensajeError(texto: String) {
    if (texto.isNotEmpty()) Text(texto, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
}

@Composable
fun EstadoVacio(texto: String, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
        Text(texto, color = MaterialTheme.colorScheme.outline)
    }
}

@Composable
fun TarjetaEspecialidad(esp: Especialidad, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(48.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) { Text(esp.emoji, style = MaterialTheme.typography.titleLarge) }
            Spacer(Modifier.width(14.dp))
            Column {
                Text(esp.nombre, fontWeight = FontWeight.Bold)
                Text(esp.descripcion, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
            }
        }
    }
}

@Composable
fun TarjetaMedico(medico: Medico, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(52.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) { Icon(Icons.Default.Person, null, tint = MaterialTheme.colorScheme.primary) }
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(medico.nombre, fontWeight = FontWeight.Bold)
                Text(
                    Repositorio.obtenerEspecialidad(medico.especialidadId)?.nombre ?: "",
                    style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline
                )
                Text("⭐ ${medico.calificacion} · ${medico.aniosExperiencia} años de experiencia", style = MaterialTheme.typography.bodySmall)
            }
            Text("S/ ${medico.precio.toInt()}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun TarjetaCita(cita: Cita, onClick: () -> Unit) {
    val medico = Repositorio.obtenerMedico(cita.medicoId)
    val esp = medico?.let { Repositorio.obtenerEspecialidad(it.especialidadId) }
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(
                Modifier.clip(RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.primaryContainer).padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(formatearFecha(cita.fecha, "d"), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
                Text(formatearFecha(cita.fecha, "MMM"), style = MaterialTheme.typography.bodySmall)
            }
            Spacer(Modifier.width(14.dp))
            Column {
                Text(medico?.nombre ?: "Médico", fontWeight = FontWeight.Bold)
                Text("${esp?.nombre ?: ""} · ${cita.hora}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
            }
        }
    }
}

private data class ItemMenu(val ruta: String, val titulo: String, val icono: ImageVector)

/** Menú principal de navegación (Inicio, Citas, Resultados, Perfil). */
@Composable
fun BarraInferior(actual: String, navController: NavController) {
    val items = listOf(
        ItemMenu(Rutas.HOME, "Inicio", Icons.Default.Home),
        ItemMenu(Rutas.MIS_CITAS, "Citas", Icons.Default.DateRange),
        ItemMenu(Rutas.RESULTADOS, "Resultados", Icons.Default.CheckCircle),
        ItemMenu(Rutas.PERFIL, "Perfil", Icons.Default.Person)
    )
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = actual == item.ruta,
                onClick = {
                    if (actual != item.ruta) {
                        navController.navigate(item.ruta) {
                            popUpTo(Rutas.HOME)
                            launchSingleTop = true
                        }
                    }
                },
                icon = { Icon(item.icono, contentDescription = item.titulo) },
                label = { Text(item.titulo) }
            )
        }
    }
}
