<?php
require_once '../modelos/Usuario.php';
require_once '../modelos/Publicacion.php';

// Verificar si se ha enviado el formulario de registro
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['accion'])) {
    
    // Acción de Registro
    if ($_POST['accion'] === 'registrar') {
        $usuario = new Usuario();
        
        // Establecer los valores del usuario
        $usuario->setNombreUsuario($_POST['nombre_usuario']);
        $usuario->setNombre($_POST['nombre']);
        $usuario->setApellidos($_POST['apellidos']);
        $usuario->setEmail($_POST['email']);
        $usuario->setPassword($_POST['password']);
        $usuario->setFechaNacimiento($_POST['fecha_nacimiento']);
        
        // Registrar usuario
        try {
            $resultado = $usuario->registrarUsuario();
            // Redireccionar al muro después del registro
            header('Location: ../vistas/Muro.php');
            exit;
        } catch (Exception $e) {
            $error_registro = "Error al registrar: " . $e->getMessage();
        }
    }
    
    // Acción de Inicio de Sesión
    if ($_POST['accion'] === 'login') {
        session_start();
        $usuario = new Usuario();
        
        try {
            $resultado = $usuario->inicioSesion($_POST['nombre_usuario'], $_POST['password']);
            
            // Verificar si se obtuvo un usuario válido
            if ($resultado && isset($resultado->nombre_usuario)) {
                // Guardar usuario en la sesión
                $_SESSION['usuario'] = $resultado;

                // Redireccionar al muro después del login
                header('Location: ../vistas/Muro.php');
                exit;
            } else {
                // Redirigir de vuelta a la página de login con un error
                header('Location: ../vistas/InicioSesion.php?error=' . urlencode("Usuario o contraseña incorrectos"));
                exit;
            }
        } catch (Exception $e) {
            // Redirigir de vuelta a la página de login con el error de la excepción
            header('Location: ../vistas/InicioSesion.php?error=' . urlencode("Error al iniciar sesión: " . $e->getMessage()));
            exit;
        }
    }
    
    // Acción de Publicar
    if ($_POST['accion'] === 'publicar') {
        $publicacion = new Publicacion();
        
        // Establecer los valores de la publicación
        $publicacion->setNombreUsuario($_POST['nombre_usuario']);
        $publicacion->setContenido($_POST['contenido']);
        $publicacion->setFechaPublicacion(date('Y-m-d'));
        
        try {
            $resultado = $publicacion->subirPublicacion();
            // Recargar la página para ver la nueva publicación
            header('Location: ../vistas/Muro.php');
            exit;
        } catch (Exception $e) {
            $error_publicacion = "Error al publicar: " . $e->getMessage();
        }
    }
}

// Cargar las publicaciones para el muro
$publicacion = new Publicacion();
$publicaciones = $publicacion->getPublicaciones();

// Acción de Ver Perfil
if (isset($accion) && $accion === 'perfil') {
    require_once '../vistas/PerfilUsuario.php';
    exit;
}

// Si no hay acción, cargar las vistas por defecto
if (!isset($accion)) {
    require_once '../vistas/Muro.php';
}
