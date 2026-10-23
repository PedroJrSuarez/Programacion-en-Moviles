package com.suarez.lavacampus.ui.components

// ============================================================================
//  ADAPTADORES: única capa que toca la API de los componentes (ui/components)
//  (LavaTheme, LavaTextField, LavaButton, EstadoPedidoChip, PedidoCard,
//  StepperEstado, IconoServicio, SelectorCantidad, GraficoDona, GraficoBarras,
//  SplashScreen). Si alguna firma del proyecto base es distinta, se corrige AQUÍ
//  y el resto de pantallas no cambia. Busca los comentarios "ADAPTAR".
// ============================================================================

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.suarez.lavacampus.data.local.relation.PedidoConDetalle
import com.suarez.lavacampus.model.EstadoPedido
import com.suarez.lavacampus.model.Rol
import com.suarez.lavacampus.ui.components.graficos.DatoGrafico
import com.suarez.lavacampus.ui.components.graficos.GraficoBarras
import com.suarez.lavacampus.ui.components.graficos.GraficoDona
import com.suarez.lavacampus.ui.estudiante.SplashScreen
import com.suarez.lavacampus.ui.theme.LavaTheme

// ADAPTAR: LavaTheme(rol, darkTheme, content)
@Composable
fun TemaLava(rol: Rol, oscuro: Boolean, content: @Composable () -> Unit) =
    LavaTheme(rol = rol, darkTheme = oscuro, content = content)

// ADAPTAR: SplashScreen(onFinish)
@Composable
fun PantallaSplash(onFin: () -> Unit) = SplashScreen(onFinish = onFin)

// ADAPTAR: LavaTextField(value, onValueChange, label, modifier, isPassword)
@Composable
fun CampoTexto(
    valor: String, onCambio: (String) -> Unit, etiqueta: String,
    modifier: Modifier = Modifier, esClave: Boolean = false
) = LavaTextField(value = valor, onValueChange = onCambio, label = etiqueta,
    modifier = modifier, isPassword = esClave)

// ADAPTAR: LavaButton(text, onClick, modifier, enabled)
@Composable
fun BotonLava(texto: String, onClick: () -> Unit, modifier: Modifier = Modifier, habilitado: Boolean = true) =
    LavaButton(text = texto, onClick = onClick, modifier = modifier, enabled = habilitado)

// ADAPTAR: EstadoPedidoChip(estado)
@Composable
fun ChipEstado(estado: EstadoPedido) = EstadoPedidoChip(estado = estado)

// ADAPTAR: StepperEstado(estado, vertical, modifier)
@Composable
fun Seguimiento(
    estado: EstadoPedido, vertical: Boolean, modifier: Modifier = Modifier, sobrePrimario: Boolean = false
) = StepperEstado(estado = estado, vertical = vertical, modifier = modifier, sobrePrimario = sobrePrimario)

// ADAPTAR: IconoServicio(icono, modifier)
@Composable
fun IconoDeServicio(icono: String, modifier: Modifier = Modifier) =
    IconoServicio(icono = icono, modifier = modifier)

// ADAPTAR: SelectorCantidad(valor, paso, minimo, maximo, onCambio)
/** onDelta recibe +1 (más) o -1 (menos); el ViewModel aplica las reglas de la cantidad */
@Composable
fun SelectorDeCantidad(valor: Double, paso: Double, minimo: Double, maximo: Double, onDelta: (Int) -> Unit) =
    SelectorCantidad(valor = valor, paso = paso, minimo = minimo, maximo = maximo,
        onCambio = { nuevo -> onDelta(if (nuevo > valor) 1 else -1) })

// ADAPTAR: PedidoCard(pedido, onClick, modifier)
@Composable
fun TarjetaPedido(
    p: PedidoConDetalle, onClick: () -> Unit, modifier: Modifier = Modifier,
    atrasado: Boolean = false,
    extra: @Composable ColumnScope.() -> Unit = {}
) {
    Column(modifier) {
        PedidoCard(
            pedido = p, onClick = onClick,
            modifier = Modifier.fillMaxWidth().then(
                if (atrasado) Modifier.border(2.dp, MaterialTheme.colorScheme.error, RoundedCornerShape(12.dp))
                else Modifier
            )
        )
        extra()
    }
}

// ADAPTAR: GraficoDona(datos, modifier) / GraficoBarras(datos, modifier)
@Composable
fun Dona(datos: List<DatoGrafico>, modifier: Modifier = Modifier) = GraficoDona(datos = datos, modifier = modifier)

@Composable
fun Barras(datos: List<DatoGrafico>, modifier: Modifier = Modifier) = GraficoBarras(datos = datos, modifier = modifier)

// ------------------------- Utilidades propias (no dependen del base) -------------------------

/** Pantalla con barra superior del color primario del rol. Insets resueltos aquí. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaBase(
    titulo: String,
    onVolver: (() -> Unit)? = null,
    fab: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(titulo) },
                navigationIcon = {
                    if (onVolver != null) {
                        IconButton(onClick = onVolver) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = fab,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        content = content
    )
}

@Composable
fun FilaSwitch(texto: String, valor: Boolean, onCambio: (Boolean) -> Unit) {
    Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(texto, Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
        Switch(checked = valor, onCheckedChange = onCambio)
    }
}
