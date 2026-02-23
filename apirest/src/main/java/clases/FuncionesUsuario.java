package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuarios")
public class FuncionesUsuario {

    static final String url = "jdbc:mariadb://sql.freedb.tech:3306/freedb_Proyecto";
    static final String user = "freedb_DiegoCosta";
    static final String password = "2?#T#@qg5S&2sEr";

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertarUsuario(Usuario usuario) {
        try {
            System.out.println("hola");
            Class.forName("org.mariadb.jdbc.Driver");
            System.out.println("fo");
            Connection conexion = DriverManager.getConnection(url, user, password);
            String query = "INSERT INTO usuarios (nombre_usuario, nombre, apellidos, email, fecha_nacimiento, password) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getApellidos());
            ps.setString(4, usuario.getEmail());
            ps.setString(5, usuario.getFechaNacimiento());
            ps.setString(6, usuario.getPassword());
            ps.executeUpdate();
            return Response.ok("Subido correctamente").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
        }
    }

    @GET
    @Path("/inicioSesion/{nombre_usuario}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response inicioSesion(@PathParam("nombre_usuario") String nombre_usuario,
            @PathParam("password") String password1) {
        Usuario usuario = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = conexion
                    .prepareStatement("SELECT * FROM usuarios WHERE nombre_usuario = ? AND password = ?");
            ps.setString(1, nombre_usuario);
            ps.setString(2, password1);
            ResultSet rs = ps.executeQuery();
            return Response.ok(usuario).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
        }
    }

    @GET
    @Path("/perfil/{nombre_usuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerDatosUsuario(@PathParam("nombre_usuario") String nombre_usuario) {
        Usuario usuario = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM usuarios WHERE nombre_usuario LIKE '" + nombre_usuario + "'");
            if (rs.next()) {
                usuario = new Usuario(rs.getString("nombre_usuario"), rs.getString("nombre"), rs.getString("apellidos"),
                        rs.getString("email"), rs.getString("fecha_nacimiento"), rs.getString("password"));
            }
            return Response.ok(usuario).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
        }
    }

    @PUT
    @Path("/modificar/{nombre_usuario}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modificarUsuario(@PathParam("nombre_usuario") String nombre_usuario, Usuario usuario) {
        try {
            Connection conexion = DriverManager.getConnection(url, user, password);
            String query = "UPDATE usuarios SET nombre=?, apellidos=?, email=?, password=? WHERE nombre_usuario=?";
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getPassword());
            ps.setString(5, nombre_usuario);

            ps.executeUpdate();

            return Response.ok("Actualizado").build();
        } catch (Exception e) {
            return Response.status(500).entity("Error").build();
        }
    }

    // @POST
    // @Path("/eliminar/{nombre_usuario}")
}
