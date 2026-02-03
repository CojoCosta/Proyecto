<?php
require_once 'Request.php';
Class Publicacion{
    private $path;
    private $request;

    
    private $id_usuario;
    private $fecha_publicacion;
    private $num_likes;
    private $database;
    

    public function __construct(){
        $this->path =$this->path = "http://10.0.2.2:8080/apirest/rest/publicacion";
        $this->request = new Request($this->path);
        $this->id_usuario = 0;
        $this->fecha_publicacion = "";
        $this->num_likes = 0;
        $this->database =  Conexion::conexion();
    }
    #region SETTERS Y GETTERS
    public function setIdUsuario($Id_usuario){$this->id_usuario = $Id_usuario;}
    public function getIdUsuario(){return $this->id_usuario;}

    public function setFechaPublicacion($fecha_publicacion){$this->fecha_publicacion = $fecha_publicacion;}
    public function getFechaPublicacion(){return $this->fecha_publicacion;}

    public function setNumLikes($num_likes){$this->num_likes = $num_likes;}
    public function getNumLikes(){return $this->num_likes;}
    #endregion

    public function getPublicacion()
    {
        return $this->request->request('GET', '/publicacion');
    }

    public function subirPublicacion($id_usuario, $fecha_publicacion, $num_likes)
    {
        $data = [
            'id_usuario' => $id_usuario,
            'fecha_publicacion' => $fecha_publicacion,
            'num_likes' => $num_likes
        ];

        return $this->request->request('POST', '/publicacion', $data);
    }

    public function editarPublicación($id_publicacion)
    {
        return $this->request->request('GET', "/publicacion/{$id_publicacion}");
    }

    public function actualizarPublicacion($id_publicacion, $id_usuario, $fecha_publicacion, $num_likes)
    {
        $data = [
            'id_publicacion' => $id_publicacion,
            'id_usuario' => $id_usuario,
            'fecha_publicacion' => $fecha_publicacion,
            'num_likes' => $num_likes
        ];

        return $this->request->request('PUT', "/publicacion/{$id_publicacion}", $data);
    }

    public function borrarPublicacion($id)
    {
        return $this->request->request('DELETE', "/publicacion/{$id}");
    }
}