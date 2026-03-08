<?php
require_once '../modelos/Usuario.php';

if (session_status() === PHP_SESSION_NONE) {
    session_start();
}

if (!isset($_SESSION['usuario'])) {
    header('Location: InicioSesion.php?error=' . urlencode('Debes iniciar sesion'));
    exit;
}

$usuarioSesion = $_SESSION['usuario'];
$nombreUsuarioSesion = '';
if (isset($usuarioSesion->nombre_usuario)) {
    $nombreUsuarioSesion = $usuarioSesion->nombre_usuario;
} elseif (isset($usuarioSesion->nombreUsuario)) {
    $nombreUsuarioSesion = $usuarioSesion->nombreUsuario;
}

$usuarioObjetivo = isset($_GET['usuario']) ? trim($_GET['usuario']) : '';
if ($usuarioObjetivo === '') {
    $usuarioObjetivo = $nombreUsuarioSesion;
}

$esPerfilPropio = strcasecmp($usuarioObjetivo, $nombreUsuarioSesion) === 0;
$modeloUsuario = new Usuario();
$errorPerfil = '';
$mensajeExito = isset($_GET['ok']) ? trim($_GET['ok']) : '';

if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['accion'])) {
    if ($_POST['accion'] === 'cerrar_sesion') {
        $_SESSION = [];
        session_destroy();
        header('Location: InicioSesion.php');
        exit;
    }

    if ($_POST['accion'] === 'eliminar_cuenta') {
        if (!$esPerfilPropio) {
            $errorPerfil = 'Solo puedes eliminar tu propia cuenta.';
        } else {
            try {
                $modeloUsuario->borrarUsuario($nombreUsuarioSesion);
                $_SESSION = [];
                session_destroy();
                header('Location: InicioSesion.php?error=' . urlencode('Cuenta eliminada correctamente'));
                exit;
            } catch (Exception $e) {
                $errorPerfil = 'No se pudo eliminar la cuenta: ' . $e->getMessage();
            }
        }
    }
}

$perfil = null;
try {
    $perfil = $modeloUsuario->editarUsuario($usuarioObjetivo);
} catch (Exception $e) {
    $errorPerfil = 'No se pudo cargar el perfil solicitado.';
}

if (!$perfil && $esPerfilPropio) {
    $perfil = $usuarioSesion;
}

$nombreUsuarioPerfil = '';
if ($perfil) {
    if (isset($perfil->nombre_usuario)) {
        $nombreUsuarioPerfil = $perfil->nombre_usuario;
    } elseif (isset($perfil->nombreUsuario)) {
        $nombreUsuarioPerfil = $perfil->nombreUsuario;
    }
}
if ($nombreUsuarioPerfil === '') {
    $nombreUsuarioPerfil = $usuarioObjetivo;
}

$nombre = ($perfil && isset($perfil->nombre)) ? $perfil->nombre : '';
$apellidos = ($perfil && isset($perfil->apellidos)) ? $perfil->apellidos : '';
$email = ($perfil && isset($perfil->email)) ? $perfil->email : '';
$fechaNacimiento = '';
if ($perfil && isset($perfil->fecha_nacimiento)) {
    $fechaNacimiento = $perfil->fecha_nacimiento;
} elseif ($perfil && isset($perfil->fechaNacimiento)) {
    $fechaNacimiento = $perfil->fechaNacimiento;
}

$nombreCompleto = trim($nombre . ' ' . $apellidos);
if ($nombreCompleto === '') {
    $nombreCompleto = $nombreUsuarioPerfil;
}
?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Perfil de Usuario</title>
    <link rel="icon" href="../recursos/logoTransparent.png" type="image/png">
    <style>
        body { margin: 0; padding: 0; font-family: 'Segoe UI', Roboto, sans-serif; background: linear-gradient(135deg, #e3f2fd, #ffffff); color: #222; }
        .profile-container { max-width: 700px; margin: 5% auto; padding: 30px; background-color: #00000034; border-radius: 12px; box-shadow: 0 8px 20px rgba(0,0,0,.12); }
        .profile-header { text-align: center; margin-bottom: 30px; }
        .profile-header img { width: 180px; height: 180px; border-radius: 50%; border: 4px solid #007bff; margin-bottom: 15px; }
        .profile-header h2 { margin: 0; font-size: 28px; color: #014d9eff; }
        .profile-info { display: flex; flex-direction: column; gap: 15px; }
        .profile-info div { display: flex; justify-content: space-between; gap: 12px; padding: 10px 15px; background-color: #ffffffaa; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,.08); font-size: 16px; }
        .profile-info .label { font-weight: bold; color: #007bff; }
        .profile-actions { margin-top: 30px; display: flex; gap: 10px; }
        .profile-actions button { flex: 1; padding: 12px; background-color: #014d9eff; color: white; border: none; border-radius: 8px; font-size: 16px; font-weight: bold; cursor: pointer; }
        .profile-actions button:hover { background-color: #002540; }
        .msg-error { color: #a40000; margin-bottom: 12px; }
        .msg-ok { color: #0a6c2f; margin-bottom: 12px; }
        .link-volver { display: inline-block; margin-top: 18px; color: #014d9e; text-decoration: none; font-weight: bold; }
    </style>
</head>
<body>
<?php $headerLogoHref = 'Muro.php'; require_once 'Header.php'; ?>

<div class="profile-container">
    <?php if ($errorPerfil !== ''): ?>
        <div class="msg-error"><?php echo htmlspecialchars($errorPerfil); ?></div>
    <?php endif; ?>
    <?php if ($mensajeExito !== ''): ?>
        <div class="msg-ok"><?php echo htmlspecialchars($mensajeExito); ?></div>
    <?php endif; ?>

    <div class="profile-header">
        <img src="../recursos/user-avatar.png" alt="Foto de perfil">
        <h2><?php echo htmlspecialchars($nombreCompleto); ?></h2>
    </div>

    <div class="profile-info">
        <div><span class="label">Nombre de usuario:</span><span><?php echo htmlspecialchars($nombreUsuarioPerfil ?: 'No disponible'); ?></span></div>
        <div><span class="label">Correo electronico:</span><span><?php echo htmlspecialchars($email ?: 'No disponible'); ?></span></div>
        <div><span class="label">Nombre:</span><span><?php echo htmlspecialchars($nombre ?: 'No disponible'); ?></span></div>
        <div><span class="label">Apellidos:</span><span><?php echo htmlspecialchars($apellidos ?: 'No disponible'); ?></span></div>
        <div><span class="label">Fecha de nacimiento:</span><span><?php echo htmlspecialchars($fechaNacimiento ?: 'No disponible'); ?></span></div>
    </div>

    <?php if ($esPerfilPropio): ?>
        <form method="post" class="profile-actions">
            <button type="button" onclick="window.location.href='EditarUsuario.php'">Editar perfil</button>
            <button type="button" onclick="window.location.href='Muro.php'">Volver al muro</button>
            <button type="submit" name="accion" value="cerrar_sesion">Cerrar sesion</button>
            <button type="submit" name="accion" value="eliminar_cuenta" onclick="return confirm('¿Seguro que quieres eliminar tu cuenta?');">Eliminar cuenta</button>
        </form>
    <?php else: ?>
        <a class="link-volver" href="Muro.php">Volver al muro</a>
    <?php endif; ?>
</div>

<?php require_once 'Bottom.php'; ?>
</body>
</html>
