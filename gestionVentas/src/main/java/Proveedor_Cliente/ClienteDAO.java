package Proveedor_Cliente;

import BaseDatos.ConexionMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {
    private ConexionMySQL conexion;
    
    public ClienteDAO() {
        this.conexion = new ConexionMySQL();
    }
    
    /**
     * Guarda o actualiza un cliente por NIT
     * @param nit
     * @param nombre
     * @return true si se guardó/actualizó correctamente
     */
    public boolean guardarCliente(Integer nit, String nombre) {
        // Primero intentamos actualizar
        if (actualizarCliente(nit, nombre)) {
            return true;
        }
        // Si no existe, insertamos
        return insertarCliente(nit, nombre);
    }
    
    private boolean actualizarCliente(Integer nit, String nombre) {
        String sql = "UPDATE cliente SET nombre = ? WHERE nit = ?";
        try (Connection conn = conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setInt(2, nit);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean insertarCliente(Integer nit, String nombre) {
        String nombreCliente = obtenerNombreClientePorNit(nit);
        if(nombreCliente != null) {
            return false;
        }
        
        String sql = "INSERT INTO cliente (nit, nombre) VALUES (?, ?)";
        try (Connection conn = conexion.getConexion();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nit);
            ps.setString(2, nombre);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            // Si falla por duplicado (en caso de concurrencia), intentamos actualizar
            if (e.getErrorCode() == 1062) { // Código de error de MySQL para duplicado
                return actualizarCliente(nit, nombre);
            }
            e.printStackTrace();
            return false;
        }
    }
    
    /**
    * Obtiene el nombre del cliente dado su NIT.
    * 
    * @param nit NIT del cliente a buscar
    * @return Nombre del cliente si existe, null si no se encuentra
    */
    public String obtenerNombreClientePorNit(Integer nit) {
        if (nit == null) {
           return null;
        }
        
        String sql = "SELECT nombre FROM cliente WHERE nit = ?";
        
        try (Connection conn = conexion.getConexion();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, nit);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nombre");
                }
            }
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return null; // No encontrado o error
   }
}