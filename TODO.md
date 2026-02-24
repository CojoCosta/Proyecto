# Plan de Conexión App_Web con apirest

## Información del Sistema
- **Base de datos**: MariaDB (`sql.freedb.tech:3306/freedb_Proyecto`)
- **API REST Java**: `http://localhost:8080/apirest/rest/`
- **App_Web**: ejecutándose desde localhost

## Tareas a Realizar

### 1. Verificar y actualizar URL base en Request.php
-确保 la URL base sea consistente: `http://localhost:8080/apirest/rest`

### 2. Actualizar modelos de App_Web
- [ ] Usuario.php - verificar endpoint de usuarios
- [ ] Publicacion.php - verificar endpoint de publicaciones

### 3. Verificar controladores
- [ ] ControladorUsuarios.php - verificar que las llamadas sean correctas

## Endpoints del API (apirest):
- `GET /usuarios` - Obtener todos los usuarios
- `POST /usuarios` - Registrar usuario
- `GET /usuarios/inicioSesion/{nombre_usuario}/{password}` - Inicio de sesión
- `GET /usuarios/perfil/{nombre_usuario}` - Obtener perfil
- `PUT /usuarios/modificar/{nombre_usuario}` - Modificar usuario
- `GET /publicaciones` - Obtener publicaciones
- `POST /publicaciones` - Crear publicación

## Estado Actual:
Los modelos ya tienen configurada la URL base. Solo se necesitan ajustes menores si hay problemas de conexión.
