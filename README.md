# Proyecto _Leash_

## Descripción general
_Leash_ es una aplicación multiplataforma (web, móvil y escritorio) orientada a la gestión de usuarios y publicaciones. El proyecto incluye una aplicación web, una aplicación móvil, una aplicación de escritorio y un servicio API-REST encargado de la comunicación entre ambas plataformas.

---

## Planificación del proyecto

### 1ª Semana de enero

#### General del proyecto
- Creación del logo de la aplicación Leash.

#### App Web
- Creación de la aplicación web.
- Implementación de las clases:
  - Usuario
  - Publicación
- Creación de las clases:
  - Conexion.php
  - ControladorUsuarios.php

#### App Móvil
- Creación de un diccionario de traducciones:
  - Gallego
  - Inglés
  - Castellano
- Creación de la aplicación móvil.
- Implementación de las clases:
  - Usuario
  - Publicación
---

### 2ª Semana de enero

#### App Web
- Creación de vistas comunes:
  - Header.php
  - Bottom.php
- Creación de vistas principales:
  - InicioSesion.php
  - Muro.php
  - Registro.php

#### App Móvil
- Creación de las vistas y sus respectivos layouts:
  - InicioActivity.java
  - PagInicioActivity.java
  - Registro.java
---

### 3ª Semana de enero

#### App Web
- Estructuración de la aplicación en directorios:
  - conexion
  - controladores
  - modelos
  - recursos
  - vistas
- Añadido de logos de la aplicación Leash en el directorio recursos.

#### App Móvil
- Creación del layout celda_muro.xml para el RecyclerView.
---

### 4ª Semana de enero

#### General del proyecto
- Creación del servicio API REST.

#### API REST
  - Creacion de las clases:
    - Conexion.java
    - Publicacion.java
    - Usuario.java

#### App Móvil
- Creación de las clases:
  - AdaptadorMuro.java
  - APIREST.java
- Añadido de logos e iconos en el directorio drawable.
---

### 1ª Semana de febrero
#### API REST
- Creacion en la clase Conexion.java las funciones correspondientes para establecer la conexión con la base de datos y llamar a estas funciones desde el resto de clases sin repetir los datos de acceso en cada clase.
- Creación en la clase Usuario.java de las funciones para insertar, obtener todos los datos y obtener datos de inicio de sesion de un usuario.
- Creacion en la clase Publicacion.java de la funcion para subir una publicacion.
#### App Móvil
- Creación de las vistas y sus respectivos loyouts:
  - PerfilUsuarioActivity.java
---
## Tecnologías utilizadas
- Aplicación web: PHP, HTML, CSS
- Aplicación móvil: Java, XML (Android)
- Aplicación escritorio: C#
- Backend: API REST
- Idiomas: Gallego, Inglés y Castellano
---

### 2ª Semana de febrero
#### App Móvil
- Creación de las vistas y sus respectivos loyouts:
  - ModificarUsuarioActivity.java

## Autor
Diego Costa Pereira
