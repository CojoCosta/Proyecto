package com.example.proyecto;

import android.widget.ImageView;

import java.io.Serializable;
import java.sql.Date;

public class Usuario implements Serializable {
    private int id_usuario;
    private String nombre;
    private String apellidos;
    private String nombre_usuario;
    private String email;
    private Date fecha_nacimiento;
    private String password;

    public void setId_usuario(int id_usuario) { this.id_usuario = id_usuario; }

    public int getId_usuario() { return id_usuario; }

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

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }
    public String getNombre_usuario() {
        return nombre_usuario;
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

    public void setFecha_nacimiento(Date fecha_nacimiento) { this.fecha_nacimiento = fecha_nacimiento; }

    public Date getFecha_nacimiento() { return fecha_nacimiento; }

    public Usuario( String nombreUsuario, String nombre, String apellidos, String email, Date fechaNacimiento, String password){
        this.nombre_usuario = nombreUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.fecha_nacimiento = fechaNacimiento;
        this.password = password;
    }
    public Usuario (String nombre_usuario, String password){
        this.nombre_usuario = nombre_usuario;
        this.password = password;
    }
}
