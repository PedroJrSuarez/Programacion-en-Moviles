# Registro de Prompts - Laboratorio 08 (SaludPlusCitas)

Este documento registra los prompts utilizados durante la interacción con el asistente de IA, el resumen de la respuesta y las correcciones o ajustes realizados.

---

### Prompt 1: Consulta sobre errores visuales (líneas rojas en Repositorio.kt)
- **Prompt del usuario:** 
  > *[Contexto en Repositorio.kt con imagen adjunta]*: "porque sale asi?"
- **Respuesta resumida de la IA:** 
  Se explicó que el proyecto compilaba perfectamente (`BUILD SUCCESSFUL`) y que las líneas rojas en el editor de Android Studio se debían a un problema de caché o indexación del IDE, sugiriendo sincronizar con Gradle o invalidar cachés.
- **Qué se tuvo que corregir / ajustar:** 
  Se confirmó mediante `gradle_build` que no existían errores de sintaxis ni de compilación en el código fuente.

---

### Prompt 2: Verificación general del proyecto
- **Prompt del usuario:** 
  > "revisa todo se ve rojit"
- **Respuesta resumida de la IA:** 
  Se realizó una sincronización y compilación de prueba con Gradle (`gradle_sync` y `gradle_build`), verificando que todas las dependencias y modelos estaban correctos.
- **Qué se tuvo que corregir / ajustar:** 
  Se reafirmó que el comportamiento visual era exclusivo del IDE y se guio al usuario en los pasos para refrescar Android Studio.

---

### Prompt 3: Cambio de paquete (Namespace y estructura de carpetas)
- **Prompt del usuario:** 
  > "cambio la carpeta com.saludplus.citas tiene que ser com.suarez.saludpluscitas"
- **Respuesta resumida de la IA:** 
  Se ejecutó una refactorización integral para actualizar el `namespace` y `applicationId` en `app/build.gradle.kts`, crear la nueva estructura de directorios `com/suarez/saludpluscitas`, mover los 28 archivos fuente de Kotlin, actualizar las declaraciones de `package` y las importaciones cruzadas, y verificar la compilación exitosa.
- **Qué se tuvo que corregir / ajustar:** 
  Se aseguró que ninguna importación interna quedara apuntando al paquete anterior (`com.saludplus.citas`), manteniendo la integridad de la navegación y modelos.

---

### Prompt 4: Solicitud de commits y documentación
- **Prompt del usuario:** 
  > "3. Mínimo 3 commits descriptivos. 4. Documenta cada prompt usado en un archivo PROMPTS.md ... 5. Al terminar: git push origin mejora-ia. PRIMERO DAME LOS 3 COMMITS DESCRIPTIVOS..."
- **Respuesta resumida de la IA:** 
  Se estructuraron los commits requeridos, se generó este archivo `PROMPTS.md` y se prepararon los comandos para realizar el `git push origin mejora-ia`.
- **Qué se tuvo que corregir / ajustar:** 
  Se organizaron los cambios en commits atómicos y descriptivos.
