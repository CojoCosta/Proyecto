<?php
require_once '../conexion/Request.php';

class Publicacion {
    private $request;
    private $basePath = "/publicaciones";
    private $id_usuario;
    private $nombre_usuario;
    private $fecha_publicacion;
    private $contenido;

    public function __construct() {
        $this->request = new Request("http://localhost:8080/apirest/rest");
        $this->id_usuario = 0;
        $this->nombre_usuario = "";
        $this->fecha_publicacion = "";
        $this->contenido = "";
    }
    
    #region SETTERS Y GETTERS
    public function setIdUsuario($id_usuario) { $this->id_usuario = $id_usuario; }
    public function getIdUsuario() { return $this->id_usuario; }

    public function setNombreUsuario($nombre_usuario) { $this->nombre_usuario = $nombre_usuario; }
    public function getNombreUsuario() { return $this->nombre_usuario; }

    public function setContenido($contenido) { $this->contenido = $contenido; }
    public function getContenido() { return $this->contenido; }

    public function setFechaPublicacion($fecha_publicacion) { $this->fecha_publicacion = $fecha_publicacion; }
    public function getFechaPublicacion() { return $this->fecha_publicacion; }
    #endregion

    // Obtener todas las publicaciones
    public function getPublicaciones() {
        $response = $this->request->request('GET', $this->basePath);
        return $response;
    }

    // Subir publicación usando las propiedades internas
    public function subirPublicacion() {
        $data = [
            'id_usuario' => $this->id_usuario,
            'nombre_usuario' => $this->nombre_usuario,
            'contenido' => $this->contenido,
        ];

        return $this->request->request('POST', $this->basePath, $data);
    }

    // Subir publicación con parámetros directo (método alternativo)
    public function subirPublicacionConParametros($nombre_usuario, $contenido, $fecha_publicacion) {
        $this->nombre_usuario = $nombre_usuario;
        $this->contenido = $contenido;
        $this->fecha_publicacion = $fecha_publicacion;
        
        return $this->subirPublicacion();
    }

    // Obtener una publicación específica
    public function obtenerPublicacion($id_publicacion) {
        return $this->request->request('GET', $this->basePath . "/{$id_publicacion}");
    }

    // Actualizar publicación
    public function actualizarPublicacion($id_publicacion, $nombre_usuario, $contenido) {
        $data = [
            'id_publicacion' => $id_publicacion,
            'nombre_usuario' => $nombre_usuario,
            'contenido' => $contenido,
        ];

        return $this->request->request('PUT', $this->basePath . "/{$id_publicacion}", $data);
    }

    // Borrar publicación
    public function borrarPublicacion($id) {
        return $this->request->request('DELETE', $this->basePath . "/{$id}");
    }
}
