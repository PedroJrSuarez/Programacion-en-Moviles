Laboratorio 03: Registro de Producto

**Estudiante:** 
**Curso:** Programación en Móviles

## Capturas de Pantalla

1. Pantalla Inicial
![Pantalla Inicial.png](../Pantalla%20Inicial.png)

2. Producto Registrado
![Producto Registrado.png](../Producto%20Registrado.png)

## Pregunta de Reflexión

**¿Qué pasaría si declaras las variables de los campos sin remember?**
Si no se utiliza remember, en cada recomposición (redibujado) del composable la variable se reinicia a su valor inicial (""). Esto provocará que al intentar escribir una letra, la pantalla se redibuje y borre inmediatamente el texto ingresado, impidiendo el registro de datos.