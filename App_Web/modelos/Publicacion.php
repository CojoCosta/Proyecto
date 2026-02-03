<?php
Class Publicacion{
    private $publicacion;
    private $idUsuario;
    private $fechaPublicacion;
    private $numLikes;
    private $fotoPublicacion;
    private $database;

    public function __construct(){
        $this->publicacion = array();
        $this->idUsuario = 0;
        $this->fechaPublicacion = "";
        $this->numLikes = 0;
        $this->database =  Conexion::conexion();
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