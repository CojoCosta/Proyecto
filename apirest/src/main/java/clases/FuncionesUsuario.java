package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuarios")
public class FuncionesUsuario {

    static final String url = "jdbc:mariadb://sql.freedb.tech:3306/freedb_Proyecto";
    static final String user = "freedb_DiegoCosta";
    static final String password = "2?#T#@qg5S&2sEr";
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuarios() {
        Usuario usuario = null;
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM usuarios");
            while (rs.next()) {
                usuario = new Usuario(rs.getString("nombre_usuario"), rs.getString("nombre"), rs.getString("apellidos"), rs.getString("email"), rs.getString("fecha_nacimiento"), rs.getString("password"));
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuarios.add(usuario);
            }
            return Response.ok(usuarios).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
        }
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertarUsuario(Usuario usuario) {
        try {
            System.out.println("hola");
            Class.forName("org.mariadb.jdbc.Driver");
            System.out.println("fo");
            Connection conexion = DriverManager.getConnection(url, user, password);
            String query = "INSERT INTO usuarios (nombre_usuario, nombre, apellidos, email, fecha_nacimiento, password) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getApellidos());
            ps.setString(4, usuario.getEmail());
            ps.setString(5, usuario.getFechaNacimiento());
            ps.setString(6, usuario.getPassword());
            ps.executeUpdate();
            return Response.ok("Subido correctamente").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
        }
    }

    @POST
    @Path("/inicioSesion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response inicioSesion(LoginRequest loginRequest) {
        if (loginRequest == null
                || loginRequest.getNombreUsuarioResolved() == null
                || loginRequest.getPassword() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Peticion invalida").build();
        }
        return autenticar(loginRequest.getNombreUsuarioResolved(), loginRequest.getPassword());
    }

    // Compatibilidad con versiones antiguas de clientes (URL params).
    @POST
    @Path("/inicioSesion/{nombre_usuario}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response inicioSesionLegacyPost(@PathParam("nombre_usuario") String nombreUsuario,
                                           @PathParam("password") String passwordLogin) {
        return autenticar(nombreUsuario, passwordLogin);
    }

    // Compatibilidad adicional si algun cliente antiguo usa GET.
    @GET
    @Path("/inicioSesion/{nombre_usuario}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response inicioSesionLegacyGet(@PathParam("nombre_usuario") String nombreUsuario,
                                          @PathParam("password") String passwordLogin) {
        return autenticar(nombreUsuario, passwordLogin);
    }

    private Response autenticar(String nombreUsuario, String passwordLogin) {
        Usuario usuario = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = conexion
                    .prepareStatement("SELECT * FROM usuarios WHERE nombre_usuario = ? AND password = ?");
            ps.setString(1, nombreUsuario);
            ps.setString(2, passwordLogin);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario = new Usuario(rs.getString("nombre_usuario"), rs.getString("nombre"), rs.getString("apellidos"),rs.getString("email"), rs.getString("fecha_nacimiento"), rs.getString("password"));
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                return Response.ok(usuario).build();
            }
            return Response.status(Response.Status.UNAUTHORIZED).entity("Credenciales incorrectas").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/perfil/{nombre_usuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerDatosUsuario(@PathParam("nombre_usuario") String nombre_usuario) {
        Usuario usuario = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM usuarios WHERE nombre_usuario LIKE '" + nombre_usuario + "'");
            if (rs.next()) {
                usuario = new Usuario(rs.getString("nombre_usuario"), rs.getString("nombre"), rs.getString("apellidos"),
                        rs.getString("email"), rs.getString("fecha_nacimiento"), rs.getString("password"));
                usuario.setIdUsuario(rs.getInt("id_usuario"));
            }
            return Response.ok(usuario).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
        }
    }

    @PUT
    @Path("/modificar/{nombre_usuario}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modificarUsuario(@PathParam("nombre_usuario") String nombre_usuario, Usuario usuario) {
        try (Connection conexion = DriverManager.getConnection(url, user, password);
        PreparedStatement ps = conexion.prepareStatement("UPDATE usuarios SET nombre=?, apellidos=?, email=?, password=? WHERE nombre_usuario=?")){
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getPassword());
            ps.setString(5, nombre_usuario);
            ps.executeUpdate();
            return Response.ok("Actualizado").build();
        } catch (Exception e) {
            return Response.status(500).entity("Error interno: " + e.getMessage()).build();
        }
    }


    public static class LoginRequest {
        private String nombreUsuario;
        private String nombre_usuario;
        private String password;

        public String getNombreUsuario() {
            return nombreUsuario;
        }

        public void setNombreUsuario(String nombreUsuario) {
            this.nombreUsuario = nombreUsuario;
        }

        public String getNombre_usuario() {
            return nombre_usuario;
        }

        public void setNombre_usuario(String nombre_usuario) {
            this.nombre_usuario = nombre_usuario;
        }

        public String getNombreUsuarioResolved() {
            if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
                return nombreUsuario;
            }
            return nombre_usuario;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
