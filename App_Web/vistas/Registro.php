<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro</title>
    <link rel="icon" href="../recursos/logoTransparent.png" type="image/png">
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Roboto, sans-serif;
            background: linear-gradient(135deg, #e3f2fd, #ffffff);
            color: #222;
        }

        form {
            width: 100%;
            max-width: 600px;
            margin: 10px auto;
            padding: 30px;
            background-color: #00000034;
            border-radius: 12px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.12);
        }

        form input[type="text"],
        form input[type="email"],
        form input[type="password"],
        form input[type="date"] {
            width: 100%;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 15px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        form input::placeholder {
            color: #999;
        }

        form input:focus {
            outline: none;
            border-color: #007bff;
            box-shadow: 0 0 5px rgba(0, 123, 255, 0.3);
        }

        /* Botón */
        form input[type="submit"] {
            width: 100%;
            padding: 14px;
            background-color: #014d9eff;
            color: #fff;
            font-size: 16px;
            font-weight: bold;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        form input[type="submit"]:hover {
            background-color: #002540;
        }

        .error {
            color: red;
            text-align: center;
            margin-bottom: 10px;
            padding: 10px;
            background-color: #ffebee;
            border-radius: 8px;
        }

        .success {
            color: green;
            text-align: center;
            margin-bottom: 10px;
            padding: 10px;
            background-color: #e8f5e9;
            border-radius: 8px;
        }
    </style>
</head>

<body>
    <?php
    require_once 'Header.php';
    
    // Mostrar error si existe
    if (isset($error_registro)) {
        echo '<p class="error">' . htmlspecialchars($error_registro) . '</p>';
    }
    ?>
    <form action="../controladores/ControladorUsuarios.php" method="post">
        <input type="hidden" name="accion" value="registrar">
        <input type="text" name="nombre_usuario" id="nombre_usuario" placeholder="Nombre de usuario" required><br><br>
        <input type="text" name="nombre" id="nombre" placeholder="Nombre" required><br><br>
        <input type="text" name="apellidos" id="apellidos" placeholder="Apellidos" required><br><br>
        <input type="email" name="email" id="email" placeholder="Email" required><br><br>
        <input type="password" name="password" id="password" placeholder="Contrasena" required><br><br>
        <input type="date" name="fecha_nacimiento" id="fecha_nacimiento" required><br><br>
        <input type="submit" value="Registrarse">
    </form>
    <?php
    require_once 'Bottom.php';
    ?>

</body>

</html>
