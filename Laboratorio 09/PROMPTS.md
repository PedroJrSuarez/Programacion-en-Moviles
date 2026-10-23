# Prompts utilizados

> Fase 2 (rama `mejora-ia`). Se uso Gemini en Android Studio con un solo prompt que pide las 3 mejoras, cada una
> separada para poder hacer un commit por mejora.

## Prompt 1

**Texto enviado a Gemini:**

```
Tengo una app Android en Kotlin con Jetpack Compose (Material 3, sin XML), arquitectura MVVM + Repository, Room 2.6.1
(version 1 de la base), DataStore y Navigation Compose. Paquete: com.suarez.lavacampus. Es LavaCampus, la app de una
lavanderia universitaria con dos perfiles (Estudiante y Administrador).

REGLAS DE ARQUITECTURA (obligatorias):
- La View (Composable) solo conoce su ViewModel: no usa DAO ni Repository ni reglas de negocio.
- El ViewModel solo conoce la interface LavaRepository y expone StateFlow. Las reglas de negocio viven en el ViewModel.
- Si agregas una funcion al Repository, la declaras PRIMERO en la interface LavaRepository y luego la implementas en
  LavaRepositoryRoom (que solo delega en los DAOs). Lecturas que deben refrescar la pantalla = Flow (sin suspend);
  escrituras = suspend.
- Si cambias una entidad, sube la version de la base y agrega una Migration (no uses fallbackToDestructiveMigration).
- Las pantallas usan los componentes de ui/components a traves de Adaptadores.kt. No uses XML ni librerias externas.

Implementa estas 3 mejoras, en este orden, cada una separada para poder hacer un commit por mejora:

MEJORA 1 - Pedidos atrasados
- La bandeja del administrador marca en rojo (borde rojo y texto "ATRASADO") los pedidos cuya fechaEntrega (String
  aaaa-MM-dd) es menor que hoy y cuyo estado es distinto de ENTREGADO y CANCELADO.
- El panel del administrador muestra una tarjeta de alerta con la cantidad de pedidos atrasados y un boton "Ver".
- Capas: consulta en PedidoDao (Flow<List<Int>> con los ids), funcion en LavaRepository + LavaRepositoryRoom,
  StateFlow en BandejaViewModel (ids atrasados) y en PanelViewModel (cantidad). La View solo lo muestra.

MEJORA 2 - Descuento por volumen
- Si en el pedido hay un servicio por KG con mas de 5 kg, se aplica 10 % de descuento sobre esa linea.
- El resumen de Nuevo pedido muestra Subtotal, "Descuento por volumen (10 %)" y el Total final; el detalle del pedido
  tambien muestra el descuento. El descuento se guarda en el pedido (columna descuento en la tabla pedidos) y
  pedido.total guarda el total ya con descuento.
- Capas: entidad Pedido (+ columna descuento con defaultValue 0.0), LavaDatabase version 2 con Migration 1->2,
  crearPedido recibe el descuento (declarado primero en la interface), NuevoPedidoViewModel calcula subtotal,
  descuento y total, y las pantallas lo muestran. El ingreso por servicio del panel debe seguir cuadrando con los
  ingresos de la semana: prorratea el subtotal de cada detalle con total / (total + descuento).

MEJORA 3 - Repetir un pedido
- En Mis pedidos > Historial, cada pedido tiene un boton "Pedir de nuevo" que abre Nuevo pedido con las mismas
  lineas ya cargadas y con los precios ACTUALES. Los servicios que ahora estan inactivos no se agregan y se avisa con
  un mensaje. Las cantidades se ajustan a las reglas (KG de 1.0 a 10.0 en pasos de 0.5; PRENDA/UNIDAD enteros de 1 a
  30). El limite de 3 pedidos activos se sigue validando al confirmar.
- Capas: NuevoPedidoViewModel (recibe el id del pedido a repetir, lo lee con repo.observarPedido), navegacion con
  argumento opcional "repetir" en NavLava, y MisPedidosScreen con el boton. No hace falta tocar el Repository.

Muestrame solo los archivos que cambian, completos, agrupados por mejora, e indica si algo no respeta MVVM.
```

**Que genero la IA:**
- Mejora 1: consulta `observarIdsAtrasados(hoy)` en `PedidoDao`, su declaracion en `LavaRepository` y delegacion en
  `LavaRepositoryRoom`; `idsAtrasados` en `BandejaViewModel` y `atrasados` en `PanelViewModel`; borde rojo + texto
  "ATRASADO" en la bandeja y tarjeta de alerta en el panel.
- Mejora 2: columna `descuento` en `Pedido`, base en version 2 con `MIGRACION_1_2`, parametro `descuento` en
  `crearPedido`, calculo de subtotal/descuento/total en `NuevoPedidoViewModel` y filas de descuento en el resumen y en
  el detalle del pedido.
- Mejora 3: parametro `repetirPedidoId` en `NuevoPedidoViewModel`, ruta `nuevo_pedido?repetir={repetir}` en
  `NavLava` y boton "Pedir de nuevo" en el historial de `MisPedidosScreen`.

**Que tuve que corregir yo:**
- (Completa con lo que realmente corrigiste. Puntos tipicos que conviene revisar:)
- Poner `@ColumnInfo(defaultValue = "0.0")` en `descuento` para que coincida con la Migration (si no, Room falla al abrir
  una base v1 existente).
- Prorratear el subtotal por servicio en el panel con `total / (total + descuento)`; sin eso los ingresos por servicio
  no cuadraban con los ingresos de la semana.
- Declarar `observarIdsAtrasados` primero en la interface y no directo en la implementacion.
- Usar un argumento opcional en la ruta para no romper las llamadas existentes a Nuevo pedido.

**Respeta MVVM?:** Si. Las Views solo leen StateFlow y avisan eventos; las reglas (umbral de 5 kg, 10 %, ajuste de
cantidades al repetir, limite de 3 pedidos) viven en los ViewModels; el Repository solo delega en los DAOs y la
interface se amplio antes que la implementacion.

## Reflexion (5 lineas)
- Que mejoro la IA y que no? Resolvio rapido el codigo repetitivo (consultas, StateFlow, filas de la UI), pero no
  anticipo por si sola el detalle de la migracion de Room ni la coherencia de los reportes con el descuento.
- Que error cometio? Dejar el cambio de esquema sin default ni migracion y calcular ingresos por servicio con el
  subtotal bruto, lo que descuadraba el panel.
- Que aprendi sobre MVVM, Room y reglas de negocio? Que cada cambio de entidad exige subir la version y migrar, que las
  reglas deben quedarse en el ViewModel aunque la IA las ponga en la pantalla, y que un dato nuevo (descuento) afecta
  tambien a las consultas de reportes.
- Que haria distinto? Pedirle a la IA primero el plan de capas por mejora y revisar la interface antes del codigo.
- Que valor tiene el Repository como interface? Permite agregar funciones sin tocar ViewModels existentes y cambiar
  Room por un servicio web mas adelante.
