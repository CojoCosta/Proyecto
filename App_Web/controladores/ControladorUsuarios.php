<?php
require_once '../modelos/Usuario.php';
require_once '../modelos/Publicacion.php';

if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['accion'])) {

    if ($_POST['accion'] === 'registrar') {
        $usuario = new Usuario();
        $usuario->setNombreUsuario($_POST['nombre_usuario']);
        $usuario->setNombre($_POST['nombre']);
        $usuario->setApellidos($_POST['apellidos']);
        $usuario->setEmail($_POST['email']);
        $usuario->setPassword($_POST['password']);
        $usuario->setFechaNacimiento($_POST['fecha_nacimiento']);

        try {
            $usuario->registrarUsuario();
            header('Location: ../vistas/Muro.php');
            exit;
        } catch (Exception $e) {
            $error_registro = 'Error al registrar: ' . $e->getMessage();
        }
    }

    if ($_POST['accion'] === 'login') {
        if (session_status() === PHP_SESSION_NONE) {
            session_start();
        }

        $usuario = new Usuario();

        try {
            $resultado = $usuario->inicioSesion($_POST['nombre_usuario'], $_POST['password']);
            $usuarioValido = false;

            if ($resultado) {
                $usuarioValido = isset($resultado->nombre_usuario) || isset($resultado->nombreUsuario);
            }

            if ($usuarioValido) {
                if (!isset($resultado->nombre_usuario) && isset($resultado->nombreUsuario)) {
                    $resultado->nombre_usuario = $resultado->nombreUsuario;
                }
                if (!isset($resultado->id_usuario) && isset($resultado->id)) {
                    $resultado->id_usuario = (int)$resultado->id;
                }

                $_SESSION['usuario'] = $resultado;
                header('Location: ../vistas/Muro.php');
                exit;
            }

            header('Location: ../vistas/InicioSesion.php?error=' . urlencode('Usuario o contrasena incorrectos'));
            exit;
        } catch (Exception $e) {
            header('Location: ../vistas/InicioSesion.php?error=' . urlencode('Error al iniciar sesion: ' . $e->getMessage()));
            exit;
        }
    }

    if ($_POST['accion'] === 'publicar') {
        if (session_status() === PHP_SESSION_NONE) {
            session_start();
        }

        if (!isset($_SESSION['usuario'])) {
            header('Location: ../vistas/InicioSesion.php?error=' . urlencode('Debes iniciar sesion para publicar'));
            exit;
        }

        $publicacion = new Publicacion();
        $usuarioSesion = $_SESSION['usuario'];

        $nombreUsuario = '';
        if (isset($usuarioSesion->nombre_usuario)) {
            $nombreUsuario = $usuarioSesion->nombre_usuario;
        } elseif (isset($usuarioSesion->nombreUsuario)) {
            $nombreUsuario = $usuarioSesion->nombreUsuario;
        }

        $idUsuario = 0;
        if (isset($usuarioSesion->id_usuario)) {
            $idUsuario = (int)$usuarioSesion->id_usuario;
        } elseif (isset($usuarioSesion->idUsuario)) {
            $idUsuario = (int)$usuarioSesion->idUsuario;
        } elseif (isset($usuarioSesion->id)) {
            $idUsuario = (int)$usuarioSesion->id;
        }

        $publicacion->setIdUsuario($idUsuario);
        $publicacion->setNombreUsuario($nombreUsuario);
        $publicacion->setContenido($_POST['contenido'] ?? '');
        $publicacion->setFechaPublicacion(date('Y-m-d'));

        try {
            $publicacion->subirPublicacion();
            header('Location: ../vistas/Muro.php');
            exit;
        } catch (Exception $e) {
            $error_publicacion = 'Error al publicar: ' . $e->getMessage();
        }
    }
}

$publicacion = new Publicacion();
$publicaciones = $publicacion->getPublicaciones();

if (isset($accion) && $accion === 'perfil') {
    require_once '../vistas/PerfilUsuario.php';
    exit;
}

if (!isset($accion)) {
    require_once '../vistas/Muro.php';
}
