<!DOCTYPE html>
<html lang="en">

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
            /* margin-bottom: 16px; */
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
    </style>
    <?php
    if ((isset($_POST['nombre'])) && ($_POST['nombre'] != '') && (isset($_POST['apellidos'])) && ($_POST['apellidos'] != '') && (isset($_POST['nombre_usuario'])) && ($_POST['nombre_usuario'] != '') && (isset($_POST['email'])) && ($_POST['email'] != '') && (isset($_POST['password'])) && ($_POST['password'] != '') && (isset($_POST['fecha_nacimiento'])) && ($_POST['fecha_nacimiento'] != '')) {
        //llamada al moddelo logico
        require_once '../App_Web/modelos/Usuario.php';
        //crear objeto
        $usuario = new Usuario();
        //Booleana
        // $resultadoConsulta = $usuario->setUsuario($_POST['nombre_usuario'], $_POST['nombre'], $_POST['apellidos'], $_POST['email'], $_POST['password'], $_POST['fecha_nacimiento']);
    }

    ?>


</head>

<body>
    <?php
    require_once 'Header.php'
    ?>
    <form action="" <?php echo $_SERVER['PHP_SELF'] ?>"" method="post">
        <input type="text" name="nombre_usuario" id="nombre_usuario" placeholder="NombreUsuario"><br><br>
        <input type="text" name="nombre" id="nombre" placeholder="Nombre"><br><br>
        <input type="text" name="apellidos" id="apellidos" placeholder="Apellidos"><br><br>
        <input type="email" name="email" id="email" placeholder="Email"><br><br>
        <input type="password" name="password" id="password" placeholder="Contraseña"><br><br>
        <input type="date" name="fecha_nacimiento" id="fecha_nacimiento"><br><br>
        <input type="submit" value="Registrarse">
    </form>
    <?php
    require_once 'Bottom.php'
    ?>

</body>

</html>