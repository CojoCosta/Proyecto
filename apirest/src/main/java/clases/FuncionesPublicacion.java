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
            String query = "INSERT INTO publicaciones (nombre_usuario, contenido, fecha_publicacion, num_likes) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setString(1, publicacion.getNombre_usuario());
            ps.setString(2, publicacion.getContenido());
            ps.setString(3, publicacion.getFecha_publicacion());
            ps.setInt(4, publicacion.getNum_likes());
            ps.executeUpdate();
            return Response.ok("Publicación creada correctamente").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error: " + e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPublicaciones() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM publicaciones ORDER BY id_publicacion DESC");
            
            StringBuilder json = new StringBuilder("[");
            boolean first = true;
            while (rs.next()) {
                if (!first) json.append(",");
                json.append("{");
                json.append("\"id_publicacion\":").append(rs.getInt("id_publicacion")).append(",");
                json.append("\"nombre_usuario\":\"").append(rs.getString("nombre_usuario")).append("\",");
                json.append("\"contenido\":\"").append(rs.getString("contenido")).append("\",");
                json.append("\"fecha_publicacion\":\"").append(rs.getString("fecha_publicacion")).append("\",");
                json.append("\"num_likes\":").append(rs.getInt("num_likes"));
                json.append("}");
                first = false;
            }
            json.append("]");
            
            return Response.ok(json.toString()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error: " + e.getMessage()).build();
        }
    }
}
