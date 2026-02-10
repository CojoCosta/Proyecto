package clases;

import java.sql.Connection;
import java.sql.Date;
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


public class Usuario {
    private int id_usuario;
    private String nombre_usuario;
    private String nombre;
    private String apellidos;
    private String email;
    private Date fecha_nacimiento;
    private String password;
    
    Conexion c = new Conexion();

    // #region SET Y GET
    public void setIdUsuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId() {
        return id_usuario;
    }

    public void setNombreUsuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getNombreUsuario() {
        return nombre_usuario;
    }

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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setFechaNacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public Date getFechaNacimiento() {
        return fecha_nacimiento;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    // #endregion

    // #region CONSTRUCTORES
    public Usuario() {
    }

    public Usuario(String nombre_usuario, String nombre, String apellidos, String email, Date fecha_nacimiento,
            String password) {
        this.nombre_usuario = nombre_usuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.fecha_nacimiento = fecha_nacimiento;
        this.password = password;
    }
    // #endregion


}
