package com.suarez.saludpluscitas.ui.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.suarez.saludpluscitas.data.model.Cita
import com.suarez.saludpluscitas.data.model.Especialidad
import com.suarez.saludpluscitas.data.model.Medico
import com.suarez.saludpluscitas.data.repository.Repositorio
import com.suarez.saludpluscitas.navigation.Rutas
import com.suarez.saludpluscitas.ui.theme.*
import java.time.LocalDate

/** "Martes 16 de setiembre 2026". Patrones cortos: "d", "MMM", "EEE". */
fun formatearFecha(iso: String, patron: String = "larga"): String {
    val f = LocalDate.parse(iso)
    return when (patron) {
        "d" -> f.dayOfMonth.toString()
        "MMM" -> Fechas.mesCorto(f)
        "EEE" -> Fechas.diaCorto(f)
        else -> Fechas.fechaLarga(f)
    }
}

// ---------------------------------------------------------------- barras
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior(
    titulo: String,
    navController: NavController,
    acciones: @Composable RowScope.() -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(titulo, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Navy) },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Atrás", tint = Navy)
            }
        },
        actions = acciones,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
    )
}

private data class ItemMenu(val ruta: String, val titulo: String, val icono: ImageVector)

/** Menú principal de navegación (Inicio, Citas, Resultados, Perfil). */
@Composable
fun BarraInferior(actual: String, navController: NavController) {
    val items = listOf(
        ItemMenu(Rutas.HOME, "Inicio", Icons.Default.Home),
        ItemMenu(Rutas.MIS_CITAS, "Citas", Icons.Default.CalendarMonth),
        ItemMenu(Rutas.RESULTADOS, "Resultados", Icons.Default.Description),
        ItemMenu(Rutas.PERFIL, "Perfil", Icons.Default.Person)
    )
    NavigationBar(containerColor = Color.White) {
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
                label = { Text(item.titulo, fontSize = 11.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Azul, selectedTextColor = Azul,
                    unselectedIconColor = Gris, unselectedTextColor = Gris,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

// ---------------------------------------------------------------- botones y campos
@Composable
fun BotonPrincipal(texto: String, modifier: Modifier = Modifier, enabled: Boolean = true, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.fillMaxWidth().height(54.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Azul, contentColor = Color.White,
            disabledContainerColor = Color(0xFFB9CBF3), disabledContentColor = Color.White
        )
    ) { Text(texto, fontWeight = FontWeight.SemiBold, fontSize = 16.sp) }
}

@Composable
fun BotonSecundario(texto: String, modifier: Modifier = Modifier, color: Color = Azul, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(54.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.5.dp, color),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = color)
    ) { Text(texto, fontWeight = FontWeight.SemiBold, fontSize = 16.sp) }
}

@Composable
fun colorCampo() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Azul, unfocusedBorderColor = Borde,
    focusedContainerColor = FondoSuave, unfocusedContainerColor = FondoSuave,
    focusedTextColor = Navy, unfocusedTextColor = Navy, cursorColor = Azul
)

/** Campo del diseño: icono en cuadro azul claro a la izquierda y etiqueta sobre el campo. */
@Composable
fun CampoConIcono(
    icono: ImageVector,
    etiqueta: String,
    valor: String,
    onCambio: (String) -> Unit,
    password: Boolean = false,
    teclado: KeyboardType = KeyboardType.Text
) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
        IconoTile(icono, Azul, AzulClaro, 52.dp, Modifier.padding(bottom = 2.dp))
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(etiqueta, color = Gris, fontSize = 12.sp, modifier = Modifier.padding(bottom = 4.dp))
            OutlinedTextField(
                value = valor, onValueChange = onCambio, singleLine = true,
                visualTransformation = if (password) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(keyboardType = if (password) KeyboardType.Password else teclado),
                shape = RoundedCornerShape(12.dp), colors = colorCampo(),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun MensajeError(texto: String) {
    if (texto.isNotEmpty()) Text(texto, color = Rojo, fontSize = 13.sp)
}

@Composable
fun EstadoVacio(texto: String, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
        Text(texto, color = Gris)
    }
}

// ---------------------------------------------------------------- piezas visuales
@Composable
fun IconoTile(
    icono: ImageVector, tint: Color, fondo: Color, tam: Dp = 44.dp,
    modifier: Modifier = Modifier, forma: Shape = RoundedCornerShape(12.dp)
) {
    Box(modifier.size(tam).clip(forma).background(fondo), contentAlignment = Alignment.Center) {
        Icon(icono, null, tint = tint, modifier = Modifier.size(tam * 0.5f))
    }
}

@Composable
fun TarjetaBase(modifier: Modifier = Modifier, onClick: (() -> Unit)? = null, contenido: @Composable () -> Unit) {
    val m = modifier.fillMaxWidth()
    if (onClick != null) {
        Surface(
            onClick = onClick, modifier = m, shape = RoundedCornerShape(16.dp), color = Color.White,
            shadowElevation = 2.dp, border = BorderStroke(1.dp, Borde)
        ) { contenido() }
    } else {
        Surface(
            modifier = m, shape = RoundedCornerShape(16.dp), color = Color.White,
            shadowElevation = 2.dp, border = BorderStroke(1.dp, Borde)
        ) { contenido() }
    }
}

@Composable
fun Insignia(texto: String, fondo: Color = VerdeClaro, color: Color = Verde) {
    Surface(shape = RoundedCornerShape(8.dp), color = fondo) {
        Text(texto, color = color, fontSize = 11.sp, fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
    }
}

/** Icono y color de cada especialidad (como en el diseño). */
fun estiloEspecialidad(id: Int): Pair<ImageVector, Color> = when (id) {
    1 -> Icons.Default.Person to Azul
    2 -> Icons.Default.ChildCare to Naranja
    3 -> Icons.Default.Female to Rosa
    4 -> Icons.Default.Favorite to Rojo
    5 -> Icons.Default.WaterDrop to Naranja
    6 -> Icons.Default.Healing to Azul
    7 -> Icons.Default.Visibility to Azul
    else -> Icons.Default.MedicalServices to Color(0xFF1AA7B8)
}

@Composable
fun TarjetaEspecialidad(esp: Especialidad, onClick: () -> Unit) {
    val (icono, color) = estiloEspecialidad(esp.id)
    TarjetaBase(onClick = onClick) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            IconoTile(icono, color, color.copy(alpha = 0.12f), 48.dp)
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(esp.nombre, fontWeight = FontWeight.Bold, color = Navy, fontSize = 15.sp)
                Text(esp.descripcion, fontSize = 12.sp, color = Gris)
            }
            Icon(Icons.Default.ChevronRight, null, tint = Gris)
        }
    }
}

@Composable
fun TarjetaMedico(medico: Medico, onClick: () -> Unit) {
    val esp = Repositorio.obtenerEspecialidad(medico.especialidadId)?.nombre ?: ""
    TarjetaBase(onClick = onClick) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            AvatarMedico(medico.nombre, 68.dp)
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(medico.nombre, fontWeight = FontWeight.Bold, color = Navy, fontSize = 15.sp)
                Text(esp, fontSize = 12.sp, color = Gris)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, null, tint = Color(0xFFFFB020), modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("${medico.calificacion} (${medico.resenas})", fontSize = 12.sp, color = Navy)
                }
                Spacer(Modifier.height(6.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Insignia(medico.disponibilidad)
                }
            }
        }
    }
}

@Composable
fun TarjetaCita(cita: Cita, onClick: () -> Unit) {
    val medico = Repositorio.obtenerMedico(cita.medicoId)
    val esp = medico?.let { Repositorio.obtenerEspecialidad(it.especialidadId) }
    TarjetaBase(onClick = onClick) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            AvatarMedico(medico?.nombre ?: "", 56.dp)
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(medico?.nombre ?: "Médico", fontWeight = FontWeight.Bold, color = Navy, fontSize = 15.sp)
                Text(esp?.nombre ?: "", fontSize = 12.sp, color = Gris)
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CalendarMonth, null, tint = Azul, modifier = Modifier.size(15.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("${formatearFecha(cita.fecha, "d")} ${formatearFecha(cita.fecha, "MMM")} · ${cita.hora}",
                        fontSize = 12.sp, color = Navy)
                }
            }
            Icon(Icons.Default.ChevronRight, null, tint = Gris)
        }
    }
}

/** Fila de información: icono en cuadro azul claro, etiqueta gris y valor. */
@Composable
fun InfoFila(icono: ImageVector, etiqueta: String, valor: String) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconoTile(icono, Azul, AzulClaro, 46.dp)
        Spacer(Modifier.width(14.dp))
        Column {
            Text(etiqueta, fontSize = 12.sp, color = Gris)
            Text(valor, fontWeight = FontWeight.SemiBold, color = Navy, fontSize = 15.sp)
        }
    }
}

/** Cabecera con avatar, nombre y especialidad (Fecha y hora / Confirmar). */
@Composable
fun CabeceraMedico(medico: Medico?, mostrarCmp: Boolean = false) {
    val esp = medico?.let { Repositorio.obtenerEspecialidad(it.especialidadId)?.nombre } ?: ""
    Surface(shape = RoundedCornerShape(16.dp), color = FondoSuave, modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            AvatarMedico(medico?.nombre ?: "", 72.dp)
            Spacer(Modifier.width(16.dp))
            Column {
                Text(medico?.nombre ?: "", fontWeight = FontWeight.Bold, color = Navy, fontSize = 17.sp)
                Text(esp, color = Gris, fontSize = 13.sp)
                if (mostrarCmp) Text("CMP: ${medico?.cmp ?: ""}", color = Gris, fontSize = 13.sp)
            }
        }
    }
}
