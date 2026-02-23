package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    static final String url = "jdbc:mariadb://sql.freedb.tech:3306/freedb_Proyecto";
    static final String user = "freedb_DiegoCosta";
    static final String password = "2?#T#@qg5S&2sEr";
    Connection conexion;

    public Conexion() {
        try {
            conexion = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa a la base de datos");
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace();
        }
    }

    public Connection getConexion() {
        return conexion;
    }

    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
