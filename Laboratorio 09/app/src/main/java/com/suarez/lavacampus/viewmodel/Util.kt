package com.suarez.lavacampus.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

fun <T> Flow<T>.aEstado(scope: CoroutineScope, inicial: T): StateFlow<T> =
    stateIn(scope, SharingStarted.WhileSubscribed(5000), inicial)
