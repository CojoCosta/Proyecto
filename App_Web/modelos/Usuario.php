<?php
// require_once '../conexion/Request.php';

// class Usuario
// {
//     private $path;
//     private $request;
//     private $nombre_usuario;
//     private $nombre;
//     private $apellidos;
//     private $email;
//     private $password;
//     private $fecha_nacimiento;

//     public function __construct()
//     {
//         $this->path = "http://10.0.2.2:8080/apirest/rest/usuarios";
//         $this->request = new Request($this->path);
//     }

//     #region SETTERS Y GETTERS
//     public function setNombreUsuario($nombre_usuario)
//     {
//         $this->nombre_usuario = $nombre_usuario;
//     }
//     public function getNombreUsuario()
//     {
//         return $this->nombre_usuario;
//     }

//     public function setNombre($nombre)
//     {
//         $this->nombre = $nombre;
//     }
//     public function getNombre()
//     {
//         return $this->nombre;
//     }

//     public function setApellidos($apellidos)
//     {
//         $this->apellidos = $apellidos;
//     }
//     public function getApellidos()
//     {
//         return $this->apellidos;
//     }

//     public function setEmail($email)
//     {
//         $this->email = $email;
//     }
//     public function getEmail()
//     {
//         return $this->email;
//     }

//     public function setPassword($password)
//     {
//         $this->password = $password;
//     }
//     public function getPassword()
//     {
//         return $this->password;
//     }

//     public function setFechaNacimiento($fecha_nacimiento)
//     {
//         $this->fecha_nacimiento = $fecha_nacimiento;
//     }
//     public function getFechaNacimiento()
//     {
//         return $this->fecha_nacimiento;
//     }
//     #endregion

//     public function getUsuarios()
//     {
//         return $this->request->request('GET', "");
//     }

//     public function registrarUsuario($nombre_usuario, $nombre, $apellidos, $email, $password, $fecha_nacimiento)
//     {
//         $data = [
//             'nombre_usuario' => $nombre_usuario,
//             'nombre' => $nombre,
//             'apellidos' => $apellidos,
//             'email' => $email,
//             'password' => $password,
//             'fecha_nacimiento' => $fecha_nacimiento
//         ];

//         return $this->request->request('POST', "", $data);
//     }

//     public function editarUsuario($nombre_usuario)
//     {
//         return $this->request->request('GET', "/perfil/{$nombre_usuario}");
//     }

//     public function actualizarUsuario($nombre_usuario, $nombre, $apellidos, $email, $password, $fecha_nacimiento)
//     {
//         $data = [
//             'nombre_usuario' => $nombre_usuario,
//             'nombre' => $nombre,
//             'apellidos' => $apellidos,
//             'email' => $email,
//             'password' => $password,
//             'fecha_nacimiento' => $fecha_nacimiento
//         ];

//         return $this->request->request('PUT', "/modificar/{$nombre_usuario}", $data);
//     }

//     public function borrarUsuario($nombre_usuario)
//     {
//         return $this->request->request('DELETE', "/eliminar/{$nombre_usuario}");
//     }

//     public function inicioSesion($nombre_usuario, $password)
//     {
//         return $this->request->request('GET', "/inicioSesion/{$nombre_usuario}/{$password}");
//     }
// }

// <?php
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
        $response = $this->request->request('GET', $this->basePath . "/inicioSesion/{$nombre_usuario}/{$password}");
        if (!$response) throw new Exception("Error al iniciar sesión");
        return $response;
    }
}