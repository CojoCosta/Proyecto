<?php
if (!isset($publicaciones)) {
    require_once __DIR__ . '/../modelos/Publicacion.php';
    $publicacionModel = new Publicacion();
    $publicaciones = $publicacionModel->getPublicaciones();
}
?>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Muro - Leash</title>
    <link rel="icon" href="../recursos/logoTransparent.png" type="image/png">
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Roboto, sans-serif;
            background: linear-gradient(135deg, #e3f2fd, #ffffff);
            color: #222;
        }
        
        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            padding-bottom: 80px;
        }
        
        /* Formulario de publicacion */
        .publicar-form {
            background-color: #fff;
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            margin-bottom: 30px;
        }
        
        .publicar-form textarea {
            width: 100%;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 15px;
            resize: vertical;
            min-height: 100px;
            font-family: inherit;
        }
        
        .publicar-form textarea:focus {
            outline: none;
            border-color: #007bff;
            box-shadow: 0 0 5px rgba(0, 123, 255, 0.3);
        }
        
        .publicar-form input[type="text"] {
            width: 100%;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 15px;
            margin-bottom: 10px;
        }
        
        .publicar-form input[type="text"]:focus {
            outline: none;
            border-color: #007bff;
            box-shadow: 0 0 5px rgba(0, 123, 255, 0.3);
        }
        
        .publicar-form input[type="submit"] {
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
            margin-top: 10px;
        }
        
        .publicar-form input[type="submit"]:hover {
            background-color: #002540;
        }
        
        /* Lista de publicaciones */
        .publicaciones {
            display: flex;
            flex-direction: column;
            gap: 20px;
        }
        
        .publicacion {
            background-color: #fff;
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }
        
        .publicacion-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
        }
        
        .publicacion-usuario {
            font-weight: bold;
            color: #014d9e;
            font-size: 16px;
        }
        
        .publicacion-fecha {
            color: #999;
            font-size: 14px;
        }
        
        .publicacion-contenido {
            font-size: 15px;
            line-height: 1.6;
            color: #333;
        }
        
        .publicacion-stats {
            margin-top: 15px;
            padding-top: 10px;
            border-top: 1px solid #eee;
            color: #666;
            font-size: 14px;
        }
        
        .error {
            color: red;
            text-align: center;
            margin-bottom: 10px;
            padding: 10px;
            background-color: #ffebee;
            border-radius: 8px;
        }
        
        .no-publicaciones {
            text-align: center;
            color: #666;
            padding: 40px;
        }
        
        /* Botón flotante para ir al perfil */
        .btn-perfil {
            position: fixed;
            bottom: 20px;
            right: 20px;
            width: 60px;
            height: 60px;
            background-color: #014d9eff;
            color: #fff;
            border: none;
            border-radius: 50%;
            font-size: 24px;
            cursor: pointer;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3);
            transition: background-color 0.3s ease, transform 0.2s ease;
            display: flex;
            align-items: center;
            justify-content: center;
            text-decoration: none;
            z-index: 1000;
        }
        
        .btn-perfil:hover {
            background-color: #002540;
            transform: scale(1.1);
        }
    </style>
</head>
<body>
    <?php
    require_once 'Header.php';
    
    // Mostrar errores si existen
    if (isset($error_publicacion)) {
        echo '<div class="container"><p class="error">' . htmlspecialchars($error_publicacion) . '</p></div>';
    }
    ?>
    
    <div class="container">
        <!-- Formulario para publicar -->
        <div class="publicar-form">
            <h3>Nueva Publicacion</h3>
            <form action="../controladores/ControladorUsuarios.php" method="post">
                <input type="hidden" name="accion" value="publicar">
                <textarea name="contenido" id="contenido" placeholder="Que estas pensando?" required></textarea><br>
                <input type="submit" value="Publicar">
            </form>
        </div>
        
        <div class="publicaciones">
            <h3>Publicaciones Recientes</h3>
            <?php
            if (isset($publicaciones) && is_array($publicaciones) && count($publicaciones) > 0) {
                foreach ($publicaciones as $pub) {
                    echo '<div class="publicacion">';
                    echo '  <div class="publicacion-header">';
                    $usuarioPub = '';
                    if (isset($pub->nombre_usuario)) {
                        $usuarioPub = $pub->nombre_usuario;
                    } elseif (isset($pub->nombreUsuario)) {
                        $usuarioPub = $pub->nombreUsuario;
                    }
                    echo '    <span class="publicacion-usuario"><a href="PerfilUsuario.php?usuario=' . urlencode($usuarioPub) . '">' . htmlspecialchars($usuarioPub) . '</a></span>';
                    echo '    <span class="publicacion-fecha">' . htmlspecialchars($pub->fecha_publicacion) . '</span>';
                    echo '  </div>';
                    echo '  <div class="publicacion-contenido">' . htmlspecialchars($pub->contenido) . '</div>';
                    echo '  <div class="publicacion-stats">';
                    echo '  </div>';
                    echo '</div>';
                }
            } else {
                echo '<div class="no-publicaciones">';
                echo '  <p>No hay publicaciones todavia.</p>';
                echo '  <p>Sé el primero en publicar algo!</p>';
                echo '</div>';
            }
            ?>
        </div>
    </div>
    
    <?php
    require_once 'Bottom.php';
    ?>
    
    <a href="PerfilUsuario.php" class="btn-perfil" title="Mi Perfil">
        &#128100;
    </a>
</body>
</html>
