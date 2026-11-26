package com.suarez.murocampus.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.suarez.murocampus.model.Comentario
import com.suarez.murocampus.model.DetalleUi
import com.suarez.murocampus.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalle(
    estadoDetalle: UiState<DetalleUi>,
    eliminarEstado: UiState<Unit>?,
    onBackClick: () -> Unit,
    onReintentar: () -> Unit,
    onEliminar: () -> Unit
) {
    var mostrarConfirmacion by remember { mutableStateOf(false) }

    if (mostrarConfirmacion) {
        AlertDialog(
            onDismissRequest = { mostrarConfirmacion = false },
            title = { Text("Eliminar publicación") },
            text = { Text("¿Quieres eliminar esta publicación?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarConfirmacion = false
                        onEliminar()
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarConfirmacion = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detalle de Publicación") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (estadoDetalle) {
                is UiState.Cargando -> {
                    Cargando(mensaje = "Cargando detalle y comentarios…")
                }
                is UiState.Error -> {
                    ErrorConReintento(
                        mensaje = estadoDetalle.mensaje,
                        onReintentar = onReintentar
                    )
                }
                is UiState.Exito -> {
                    val detalle = estadoDetalle.datos
                    val post = detalle.post
                    val comentarios = detalle.comentarios

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Text(
                                text = "Usuario ${post.userId}",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = post.title,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = post.body,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = { mostrarConfirmacion = true },
                                enabled = eliminarEstado !is UiState.Cargando &&
                                    eliminarEstado !is UiState.Exito,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.error
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Eliminar publicación")
                            }
                            when (eliminarEstado) {
                                is UiState.Cargando -> {
                                    CircularProgressIndicator()
                                }
                                is UiState.Error -> {
                                    Text(
                                        text = eliminarEstado.mensaje,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                    TextButton(onClick = onEliminar) {
                                        Text("Reintentar eliminación")
                                    }
                                }
                                is UiState.Exito -> {
                                    Text(
                                        text = "Eliminación aceptada. JSONPlaceholder solo la simula.",
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                                null -> Unit
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            HorizontalDivider()
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Comentarios (${comentarios.size})",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        items(comentarios) { comentario ->
                            ComentarioItem(comentario = comentario)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ComentarioItem(comentario: Comentario) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = comentario.email,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comentario.body,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
