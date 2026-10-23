# LavaCampus

App móvil de la lavandería de un campus universitario (Kotlin + Jetpack Compose + Material 3, Room 2.6.1, DataStore, Navigation Compose).
Paquete: `com.suarez.lavacampus` · Patrón: **MVVM + Repository (interface)** · Datos 100 % ficticios (Ley 29733).

Dos perfiles en el mismo dispositivo y la misma base Room:
- **Estudiante**: arma pedidos (kilos / prendas), ve total y fecha de entrega (sin domingos), sigue el estado y recoge en casillero.
- **Administrador**: avanza estados (Recibido → En proceso → Listo → Entregado), asigna casillero, cobra, gestiona tarifas y clientes.

## Árbol de paquetes
```
com.suarez.lavacampus
├── MainActivity.kt · LavaApp.kt
├── data
│   ├── local
│   │   ├── entity   Servicio, Usuario, Pedido, DetallePedido
│   │   ├── dao      ServicioDao, UsuarioDao, PedidoDao, DetalleDao
│   │   ├── relation DetalleConServicio, PedidoConDetalle
│   │   ├── LavaDatabase.kt · DatosIniciales.kt
│   ├── repository   LavaRepository (interface) · LavaRepositoryRoom
│   └── prefs        PreferenciasStore (DataStore)
├── model            Rol, EstadoPedido, Unidad, LineaPedido, IngresoServicio, ClienteResumen, Casilleros, Extensiones
├── viewmodel        Sesion, Inicio, NuevoPedido, MisPedidos, DetallePedido, Panel, Bandeja, GestionPedido, Servicios, Clientes
└── ui
    ├── theme · components (+ graficos, Adaptadores)
    ├── navigation   NavLava, VmUtil
    ├── estudiante   Login, Inicio, NuevoPedido, DetallePedido, MisPedidos, Tarifas, Perfil
    └── administrador Panel, Bandeja, GestionPedido, Servicios, FormularioServicio, Clientes, Ajustes
```

## Diagrama MVVM
```
VIEW (Composable)
 │ eventos ▲ collectAsState()
 ▼         │
VIEWMODEL (StateFlow + reglas de negocio)
 │ llama   ▲ Flow / resultados
 ▼         │
LavaRepository (interface)
 │ la implementa
 ▼
LavaRepositoryRoom ──► DAO ──► SQLite (lavacampus.db)
DataStore (solo preferencias) ──► ViewModel
```

## Capturas
| Estudiante | Administrador |
|---|---|
| ![Inicio](capturas/est_inicio.png) | ![Panel](capturas/adm_panel.png) |
| ![Nuevo pedido](capturas/est_nuevo.png) | ![Bandeja](capturas/adm_bandeja.png) |
| ![Detalle](capturas/est_detalle.png) | ![Gestión](capturas/adm_gestion.png) |

## Decisiones de diseño
- Se agregaron 2 funciones al contrato `LavaRepository` (`obtenerUsuario`, `usuarioPorRol`) para poder **restaurar la sesión guardada** (Splash) y **cambiar de perfil** sin credenciales fijas.
- `Adaptadores.kt` concentra la llamada a los componentes entregados; si cambia su firma, se corrige en un solo archivo.
- Editar un servicio usa `@Update` (no `REPLACE`) para no romper las claves foráneas de los detalles.

## Para pensar
**¿Por qué un pedido tiene detalles en otra tabla?**
Porque un pedido puede tener varios servicios. Guardar cada línea como fila de `detalles_pedido` evita columnas repetidas (servicio1, servicio2…), respeta la primera forma normal y permite consultas como el ingreso por servicio con `GROUP BY`. Es la relación uno a muchos clásica.

**¿Por qué guardar `precioUnitario` y `subtotal` en el detalle?**
Es una "foto" del momento. Si el kilo sube de precio mañana, los pedidos antiguos conservan lo que costaron y los ingresos del panel no cambian.

**¿Esta función cambia datos una sola vez o debe avisar cuando cambian?**
`cambiarEstado` / `crearPedido` cambian datos una sola vez → `suspend`. Las lecturas que deben refrescar la pantalla (lista de pedidos, panel) avisan cada cambio → `Flow` sin `suspend`. Se complementan.

**¿Por qué `crearPedido` va en una transacción?**
Para que o se guarden el pedido y todas sus líneas, o no se guarde nada. Si falla un detalle, `withTransaction` deshace también el pedido y nunca queda un pedido sin detalles.
