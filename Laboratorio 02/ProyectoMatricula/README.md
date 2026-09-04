# Sistema de Control de Matrícula

Aplicación de consola en **Kotlin** desarrollada en **Android Studio** (entorno macOS) para gestionar el registro de matrícula de estudiantes, calcular el costo acumulado por créditos, categorizar la carga académica e identificar la modalidad de pago en cuotas.

---

## Funcionalidades

- **Captura interactiva de datos:** Permite ingresar el nombre del estudiante, la cantidad de cursos a matricular y el precio fijo por crédito.
- **Registro de asignaturas:** Solicita dinámicamente el nombre y número de créditos para cada curso.
- **Evaluación de Carga Académica:** Clasifica la condición del alumno según el total de créditos acumulados:
  - **Hasta 12 créditos:** Malla regular.
  - **De 13 a 18 créditos:** Carga completa.
  - **Más de 18 créditos:** Requiere autorización.
- **Plan de Financiamiento:** Determina la cantidad de cuotas y el monto de cada una según el costo total del ciclo:
  - **Mayor a S/ 2,500.00:** Se financia en **3 cuotas**.
  - **Menor o igual a S/ 2,500.00:** Se financia en **2 cuotas**.
- **Reporte Consolidado:** Muestra un resumen detallado con formato tabular en la consola.

---

## Tecnologías Utilizadas

- **Lenguaje:** Kotlin
- **IDE:** Android Studio
- **Sistema Operativo:** macOS
- **Control de Versiones:** Git & GitHub

---

## Historial de Commits

El desarrollo del proyecto está estructurado en 3 commits progresivos:

1. `feat: capturar datos de entrada del estudiante y cursos`  
   *Implementación de la lectura de datos mediante Scanner y estructuras de datos para almacenar los cursos.*
2. `feat: agregar calculo de creditos, carga academica y cuotas`  
   *Lógica de negocio para sumar créditos, evaluar el tipo de carga académica y calcular la estructura de pago.*
3. `feat: dar formato e imprimir el reporte final en consola`  
   *Diseño final de la salida por consola formateada con columnas y encabezados.*

---

## Programa en Funcionamiento

<img width="438" height="346" alt="image" src="https://github.com/user-attachments/assets/e374f904-7326-4040-9bae-dc8c6c06fbff" />

## Ejemplo de Salida en Consola

```text
========================================
RESULTADO FINAL
ESTUDIANTE: Pedro Suarez
========================================
curso                | creditos | costo     
----------------------------------------
Matematica           | 5        | S/ 750.00 
Programacion         | 4        | S/ 600.00 
BaseDatos            | 5        | S/ 750.00 
----------------------------------------
CURSO MATRICULADO : 3
TOTAL DE CREDITO  : 14
TOTAL A PAGAR     : S/ 2100.00
Carga Academica   : Carga completa
Forma de pago     : 2 cuotas de S/ 1050.00 cada una
========================================

