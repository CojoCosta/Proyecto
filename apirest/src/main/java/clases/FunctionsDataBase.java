public class FunctionsDataBase {
    private static final String url = "jdbc:mariadb://sql.freedb.tech:3306/freedb_Proyecto";
    private static final String user = "freedb_DiegoCosta";
    private static final String password = "2?#T#@qg5S&2sEr";
    ArrayList<Usuario> usuarios = new ArrayList<>();
    //Subir una consulta
    @POST
    @Path("/registro")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response subirDeportistaAndroid(Usuario usuario) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, user, password);
            Statement st = conexion.createStatement();
            st.executeUpdate(String.format("INSERT INTO usuarios (nombre_usuario, nombre, apellidos, email, fecha_nacimiento, password) VALUES ('%s', '%s' ,'%s', '%s' ,'%s', '%s')", usuario.getNombreUsuario(), usuario.getNombre(), usuario.getApellidos, usuario.getEmail(), usuario.getFechaNacimiento(), usuario.getPassword()));
            return Response.ok("Usuario añadido").build(); // Esto solo muestra json
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error").build();
        }
    }
}
