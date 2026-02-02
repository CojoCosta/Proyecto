package com.example.proyecto;

import android.widget.ImageView;

import java.sql.Blob;
import java.sql.Date;

public class Usuario {
    private ImageView fotoUsuario;
    private String nombre;
    private String apellidos;
    private String nombreUsuario;
    private String email;
    private Date fechaNacimiento;
    private String password;

    public void setFotoUsuario(ImageView fotoUsuario) { this.fotoUsuario = fotoUsuario; }

    public ImageView getFotoUsuario() { return fotoUsuario; }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNombre() {
        return nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public String getApellidos() {
        return apellidos;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public Date getFechaNacimiento() { return fechaNacimiento; }

    public Usuario(ImageView fotoUsuario, String nombreUsuario, String nombre, String apellidos, String email, Date fechaNacimiento, String password){
        this.fotoUsuario = fotoUsuario;
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.password = password;
    }
}
