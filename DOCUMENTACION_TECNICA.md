# Technical Documentation - Leash Project

## 1. Summary
Leash is a multi-platform system with four main components:
1. REST API in Java (Jersey + Servlet) packaged as a WAR.
2. Web App in PHP (simple MVC) that consumes the API.
3. Android Mobile App (Java) that consumes the API.
4. Windows Desktop App (C# WinForms) that consumes the API.

The API exposes user and post operations and connects to a MariaDB database.

## 2. Repository Structure
- `apirest/`: Java REST API (Maven + WAR).
- `App_Web/`: PHP web app (controllers, models, views).
- `App_Movil/`: Android app (Gradle).
- `App_Escritorio/`: Windows app (WinForms, .NET Framework 4.7.2).

## 3. Components and Responsibilities

### 3.1. REST API (Java)
Location: `apirest/`

Technology:
- Java 8 (source/target 1.8 in Maven).
- Jersey 3.x (JAX-RS).
- MariaDB JDBC.
- Deployed as WAR in a Servlet container (e.g., Tomcat).

Endpoints (base: `/apirest/rest`):
- `GET /usuarios` -> list users.
- `POST /usuarios` -> create user.
- `POST /usuarios/inicioSesion` -> login by JSON.
- `POST /usuarios/inicioSesion/{nombre_usuario}/{password}` -> legacy login.
- `GET /usuarios/inicioSesion/{nombre_usuario}/{password}` -> legacy login (GET).
- `GET /usuarios/perfil/{nombre_usuario}` -> user data.
- `PUT /usuarios/modificar/{nombre_usuario}` -> update user.
- `DELETE /usuarios/eliminar/{nombre_usuario}` -> delete user.
- `GET /publicaciones` -> list posts (desc order).
- `POST /publicaciones` -> create post.

Key configuration:
- Jersey Servlet in `apirest/src/main/webapp/WEB-INF/web.xml` with `url-pattern` `/rest/*`.
- DB connection hardcoded in `FuncionesUsuario.java` and `FuncionesPublicacion.java`:
  - Host: `sql.freedb.tech`
  - DB: `freedb_Proyecto`
  - User: `freedb_DiegoCosta`
  - Password: `2?#T#@qg5S&2sEr`

Models (classes):
- `Usuario`: `id_usuario`, `nombre_usuario`, `nombre`, `apellidos`, `email`, `fecha_nacimiento`, `password`.
- `Publicacion`: `id_publicacion`, `id_usuario`, `nombre_usuario`, `contenido`, `fecha_publicacion`.

### 3.2. Web App (PHP)
Location: `App_Web/`

Structure:
- `conexion/Request.php`: cURL wrapper for the API.
- `modelos/Usuario.php` and `modelos/Publicacion.php`: DTO + API calls.
- `controladores/ControladorUsuarios.php`: handles login, register, and posts.
- `vistas/`: UI (InicioSesion, Registro, Muro, Perfil, Editar, etc.).

API configuration:
- `Request.php` points to `http://localhost:8080/apirest/rest`.

Flows:
- Register: POST to `/usuarios`.
- Login: tries POST `/usuarios/inicioSesion` and fallback to legacy route.
- Post: POST `/publicaciones` with `id_usuario`, `nombre_usuario`, `contenido`.

### 3.3. Mobile App (Android)
Location: `App_Movil/`

Technology:
- Android (Java).
- `compileSdk 36`, `minSdk 26`, `targetSdk 34`.

API configuration:
- `APIREST.java` uses:
  - `http://10.0.2.2:8080/apirest/rest/usuarios/`
  - `http://10.0.2.2:8080/apirest/rest/publicaciones/`

Main screens:
- `InicioActivity` (login).
- `RegistroActivity` (create user).
- `MuroActivity` (list posts).
- `NuevaPublicacionActivity`, `PerfilUsuarioActivity`, `ModificarActivity`.

Permissions:
- `INTERNET`.
- `usesCleartextTraffic="true"` (HTTP without TLS).

### 3.4. Desktop App (WinForms)
Location: `App_Escritorio/Leash/`

Technology:
- .NET Framework 4.7.2.
- WinForms.

API configuration:
- `App.config`: `ApiBaseUrl = http://localhost:8080/apirest/rest`.

API client:
- `Api/ApiClient.cs`: login, register, profile, posts.
- Fallbacks for legacy login and `id_usuario` resolution.

## 4. Data Model (based on queries)
Expected tables in MariaDB:

`usuarios`:
- `id_usuario` (int, PK)
- `nombre_usuario` (varchar)
- `nombre` (varchar)
- `apellidos` (varchar)
- `email` (varchar)
- `fecha_nacimiento` (date or varchar)
- `password` (varchar)

`publicaciones`:
- `id_publicacion` (int, PK)
- `id_usuario` (int, FK)
- `nombre_usuario` (varchar)
- `contenido` (text)
- `fecha_publicacion` (date)

## 5. Build and Run

### 5.1. REST API
1. Requirements: JDK 8+, Maven, Servlet container (Tomcat).
2. Build WAR:
```
mvn clean package
```
3. Deploy: copy `apirest/target/apirest.war` to the container.
4. Expected base URL: `http://localhost:8080/apirest/rest`.

### 5.2. Web App
1. Requirements: PHP + web server (Apache/Nginx).
2. Configure local host to serve `App_Web/`.
3. Ensure the API is accessible at `http://localhost:8080/apirest/rest`.

### 5.3. Mobile App
1. Open `App_Movil/` in Android Studio.
2. Run on emulator or device.
3. For emulator, `10.0.2.2` points to the local host.

### 5.4. Desktop App
1. Open `App_Escritorio/Proyecto Escritorio.sln` in Visual Studio.
2. Build and run.
3. Ensure `ApiBaseUrl` in `App.config`.

## 6. API - Basic Contracts
Main JSON examples:

Create user (`POST /usuarios`):
```
{
  "nombreUsuario": "dcosta",
  "nombre": "Diego",
  "apellidos": "Costa",
  "email": "diego@ejemplo.com",
  "password": "1234",
  "fechaNacimiento": "2000-01-01"
}
```

Login (`POST /usuarios/inicioSesion`):
```
{
  "nombreUsuario": "dcosta",
  "password": "1234"
}
```

Create post (`POST /publicaciones`):
```
{
  "id_usuario": 1,
  "nombre_usuario": "dcosta",
  "contenido": "Hola mundo"
}
```

## 7. Technical Notes and Risks
- DB credentials are hardcoded in the API and the web app.
- Passwords are stored/used in plain text (no hashing).
- Legacy login sends password in URL (GET/POST), security risk.
- The PHP model includes operations (`PUT`/`DELETE` for posts) that do not exist in the Java API.
- HTTP communication is clear text (no TLS).
