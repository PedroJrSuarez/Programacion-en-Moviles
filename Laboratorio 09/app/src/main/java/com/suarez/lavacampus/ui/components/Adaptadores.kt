package com.suarez.lavacampus.ui.components


import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

@Composable
fun TemaLava(rol: Rol, oscuro: Boolean, content: @Composable () -> Unit) =
    LavaTheme(rol = rol, darkTheme = oscuro, content = content)

@Composable
fun PantallaSplash(onFin: () -> Unit) = SplashScreen(onFinish = onFin)

@Composable
fun CampoTexto(
    valor: String, onCambio: (String) -> Unit, etiqueta: String,
    modifier: Modifier = Modifier, esClave: Boolean = false
) = LavaTextField(value = valor, onValueChange = onCambio, label = etiqueta,
    modifier = modifier, isPassword = esClave)

@Composable
fun BotonLava(texto: String, onClick: () -> Unit, modifier: Modifier = Modifier, habilitado: Boolean = true) =
    LavaButton(text = texto, onClick = onClick, modifier = modifier, enabled = habilitado)

@Composable
fun ChipEstado(estado: EstadoPedido) = EstadoPedidoChip(estado = estado)

@Composable
fun Seguimiento(
    estado: EstadoPedido, vertical: Boolean, modifier: Modifier = Modifier, sobrePrimario: Boolean = false
) = StepperEstado(estado = estado, vertical = vertical, modifier = modifier, sobrePrimario = sobrePrimario)

@Composable
fun IconoDeServicio(icono: String, modifier: Modifier = Modifier) =
    IconoServicio(icono = icono, modifier = modifier)

@Composable
fun SelectorDeCantidad(valor: Double, paso: Double, minimo: Double, maximo: Double, onDelta: (Int) -> Unit) =
    SelectorCantidad(valor = valor, paso = paso, minimo = minimo, maximo = maximo,
        onCambio = { nuevo -> onDelta(if (nuevo > valor) 1 else -1) })

@Composable
fun TarjetaPedido(
    p: PedidoConDetalle, onClick: () -> Unit, modifier: Modifier = Modifier,
    extra: @Composable ColumnScope.() -> Unit = {}
) {
    Column(modifier) {
        PedidoCard(pedido = p, onClick = onClick, modifier = Modifier.fillMaxWidth())
        extra()
    }
}

@Composable
fun Dona(datos: List<DatoGrafico>, modifier: Modifier = Modifier) = GraficoDona(datos = datos, modifier = modifier)

@Composable
fun Barras(datos: List<DatoGrafico>, modifier: Modifier = Modifier) = GraficoBarras(datos = datos, modifier = modifier)


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
