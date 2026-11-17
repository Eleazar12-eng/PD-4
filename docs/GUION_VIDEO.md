# Guion del video (máx. 5 minutos)

Dura unos 4:45 a ritmo normal (unas 640 palabras). Lo que va en *cursiva entre corchetes* es lo que debes mostrar en pantalla.

**Antes de grabar:** ten abiertos en VS Code `Main.java`, `ConexionBD.java`, `StandDAO.java`, `StandDAOJdbc.java`, `StandControlador.java`, `VistaStands.java` y `Stand.java`; ten también las imágenes `docs/diagramas/capas.png` y `er.png`, y una terminal en la raíz del proyecto con el código ya compilado.

---

## 1. Introducción (0:00 – 0:20)

*[Pantalla: la imagen de capas, `docs/diagramas/capas.png`]*

"Hola, soy [tu nombre]. En este video explico el código que lee las tablas de Stands, Editoriales y Reservaciones desde una base de datos con JDBC, usando el patrón MVC más una capa de base de datos. Voy a recorrer cada capa y al final muestro el programa funcionando."

---

## 2. Las tres tablas y sus relaciones (0:20 – 0:55)

*[Pantalla: `docs/diagramas/er.png`]*

"Primero, los datos. Hay tres tablas. Stand, con número, dimensiones, costo por metro cuadrado y estado. Editorial, con id, nombre, correo y si está aprobada. Y Reservación, que une a las otras dos: tiene una llave foránea a la editorial y otra al stand, además de las fechas y los montos. Una editorial puede tener muchas reservaciones, y un stand también. La base de datos impone reglas: el estado solo acepta cuatro valores y las llaves foráneas impiden reservar algo que no existe."

---

## 3. Modelo (0:55 – 1:20)

*[Pantalla: `Stand.java` y luego `Reservacion.java`]*

"La primera capa es el modelo: una clase por tabla. Stand calcula su propia área y su costo, en vez de guardarlos. Reservación guarda los objetos Editorial y Stand completos, no solo sus números. El modelo no sabe nada de JDBC ni de consola: solo representa los datos."

---

## 4. Capa de base de datos (1:20 – 3:00)

*[Pantalla: `ConexionBD.java`]*

"Luego la capa de base de datos. ConexionBD es el único lugar que conoce la URL, el usuario y la clave. Por defecto usa H2 en memoria, y se puede cambiar con una propiedad del sistema sin tocar el código. Tiene un método probar, que abre una conexión y lee el nombre y la versión del motor: eso es lo que demuestra que la conexión funciona.

*[Pantalla: `StandDAO.java`]*

Para leer cada tabla uso el patrón DAO. Esta es la interfaz: solo dos métodos de lectura, listar todos y buscar por número. Los controladores dependen de esta interfaz, no de JDBC.

*[Pantalla: `StandDAOJdbc.java`, señalando cada parte mientras la nombras]*

Y esta es la implementación con JDBC. Abro la conexión, preparo la consulta con PreparedStatement, la ejecuto y recorro el ResultSet fila por fila. Todo está en un try-with-resources, así que la conexión, la consulta y el resultado se cierran solos aunque haya un error. El método mapear convierte cada fila en un objeto Stand: eso es justo lo que haría un ORM por nosotros. Para buscar por número uso un parámetro con signo de interrogación, lo que evita la inyección SQL.

*[Pantalla: `ReservacionDAOJdbc.java`, la consulta]*

En Reservación la consulta hace un JOIN con Editorial y Stand, para armar el objeto completo en una sola consulta.

Si algo falla, el DAO lanza una BDException, que envuelve a la excepción de SQL, para que las capas de arriba no dependan de java punto sql."

---

## 5. Controlador y vista (3:00 – 3:50)

*[Pantalla: `StandControlador.java`]*

"El controlador hace una sola cosa: pedirle los stands al DAO y entregárselos a la vista. Fíjense que recibe la interfaz StandDAO y no la clase JDBC. Si la base de datos falla, captura la BDException y le pide a la vista que muestre el error. Tengo un controlador por tarea: stands, editoriales, reservaciones y otro para verificar la conexión.

*[Pantalla: `VistaStands.java`]*

La vista solo presenta: recibe una lista de stands y la imprime como tabla. No conoce ni la base de datos ni al controlador. Como ven, el texto no lleva acentos, para que se vea bien en la consola de Windows."

---

## 6. Demostración (3:50 – 4:45)

*[Pantalla: `Main.java` un momento, luego la terminal]*

"Main arma las capas: crea la conexión, los DAO, las vistas y se los pasa a los controladores. Lo ejecuto."

*[Escribe y ejecuta:]*
```
java -cp "out;lib/*" filey.Main
```

*[Señala cada bloque conforme aparece]*

"Primero, la conexión exitosa, con la URL, el motor H2 y su versión. Luego se crean las tablas y los datos de ejemplo. Aquí está la tabla de Stands, ordenada por área, con su costo y su estado. Aquí las Editoriales, con la columna de aprobada. Y aquí las Reservaciones, que muestran el nombre de la editorial y el número del stand gracias al JOIN, con lo pendiente por pagar. Al final, una consulta por clave a cada tabla, y una que no existe, el stand 999, para mostrar que se maneja ese caso."

---

## 7. Cierre (4:45 – 5:00)

*[Pantalla: la imagen de capas]*

"En resumen: el modelo guarda los datos, la capa de BD habla con la base con JDBC, el controlador pide y entrega, y la vista presenta. Cada capa tiene una sola responsabilidad. El código, los diagramas y la documentación están en el repositorio. Gracias."

---

## Consejos para grabar

- **Ensaya cronometrando.** Si pasas de 5:00, acorta la sección 5 (muestra solo el controlador de stands) o la 2.
- **Agranda la fuente** de VS Code y la terminal (`Ctrl` + `+`) y limpia con `cls` antes de ejecutar.
- **Si el texto de la salida se corta en la terminal**, ensancha la ventana: la tabla de reservaciones mide unos 95 caracteres.
- **Al subir el video, asegura el acceso:**
  - *YouTube:* visibilidad **No listado**.
  - *Google Drive:* Compartir → **Cualquier persona con el enlace** → rol **Lector**.
  - Abre el enlace en una ventana de **incógnito** para confirmar que se ve sin iniciar sesión.
  - Pega el enlace en el `README.md`, en la sección "Video".
