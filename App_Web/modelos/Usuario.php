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
            'nombreUsuario'    => $this->nombre_usuario,
            'nombre'           => $this->nombre,
            'apellidos'        => $this->apellidos,
            'email'            => $this->email,
            'password'         => $this->password,
            'fechaNacimiento'  => $this->fecha_nacimiento
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
    public function actualizarUsuario($nombreUsuarioOriginal = null)
    {
        if (!$this->nombre_usuario) throw new Exception("Debe establecer nombre_usuario");
        if (!$nombreUsuarioOriginal) {
            $nombreUsuarioOriginal = $this->nombre_usuario;
        }

        $data = [
            'nombreUsuario'    => $this->nombre_usuario,
            'nombre'           => $this->nombre,
            'apellidos'        => $this->apellidos,
            'email'            => $this->email,
            'password'         => $this->password,
            'fechaNacimiento'  => $this->fecha_nacimiento
        ];

        $response = $this->request->request('PUT', $this->basePath . "/modificar/{$nombreUsuarioOriginal}", $data);
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
            $data = [
                'nombreUsuario' => $nombre_usuario,
                'password' => $password
            ];
            $response = $this->request->request('POST', $this->basePath . "/inicioSesion", $data);
            error_log("Response del login: " . json_encode($response));

            if (!$response) {
                return null;
            }
            return $response;
        } catch (Exception $e) {
            error_log("Excepcion en login: " . $e->getMessage());

            if (strpos($e->getMessage(), "max_connections_per_hour") !== false) {
                throw new Exception("Servicio temporalmente saturado. Espera unos minutos y vuelve a intentar.");
            }

            if (strpos($e->getMessage(), "Error HTTP: 404") !== false || strpos($e->getMessage(), "Error HTTP: 405") !== false) {
                try {
                    $response = $this->request->request(
                        'POST',
                        $this->basePath . "/inicioSesion/" . urlencode($nombre_usuario) . "/" . urlencode($password)
                    );
                    if (!$response) {
                        return null;
                    }
                    return $response;
                } catch (Exception $legacyException) {
                    if (strpos($legacyException->getMessage(), "max_connections_per_hour") !== false) {
                        throw new Exception("Servicio temporalmente saturado. Espera unos minutos y vuelve a intentar.");
                    }

                    if (strpos($legacyException->getMessage(), "Error HTTP: 401") !== false || strpos($legacyException->getMessage(), "Error HTTP: 500") !== false) {
                        return null;
                    }
                    throw $legacyException;
                }
            }

            if (strpos($e->getMessage(), "Error HTTP: 401") !== false) {
                return null;
            }

            return null;
        }
    }

    private function autenticarDesdeListado($nombre_usuario, $password)
    {
        try {
            $usuarios = $this->request->request('GET', $this->basePath);
            if (!$usuarios) {
                return null;
            }

            foreach ($usuarios as $usuario) {
                $username = null;
                if (isset($usuario->nombreUsuario)) {
                    $username = $usuario->nombreUsuario;
                } elseif (isset($usuario->nombre_usuario)) {
                    $username = $usuario->nombre_usuario;
                }

                if ($username !== null
                    && strcasecmp($username, $nombre_usuario) === 0
                    && isset($usuario->password)
                    && $usuario->password === $password) {
                    return $usuario;
                }
            }

            return null;
        } catch (Exception $e) {
            return null;
        }
    }
}
