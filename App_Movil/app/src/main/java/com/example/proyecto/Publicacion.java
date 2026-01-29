package com.example.proyecto;

import android.widget.ImageView;

public class Publicacion {
    private int nombreUsuario;
    private String fechaPublicacion;
    private int numLikes;
    private int fotoPublicacion;

    public void setNombreUsuario(int idUsuario) {
        this.nombreUsuario = idUsuario;
    }

    public int getNombreUsuario() {
        return nombreUsuario;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getFechaPublicacion() {
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
    public Publicacion(int idUsuario, String fechaPublicacion, int numLikes, ImageView fotoPublicacion){
        this.nombreUsuario = 0;
        this.fechaPublicacion = "";
        this.numLikes = 0;
        this.fechaPublicacion = "";
    }
}
