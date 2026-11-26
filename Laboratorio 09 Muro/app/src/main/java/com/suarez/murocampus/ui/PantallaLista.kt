package com.suarez.murocampus.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.suarez.murocampus.model.Post
import com.suarez.murocampus.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaLista(
    estadoLista: UiState<List<Post>>,
    busqueda: String,
    usuarioFiltro: Int?,
    onBuscar: (String) -> Unit,
    onFiltrar: (Int?) -> Unit,
    onReintentar: () -> Unit,
    onPostClick: (Int) -> Unit,
    onCreateClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "MuroCampus") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateClick) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Crear publicación")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            OutlinedTextField(
                value = busqueda,
                onValueChange = onBuscar,
                label = { Text("Buscar por título") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = usuarioFiltro == null,
                        onClick = { onFiltrar(null) },
                        label = { Text("Todos") }
                    )
                }
                items(10) { index ->
                    val userId = index + 1
                    FilterChip(
                        selected = usuarioFiltro == userId,
                        onClick = { onFiltrar(userId) },
                        label = { Text("Usuario $userId") }
                    )
                }
            }

            when (estadoLista) {
                is UiState.Cargando -> {
                    Cargando()
                }
                is UiState.Error -> {
                    ErrorConReintento(
                        mensaje = estadoLista.mensaje,
                        onReintentar = onReintentar
                    )
                }
                is UiState.Exito -> {
                    val posts = estadoLista.datos
                    if (posts.isEmpty()) {
                        if (busqueda.isBlank()) {
                            SinPublicaciones()
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Sin resultados para \"$busqueda\"")
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(vertical = 8.dp)
                        ) {
                            items(posts) { post ->
                                TarjetaPost(
                                    post = post,
                                    onClick = { onPostClick(post.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
