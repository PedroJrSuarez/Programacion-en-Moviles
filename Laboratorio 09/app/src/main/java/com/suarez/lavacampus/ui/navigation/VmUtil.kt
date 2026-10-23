package com.suarez.lavacampus.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

/** Crea/recupera un ViewModel con constructor propio (sin depender de LavaViewModelFactory). */
@Composable
inline fun <reified VM : ViewModel> vmDe(key: String? = null, crossinline crear: () -> VM): VM =
    viewModel(
        key = key?.let { "${VM::class.java.name}:$it" },
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T = crear() as T
        }
    )
