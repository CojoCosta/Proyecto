package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/publicaciones")
public class FuncionesPublicacion {
    static final String url = "jdbc:mariadb://sql.freedb.tech:3306/freedb_Proyecto";
    static final String user = "freedb_DiegoCosta";
    static final String password = "2?#T#@qg5S&2sEr";

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response subirPublicacion(Publicacion publicacion) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);

            int idUsuario = publicacion.getId_usuario();
            if (idUsuario <= 0 && publicacion.getNombre_usuario() != null) {
                PreparedStatement psUsuario = conexion
                        .prepareStatement("SELECT id_usuario FROM usuarios WHERE nombre_usuario = ?");
                psUsuario.setString(1, publicacion.getNombre_usuario());
                ResultSet rsUsuario = psUsuario.executeQuery();
                if (rsUsuario.next()) {
                    idUsuario = rsUsuario.getInt("id_usuario");
                }
            }

            if (idUsuario <= 0) {
                return Response.status(Response.Status.BAD_REQUEST).entity("id_usuario invalido").build();
            }

            String query = "INSERT INTO publicaciones (id_usuario, nombre_usuario, contenido, fecha_publicacion) VALUES (?, ?, ?, CURRENT_DATE)";
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setInt(1, idUsuario);
            ps.setString(2, publicacion.getNombre_usuario());
            ps.setString(3, publicacion.getContenido());
            ps.executeUpdate();
            return Response.ok("Publicacion creada correctamente").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error: " + e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPublicaciones() {
        Publicacion publi = null;
        ArrayList<Publicacion> publicaciones2 = new ArrayList<>();
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM publicaciones ORDER BY id_publicacion DESC");
            while (rs.next()) {
                publi = new Publicacion(rs.getInt("id_publicacion"), rs.getInt("id_usuario"), rs.getString("nombre_usuario"), rs.getString("contenido"), rs.getString("fecha_publicacion"));
                publicaciones2.add(publi);
            }
            return Response.ok(publicaciones2).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error: " + e.getMessage()).build();
        }
    }
}
