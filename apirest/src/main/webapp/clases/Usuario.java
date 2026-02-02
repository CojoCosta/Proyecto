public class Usuario {
    private Blob fotoUsuario;
    private String nombreUsuario;
    private String nombre;
    private String apellidos;
    private String email;
    private String fechaNacimiento;
    private String password;

    @Path("usuario")
//#region SET Y GET
    public void setFotoUsuario(Blob fotoUsuario){
        this.fotoUsuario = fotoUsuario;
    }
    public Blob getFotoUsuario() {
        return fotoUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNombre() {
        return nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public String getApellidos() {
        return apellidos;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
 //#endregion

    public Usuario(Blob fotoUsuario, String nombre, String apellidos, String nombreUsuario, String email, String fechaNacimiento, String password){
        this.fotoUsuario = fotoUsuario;
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.password = password;
    }

    @POST
    @Path("/insertar") //ACABAR ESTO
    public Response insertarUsuarioMovil(Usuario usuario){
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            Statement st = conexion.createStatement();
            st.executeUpdate(String.format("INSERT INTO deportistas (nombreUsuario, nombre, apellidos, email) VALUES ('%s', '%s')", deportista.getNombre(), deportista.getDeporte()));
            return Response.ok("Subido correctamente").build(); // Esto solo muestra json
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
        }
    }
}

