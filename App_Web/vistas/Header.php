<style>
    /* Reset básico */
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }

    /* Header principal */
    #header {
        width: 100%;
        height: 70px;
        background-color: #0a1f44; /* Azul oscuro */
        display: flex;
        align-items: center;
        padding: 0 20px;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);
    }

    /* Contenedor del logo */
    #logo {
        display: flex;
        align-items: center;
    }

    /* Imagen del logo */
    #logo img {
        height: 45px;
        width: auto;
        cursor: pointer;
    }

    /* Efecto hover sutil */
    #logo img:hover {
        opacity: 0.9;
    }
</style>
<div id = "header">
    <div id = "logo">
        <img src="App_Web\recursos\logoTransparent.png">
    </div>
</div>