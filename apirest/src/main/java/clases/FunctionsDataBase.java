package clases;

import java.awt.PageAttributes.MediaType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.annotation.processing.Generated;

public class FunctionsDataBase {
    private static final String url = "jdbc:mariadb://sql.freedb.tech:3306/freedb_Proyecto";
    private static final String user = "freedb_DiegoCosta";
    private static final String password = "2?#T#@qg5S&2sEr";
    ArrayList<Usuario> usuarios = new ArrayList<>();
    //Subir una consulta
    @POST
    @Path("/registro")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertarUsuarioM(Usuario usuario){
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            Statement st = conexion.createStatement();
            st.executeUpdate("INSERT INTO usuarios (nombre_usuario, nombre, apellidos, email, fecha_nacimiento, password) VALUES ('?', '?', '?', '?', '?', '?')");
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

    @Generated
    @Path("/inicioSesion")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inicioSesion(Usuario usuario){
        Class.forName("org.mariadb.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            Statement st = conexion.createStatement();
            st.executeUpdate("Select nombre_usuario, password FROM usuarios WHERE nombre_usuario LIKE '?' AND password LIKE '?'");
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, usuario.getNombreUsuario());

    }
}
