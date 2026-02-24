<?php

require_once '../conexion/Request.php';

class Usuario
{
    private $request;
    private $basePath = "/usuarios"; // Endpoint base de la API

    // Propiedades internas del usuario
    private $nombre_usuario;
    private $nombre;
    private $apellidos;
    private $email;
    private $password;
    private $fecha_nacimiento;

    // Constructor: instancia la clase Request
    public function __construct()
    {
        $this->request = new Request("http://localhost:8080/apirest/rest");
    }

    #region SETTERS Y GETTERS
    public function setNombreUsuario($nombre_usuario) { $this->nombre_usuario = $nombre_usuario; }
    public function getNombreUsuario() { return $this->nombre_usuario; }

    public function setNombre($nombre) { $this->nombre = $nombre; }
    public function getNombre() { return $this->nombre; }

    public function setApellidos($apellidos) { $this->apellidos = $apellidos; }
    public function getApellidos() { return $this->apellidos; }

    public function setEmail($email) { $this->email = $email; }
    public function getEmail() { return $this->email; }

    public function setPassword($password) { $this->password = $password; }
    public function getPassword() { return $this->password; }

    public function setFechaNacimiento($fecha_nacimiento) { $this->fecha_nacimiento = $fecha_nacimiento; }
    public function getFechaNacimiento() { return $this->fecha_nacimiento; }
    #endregion

    // Devuelve todos los usuarios
    public function getUsuarios()
    {
        $response = $this->request->request('GET', $this->basePath);
        if (!$response) throw new Exception("Error al obtener usuarios");
        return $response;
    }

    // Registra un nuevo usuario usando las propiedades internas
    public function registrarUsuario()
    {
        $data = [
            'nombre_usuario'   => $this->nombre_usuario,
            'nombre'           => $this->nombre,
            'apellidos'        => $this->apellidos,
            'email'            => $this->email,
            'password'         => $this->password,
            'fecha_nacimiento' => $this->fecha_nacimiento
        ];

        $response = $this->request->request('POST', $this->basePath, $data);
        if (!$response) throw new Exception("Error al registrar usuario");
        return $response;
    }

    // Obtiene los datos de un usuario específico
    public function editarUsuario($nombre_usuario)
    {
        $response = $this->request->request('GET', $this->basePath . "/perfil/{$nombre_usuario}");
        if (!$response) throw new Exception("Error al obtener usuario");
        return $response;
    }

    // Actualiza los datos de un usuario específico
    public function actualizarUsuario()
    {
        if (!$this->nombre_usuario) throw new Exception("Debe establecer nombre_usuario");

        $data = [
            'nombre_usuario'   => $this->nombre_usuario,
            'nombre'           => $this->nombre,
            'apellidos'        => $this->apellidos,
            'email'            => $this->email,
            'password'         => $this->password,
            'fecha_nacimiento' => $this->fecha_nacimiento
        ];

        $response = $this->request->request('PUT', $this->basePath . "/modificar/{$this->nombre_usuario}", $data);
        if (!$response) throw new Exception("Error al actualizar usuario");
        return $response;
    }

    // Borra un usuario por nombre_usuario
    public function borrarUsuario($nombre_usuario)
    {
        $response = $this->request->request('DELETE', $this->basePath . "/eliminar/{$nombre_usuario}");
        if (!$response) throw new Exception("Error al borrar usuario");
        return $response;
    }

    // Inicio de sesión
    public function inicioSesion($nombre_usuario, $password)
    {
        try {
            error_log("Intentando login con usuario: " . $nombre_usuario);
            $response = $this->request->request('GET', $this->basePath . "/inicioSesion/" . urlencode($nombre_usuario) . "/" . urlencode($password));
            error_log("Response del login: " . json_encode($response));
            
            if (!$response) {
                error_log("Response vacío, retornando null");
                return null;
            }
            return $response;
        } catch (Exception $e) {
            error_log("Excepción en login: " . $e->getMessage());
            // Si la API devuelve un 500, podría ser un inicio de sesión fallido
            if (strpos($e->getMessage(), "Error HTTP: 500") !== false) {
                return null; // Tratar como inicio de sesión fallido
            }
            // Para otros errores, lanzar la excepción
            throw $e;
        }
    }
}