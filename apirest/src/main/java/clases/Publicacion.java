package clases;

import java.beans.Statement;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class Publicacion {
    private int id_publicacion;
    private int id_usuario;
    private String nombre_usuario;
    private Date fecha_publicacion;
    private int num_likes;

    // #region SET Y GET
    public void setId_publicacion(int id_publicacion) {
        this.id_publicacion = id_publicacion;
    }

    public int getId_publicacion() {
        return id_publicacion;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setNombre_usuario(String nombre_usuario){
        this.nombre_usuario = nombre_usuario;
    }
    public String getNombre_usuario(){
        return nombre_usuario;
    }

    public void setFecha_publicacion(Date fecha_publicacion) {
        this.fecha_publicacion = fecha_publicacion;
    }

    public Date getFecha_publicacion() {
        return fecha_publicacion;
    }

    public void setNum_likes(int num_likes) {
        this.num_likes = num_likes;
    }

    public int getNum_likes() {
        return num_likes;
    }
    // #endregion

    // #region CONSTRUCTORES
    public Publicacion() {
    }

    public Publicacion(int id_usuario, String nombre_usuario, Date fecha_publicacion, int num_likes) {
        this.id_usuario = id_usuario;
        this.fecha_publicacion = fecha_publicacion;
        this.num_likes = num_likes;
    }

    // #endregion
    @path("/publicacion")
    
    @POST
    @Path("/publicar")
    public Response subirPublicacion(Publicacion publicacion){
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conexion = c.getConexion();
            Statement st = conexion.createStatement();
            st.executeQuery("INSERT INTO publicaciones (nombre_usuario, fecha_publicacion, num_likes) VALUES ('?', '?', '?')");
            PreparedStatement ps = conexion.prepareStatement(sql)
            ps.setString(2, publicacion.getNombre_usuario());
            ps.setDate(3, publicacion.getFecha_publicacion());
            ps.setInt(4, publicacion.getNum_likes());
            return Response.ok("Subido correctamente").build(); 
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
        }
    }
}

