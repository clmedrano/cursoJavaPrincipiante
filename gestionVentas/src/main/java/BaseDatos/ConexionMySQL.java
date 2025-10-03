package BaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {
    // Parámetros de conexión
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_ventas";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "";
//    private static final String CONTRASENA = "root2025UBI_sucre";
    
    /**
     * Método para obtener la conexión a la base de datos
     * @return Connection
     */
    public Connection getConexion() {
        try {
            // Cargar el driver (opcional en versiones recientes, pero recomendado)
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establecer la conexión
            return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
//            System.out.println("Conexión exitosa a la base de datos.");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver de MySQL.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos.");
            e.printStackTrace();
        }
        return null; // si falla, devuelve null
    }

    /**
     * Método para cerrar la conexión
     * @param conn
     */
    public void cerrarConexion(Connection conn) {
        if (conn != null) {
            try {
                if(!conn.isClosed()) {
                    conn.close();
                    System.out.println("✅ Conexión cerrada.");
                }
            } catch (SQLException e) {
                System.err.println("❌ Error al cerrar la conexión.");
                e.printStackTrace();
            }
        }
    }
}