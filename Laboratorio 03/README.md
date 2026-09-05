Laboratorio 03: Registro de Producto

**Estudiante:** 
**Curso:** Programación en Móviles

## Capturas de Pantalla

1. Pantalla Inicial
<img width="476" height="1107" alt="Pantalla Inicial" src="https://github.com/user-attachments/assets/e125aea6-6b97-4ee2-8233-5d1ccb3f8f68" />

2. Producto Registrado
<img width="477" height="1108" alt="Producto Registrado" src="https://github.com/user-attachments/assets/f71e3894-6754-492f-9e8d-4a0d357cf12e" />


## Pregunta de Reflexión

**¿Qué pasaría si declaras las variables de los campos sin remember?**
Si no se utiliza remember, en cada recomposición (redibujado) del composable la variable se reinicia a su valor inicial (""). Esto provocará que al intentar escribir una letra, la pantalla se redibuje y borre inmediatamente el texto ingresado, impidiendo el registro de datos.
