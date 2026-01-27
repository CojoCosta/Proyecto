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

    public function setIdUsuario($IdUsuario){
        $this->idUsuario = $IdUsuario;
    }
    public function getIdUsuario(){
        return $this->idUsuario;
    }

    public function setFechaPublicacion($fechaPublicacion){
        $this->fechaPublicacion = $fechaPublicacion;
    }
    public function getFechaPublicacion(){
        return $this->fechaPublicacion;
    }

    public function setNumLikes($numLikes){
        $this->numLikes = $numLikes;
    }
    public function getNumLikes(){
        return $this->numLikes;
    }
}