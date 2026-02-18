<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Perfil de Usuario</title>
    <link rel="icon" href="../recursos/logoTransparent.png" type="image/png">
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Roboto, sans-serif;
            background: linear-gradient(135deg, #e3f2fd, #ffffff);
            color: #222;
        }

        .profile-container {
            max-width: 700px;
            margin: 5% auto;
            padding: 30px;
            background-color: #00000034;
            border-radius: 12px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.12);
        }

        .profile-header {
            text-align: center;
            margin-bottom: 30px;
        }

        .profile-header img {
            width: 180px;
            height: 180px;
            border-radius: 50%;
            border: 4px solid #007bff;
            margin-bottom: 15px;
        }


        .profile-header h2 {
            margin: 0;
            font-size: 28px;
            color: #014d9eff;
        }

        .profile-info {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        .profile-info div {
            display: flex;
            justify-content: space-between;
            padding: 10px 15px;
            background-color: #ffffffaa;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
            font-size: 16px;
        }

        .profile-info div span.label {
            font-weight: bold;
            color: #007bff;
        }

        .profile-actions {
            margin-top: 30px;
            display: flex;
            justify-content: space-between;
            gap: 10px;
        }

        .profile-actions button {
            flex: 1;
            padding: 12px;
            background-color: #014d9eff;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .profile-actions button:hover {
            background-color: #002540;
        }
    </style>
</head>

<body>
    <?php
    require_once 'Header.php';
    ?>

    <div class="profile-container">
        <div class="profile-header">
            <img src="../recursos/user-avatar.png" alt="Foto de perfil">
            <h2>Nombre y apellidos</h2>
        </div>

        <div class="profile-info">
            <div><span class="label">Nombre de usuario:</span> usuario</div>
            <div><span class="label">Correo electrónico:</span> email@example.com</div>
            <div><span class="label">Teléfono:</span> +34 555555555 </div>
            <div><span class="label">Fecha de registro:</span> dd/mm/yyyy</div>
        </div>

        <div class="profile-actions">
            <button>Editar Perfil</button>
            <button>Cambiar Contraseña</button>
        </div>
    </div>

    <?php
    require_once 'Bottom.php';
    ?>
</body>

</html>