<style>
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }

    #header {
        width: 100%;
        height: 15%;
        background-color: #002540;/* Azul oscuro */
        display: flex;
        align-items: center;
        justify-content: center;
        padding: 0 20px;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);
    }
    #logo {
        display: flex;
        align-items: center;
        justify-content: center;
    }

    /* Imagen del logo */
    #logo img {
        height: 120px;
        width: auto;
        cursor: pointer;
    }

    /* Efecto hover sutil */
    #logo img:hover {
        opacity: 1;
    }

</style>
<div id = "header">
    <div id = "logo">
        <img src="../recursos/logoTransparent.png">
    </div>
</div>
