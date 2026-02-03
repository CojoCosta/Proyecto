package clases;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class Usuario {
    private int id_usuario;
    private Blob foto_usuario;
    private String nombre_usuario;
    private String nombre;
    private String apellidos;
    private String email;
    private Date fecha_nacimiento;
    private String password;

    @Path("usuario")
//#region SET Y GET
    public void setIdUsuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
    public int getId() {
        return id_usuario;
    }

    public void setFotoUsuario(Blob fotoUsuario){
        this.foto_usuario = foto_usuario;
    }
    public Blob getFotoUsuario() {
        return foto_usuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombre_usuario = nombre_usuario;
    }
    public String getNombreUsuario() {
        return nombre_usuario;
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

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }
    public String getFechaNacimiento() {
        return fecha_nacimiento;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
 //#endregion

//#region CONSTRUCTORES
    public Usuario(){ }
    public Usuario(int idUsuario, Blob fotoUsuario, String nombre, String apellidos, String nombreUsuario, String email, Date fechaNacimiento, String password){
        this.idUsuario = idUsuario;
        this.fotoUsuario = fotoUsuario;
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.password = password;
    }
//#endregion

    @POST
    @Path("/insertar") //ACABAR ESTO
    public Response insertarUsuarioMovil(Usuario usuario){
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            Statement st = conexion.createStatement();
            st.executeUpdate("INSERT INTO deportistas (nombre_usuario, nombre, apellidos, email, fecha_nacimiento, password) VALUES ('?', '?', '?', '?', '?', '?')");
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getApellidos());
            ps.setString(4, usuario.getEmail());
            ps.setString(5, usuario.getFechaNacimiento());
            ps.setString(6, usuario.getPassword());
            return Response.ok("Subido correctamente").build(); // Esto solo muestra json
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
        }
    }
}

