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

if ($nombreUsuarioSesion === '') {
    header('Location: PerfilUsuario.php?error=' . urlencode('No se pudo identificar el usuario'));
    exit;
}

$modeloUsuario = new Usuario();
$errorPerfil = '';

$perfil = null;
try {
    $perfil = $modeloUsuario->editarUsuario($nombreUsuarioSesion);
} catch (Exception $e) {
    $errorPerfil = 'No se pudo cargar tu perfil.';
}

if (!$perfil) {
    $perfil = $usuarioSesion;
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
$passwordActual = ($perfil && isset($perfil->password)) ? $perfil->password : '';

if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['accion']) && $_POST['accion'] === 'actualizar_perfil') {
    try {
        $nuevoNombreUsuario = trim($_POST['nombre_usuario'] ?? '');
        if ($nuevoNombreUsuario === '') {
            throw new Exception('El nombre de usuario es obligatorio');
        }

        $modeloUsuario->setNombreUsuario($nuevoNombreUsuario);
        $modeloUsuario->setNombre(trim($_POST['nombre'] ?? ''));
        $modeloUsuario->setApellidos(trim($_POST['apellidos'] ?? ''));
        $modeloUsuario->setEmail(trim($_POST['email'] ?? ''));

        $passwordNueva = trim($_POST['password'] ?? '');
        $modeloUsuario->setPassword($passwordNueva !== '' ? $passwordNueva : $passwordActual);
        $modeloUsuario->setFechaNacimiento($fechaNacimiento);

        $modeloUsuario->actualizarUsuario($nombreUsuarioSesion);

        $usuarioActualizado = $modeloUsuario->editarUsuario($nuevoNombreUsuario);
        if ($usuarioActualizado) {
            $_SESSION['usuario'] = $usuarioActualizado;
            $nombreUsuarioSesion = $nuevoNombreUsuario;
        }

        header('Location: PerfilUsuario.php?usuario=' . urlencode($nuevoNombreUsuario) . '&ok=' . urlencode('Perfil actualizado correctamente'));
        exit;
    } catch (Exception $e) {
        $errorPerfil = 'Error al actualizar perfil: ' . $e->getMessage();
        $nombreUsuarioSesion = trim($_POST['nombre_usuario'] ?? $nombreUsuarioSesion);
        $nombre = trim($_POST['nombre'] ?? $nombre);
        $apellidos = trim($_POST['apellidos'] ?? $apellidos);
        $email = trim($_POST['email'] ?? $email);
    }
}
?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Usuario</title>
    <link rel="icon" href="../recursos/logoTransparent.png" type="image/png">
    <style>
        body { margin: 0; padding: 0; font-family: 'Segoe UI', Roboto, sans-serif; background: linear-gradient(135deg, #e3f2fd, #ffffff); color: #222; }
        .edit-container { max-width: 700px; margin: 5% auto; padding: 30px; background-color: #00000034; border-radius: 12px; box-shadow: 0 8px 20px rgba(0,0,0,.12); }
        h2 { margin-top: 0; color: #014d9eff; }
        .edit-form { display: grid; gap: 12px; }
        .edit-form label { font-weight: bold; color: #014d9eff; }
        .edit-form input { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 8px; font-size: 15px; }
        .actions { margin-top: 10px; display: flex; gap: 10px; }
        .actions button { flex: 1; padding: 12px; background-color: #014d9eff; color: #fff; font-size: 16px; font-weight: bold; border: none; border-radius: 8px; cursor: pointer; }
        .actions button:hover { background-color: #002540; }
        .msg-error { color: #a40000; margin-bottom: 12px; }
    </style>
</head>
<body>
<?php $headerLogoHref = 'Muro.php'; require_once 'Header.php'; ?>

<div class="edit-container">
    <h2>Editar perfil</h2>

    <?php if ($errorPerfil !== ''): ?>
        <div class="msg-error"><?php echo htmlspecialchars($errorPerfil); ?></div>
    <?php endif; ?>

    <form class="edit-form" method="post" action="">
        <input type="hidden" name="accion" value="actualizar_perfil">

        <label for="nombre_usuario">Nombre de usuario</label>
        <input type="text" id="nombre_usuario" name="nombre_usuario" value="<?php echo htmlspecialchars($nombreUsuarioSesion); ?>" required>

        <label for="nombre">Nombre</label>
        <input type="text" id="nombre" name="nombre" value="<?php echo htmlspecialchars($nombre); ?>" required>

        <label for="apellidos">Apellidos</label>
        <input type="text" id="apellidos" name="apellidos" value="<?php echo htmlspecialchars($apellidos); ?>" required>

        <label for="email">Email</label>
        <input type="email" id="email" name="email" value="<?php echo htmlspecialchars($email); ?>" required>

        <label for="fecha_nacimiento">Fecha de nacimiento</label>
        <input type="text" id="fecha_nacimiento" value="<?php echo htmlspecialchars($fechaNacimiento ?: 'No disponible'); ?>" disabled>

        <label for="password">Nueva contrasena (opcional)</label>
        <input type="password" id="password" name="password" value="" placeholder="Deja vacio para mantener la actual">

        <div class="actions">
            <button type="submit">Guardar cambios</button>
            <button type="button" onclick="window.location.href='PerfilUsuario.php?usuario=<?php echo urlencode($nombreUsuarioSesion); ?>'">Cancelar</button>
        </div>
    </form>
</div>

<?php require_once 'Bottom.php'; ?>
</body>
</html>
