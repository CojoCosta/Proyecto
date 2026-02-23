package clases;

import java.sql.Date;
import java.util.ArrayList;

import jakarta.ws.rs.Path;


public class Publicacion {
    private int id_publicacion;
    private int id_usuario;
    private String nombre_usuario;
    private String contenido;
    private String fecha_publicacion;
    ArrayList<Publicacion> publicaciones = new ArrayList<>();
    Conexion c = new Conexion();

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

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }
    
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getContenido() {
        return contenido;
    }

    public void setFecha_publicacion(String fecha_publicacion) {
        this.fecha_publicacion = fecha_publicacion;
    }

    public String getFecha_publicacion() {
        return fecha_publicacion;
    }
    // #endregion

    // #region CONSTRUCTORES
    public Publicacion() {
    }

    public Publicacion(String nombre_usuario, String contenido, String fecha_publicacion) {
        this.nombre_usuario = nombre_usuario;
        this.contenido = contenido;
        this.fecha_publicacion = fecha_publicacion;
    }

    // #endregion


}
