package com.example.proyecto.Modelos;

import java.sql.Date;

public class Publicacion {
    private int id_publicacion;
    private int id_usuario;
    private String nombre_usuario;
    private String contenido;
    private String fecha_publicacion;

    public void setIdPublicacion(int id_publicacion) {
        this.id_publicacion = id_publicacion;
    }

    public int getIdPublicacion() {
        return id_publicacion;
    }

    public void setIdUsuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getIdUsuario() {
        return id_usuario;
    }

    public void setNombreUsuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getNombreUsuario() {
        return nombre_usuario;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getContenido() {
        return contenido;
    }

    public void setFechaPublicacion(String fecha_publicacion) {
        this.fecha_publicacion = fecha_publicacion;
    }

    public String getFechaPublicacion() {
        return fecha_publicacion;
    }
    public Publicacion() {
    }

    public Publicacion(String nombre_usuario, String contenido, String fecha_publicacion) {
        this.nombre_usuario = nombre_usuario;
        this.contenido = contenido;
        this.fecha_publicacion = fecha_publicacion;
    }
}
