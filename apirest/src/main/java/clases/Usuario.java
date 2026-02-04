package clases;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.annotation.processing.Generated;

public class Usuario {
    private int id_usuario;
    private String nombre_usuario;
    private String nombre;
    private String apellidos;
    private String email;
    private Date fecha_nacimiento;
    private String password;

    Conexion c = new Conexion();

    @Path("usuario")
    // #region SET Y GET
    public void setIdUsuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId() {
        return id_usuario;
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
    // #endregion

    // #region CONSTRUCTORES
    public Usuario() {
    }

    public Usuario(int idUsuario, String nombre, String apellidos, String nombreUsuario, String email, Date fechaNacimiento, String password) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.password = password;
    }
    // #endregion

    @POST
    @Path("/insertar") // ACABAR ESTO
    public Response insertarUsuario(Usuario usuario) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conexion = c.getConexion();
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

    @GET
    @Path("/inicioSesion/{nombre_usuario}/{password}")
    public Response obtenerDatosUsuario(@PathParam("nombre_usuario") String nombre_usuario @PathParam("password") String password1){
        Usuario usuario = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conexion = c.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT nombre_usuario, password FROM usuarios WHERE nombre_usuario LIKE '"+ nombre_usuario +"' AND password LIKE '"+ password1 +"'");
            if(rs.next()) {
                usuario = new Usuario(rs.getString("nombre_usuario"), rs.getString("password"));
            }
            return Response.ok(usuario).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
        }
    }

    @GET
    @Path("/perfil/{nombre_usuario}")
    public Response datosParaPerfil(@PathParam("nombre_usuario") String nombre_usuario) {
        Usuario usuario = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conexion = c.getConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT *FROM usuarios WHERE nombre_usuario LIKE '" + nombre_usuario + "'");
            if (rs.next()) {
                usuario = new Usuario(rs.getString("nombre_usuario"), rs.getString("nombre"), rs.getString("apellidos"),
                        rs.getString("email"), rs.getDate("fecha_nacimiento"), rs.getString("password"));
            }
            return Response.ok(usuario).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
        }
    }


}

