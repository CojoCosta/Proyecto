package com.example.proyecto;

import android.widget.ImageView;

import java.sql.Date;

public class Publicacion {
    private int idUsuario;
    private Date fechaPublicacion;
    private int numLikes;
    private int fotoPublicacion;

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setFotoPublicacion(int fotoPublicacion) {
        this.fotoPublicacion = fotoPublicacion;
    }

    public int getFotoPublicacion() {
        return fotoPublicacion;
    }
    public Publicacion(int idUsuario, int fotoPublicacion, int numLikes, Date fechaPublicacion){
        this.idUsuario = idUsuario;
        this.fotoPublicacion = fotoPublicacion;
        this.numLikes = numLikes;
        this.fechaPublicacion = fechaPublicacion;
    }
}
