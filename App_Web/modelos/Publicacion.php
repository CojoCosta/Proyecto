<?php
Class Publicacion{
    private $idUsuario;
    private $fechaPublicacion;
    private $numLikes;

    public function __construct($idUsuario, $fechaPublicacion, $numLikes){
        $this->idUsuario = $idUsuario;
        $this->fechaPublicacion = $fechaPublicacion;
        $this->numLikes = $numLikes;
    }

    
}