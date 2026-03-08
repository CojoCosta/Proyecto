<style>
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }
    #header {
        width: 100%;
        height: 70px;
        background-color: #002540;
        display: flex;
        align-items: center;
        padding: 0 20px;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);
    }

    #bottom {
        width: 100%;
        height: 60px;
        background-color: #002540;
        position: fixed;
        bottom: 0;
        left: 0;
        display: flex;
        align-items: center;
        justify-content: center;
        color: white;
        box-shadow: 0 -2px 6px rgba(0, 0, 0, 0.3);
    }

    #bottom a {
        color: #ffffff;
        text-decoration: none;
        font-weight: 600;
    }

    #bottom a:hover {
        text-decoration: underline;
    }

</style>

<?php
$vistaActual = basename($_SERVER['PHP_SELF']);
$textoEnlace = '';
$urlEnlace = '';

if ($vistaActual === 'InicioSesion.php') {
    $textoEnlace = '¿Todavia no tienes una cuenta? Registrate';
    $urlEnlace = 'Registro.php';
} elseif ($vistaActual === 'Registro.php') {
    $textoEnlace = '¿Ya tienes cuenta? Inicia sesion aqui';
    $urlEnlace = 'InicioSesion.php';
}
?>

<div id = "bottom">
    <?php if ($textoEnlace !== ''): ?>
        <a href="<?php echo htmlspecialchars($urlEnlace); ?>"><?php echo htmlspecialchars($textoEnlace); ?></a>
    <?php endif; ?>
</div>
