<?php
require_once 'Request.php';

class Usuario
{
    private $path;
    private $request;
    private $nombre_usuario;
    private $nombre;
    private $apellidos;
    private $email;
    private $password;
    private $fecha_nacimiento;
    private $database;

    public function __construct()
    {
        $this->path = "http://10.0.2.2:8080/apirest/rest/usuarios";
        $this->request = new Request($this->path);
        $this->nombre_usuario = "";
        $this->nombre = "";
        $this->apellidos = "";
        $this->email = "";
        $this->password = "";
        $this->fecha_nacimiento = "";
        $this->database =  Conexion::conexion();
    }

    #region SETTERS Y GETTERS
    public function setNombreUsuario($nombre_usuario)
    {
        $this->nombre_usuario = $nombre_usuario;
    }
    public function getNombreUsuario()
    {
        return $this->nombre_usuario;
    }

    public function setNombre($nombre)
    {
        $this->nombre = $nombre;
    }
    public function getNombre()
    {
        return $this->nombre;
    }

    public function setApellidos($apellidos)
    {
        $this->apellidos = $apellidos;
    }
    public function getApellidos()
    {
        return $this->apellidos;
    }

    public function setEmail($email)
    {
        $this->email = $email;
    }
    public function getEmail()
    {
        return $this->email;
    }

    public function setPassword($password)
    {
        $this->password = $password;
    }
    public function getPassword()
    {
        return $this->password;
    }

    public function setFechaNacimiento($fecha_nacimiento)
    {
        $this->fecha_nacimiento = $fecha_nacimiento;
    }
    public function getFechaNacimiento()
    {
        return $this->fecha_nacimiento;
    }
    #endregion

    public function getUsuario()
    {
        return $this->request->request('GET', '/usuarios');
    }

    public function registrarUsuario($nombre_usuario, $nombre, $apellidos, $email, $password, $fecha_nacimiento)
    {
        $data = [
            'nombre_usuario' => $nombre_usuario,
            'nombre' => $nombre,
            'apellidos' => $apellidos,
            'email' => $email,
            'password' => $password,
            'fecha_nacimiento' => $fecha_nacimiento
        ];

        return $this->request->request('POST', '/usuarios', $data);
    }

    public function editarUsuario($id)
    {
        return $this->request->request('GET', "/usuarios/{$id}");
    }

    public function actualizarUsuario($id, $nombre_usuario, $nombre, $apellidos, $email, $password, $fecha_nacimiento)
    {
        $data = [
            'nombre_usuario' => $nombre_usuario,
            'nombre' => $nombre,
            'apellidos' => $apellidos,
            'email' => $email,
            'password' => $password,
            'fecha_nacimiento' => $fecha_nacimiento
        ];

        return $this->request->request('PUT', "/usuarios/{$id}", $data);
    }

    public function borrarUsuario($id)
    {
        return $this->request->request('DELETE', "/usuarios/{$id}");
    }
}
