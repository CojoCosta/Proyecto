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
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO publicaciones (nombre_usuario, fecha_publicacion, num_likes) VALUES ('?', '?', '?')");
            ps.setString(3, publicacion.getNombre_usuario());
            ps.setDate(4, publicacion.getFecha_publicacion());
            ps.setInt(5, publicacion.getNum_likes());
            return Response.ok("Subido correctamente").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
        }
    }
}
