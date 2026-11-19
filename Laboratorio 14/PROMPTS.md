# PROMPTS.md · Fase 2 (rama `mejora-ia`)

Registro del uso de Gemini en Android Studio para el desarrollo de las mejoras de la Fase 2 en CentroTareas.

| Prompt que escribí | Qué respondió Gemini | Qué cambié o descarté | Por qué |
|---|---|---|---|
| «Mueve `tareaLarga()` al Repository como `fun tareaConProgreso(): Flow<Float>` usando `flow { emit(...) }` y `flowOn(Dispatchers.Default)`. El ViewModel solo recolecta y actualiza `progreso`. No uses GlobalScope ni runBlocking.» | Propuso crear un builder `flow { ... }` en el Repository emitiendo el progreso cada 40ms con `delay(40)` y aplicandotransferencia de hilo con `flowOn(Dispatchers.Default)`. En el ViewModel, sugirió consumir el Flow mediante `.collect { _progreso.value = it }` dentro de `viewModelScope.launch`. | Quité lógica redundante de hilos dentro del ViewModel; mantuve el bloque `try/finally` para asegurar que `_corriendo.value = false` se restablezca siempre. | `flowOn` maneja eficientemente el cambio de contexto del productor. El `try/finally` garantiza que el botón Iniciar se reactive incluso si el usuario cancela la tarea. |
| «Agrega reintento automático (3 intentos, espera 1 s, 2 s, 4 s) a las cargas del ViewModel. Reintenta solo ante IOException y no rompas la cancelación.» | Sugirió una función suspend genérica `conReintentos` usando un bucle `repeat` con retraso exponencial (`delay(espera); espera *= 2`), capturando `IOException` y exponiendo el número de intento actual. | Añadí captura explícita de `CancellationException` al inicio de los bloques `catch` para relanzarla inmediatamente antes de evaluar `IOException`. | Capturar de forma genérica `Exception` tragaría la cancelación de la corrutina impidiendo detener tareas canceladas. Solo los errores de E/S transitorios deben reintentarse. |
| «Crea una tarjeta que acumule los tiempos de "Una por una" y "A la vez" y muestre último, promedio y diferencia. Mantén el estado en el ViewModel con StateFlow.» | Propuso la data class `Comparacion` conteniendo listas de `Long` para cada modo, actualizándolas con `_comparacion.update { ... }` y mostrando estadísticas en una nueva tarjeta `TarjetaComparacion`. | Mantuvo las listas brutas de tiempos en el ViewModel y delegó el cálculo de promedios y porcentajes de mejora a funciones auxiliares reactivas en la capa de UI. | `_comparacion.update` ofrece actualización atómica thread-safe en StateFlow, y almacenar datos crudos permite recalcular estadísticas de forma flexible sin recalcular en el repositorio. |

## Prompts de apoyo (opcionales utilizados)
- «Revisa mi `TareasViewModel` y dime si hay alguna `CancellationException` que se esté tragando.»
- «¿Mi `conReintentos` reintenta también cuando el usuario cancela? Explícame por qué sí o no.»

## Reglas verificadas en el código final
- Sin `GlobalScope`, `runBlocking` ni `Thread.sleep` (`git grep` limpio).
- `CancellationException` siempre se relanza.
- La arquitectura MVVM + Repository se mantiene estrictamente: la View no lanza corrutinas.
