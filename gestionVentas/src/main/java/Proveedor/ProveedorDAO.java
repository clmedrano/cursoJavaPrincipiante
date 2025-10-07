package Proveedor;

import BaseDatos.ConexionMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProveedorDAO {
    private ConexionMySQL conexion;

    public ProveedorDAO() {
        this.conexion = new ConexionMySQL();
    }

    /**
     * Guarda o actualiza un proveedor por NIT
     * @return true si se guardó/actualizó correctamente
     */
    public boolean guardarProveedor(Integer nit, String nombre) {
        // Primero intentamos actualizar
        if (actualizarProveedor(nit, nombre)) {
            return true;
        }
        // Si no existe, insertamos
        return insertarProveedor(nit, nombre);
    }

    private boolean actualizarProveedor(Integer nit, String nombre) {
        String sql = "UPDATE proveedor SET nombre = ? WHERE nit = ?";
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

    private boolean insertarProveedor(Integer nit, String nombre) {
        String sql = "INSERT INTO proveedor (nit, nombre) VALUES (?, ?)";
        try (Connection conn = conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nit);
            ps.setString(2, nombre);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            // Si falla por duplicado (en caso de concurrencia), intentamos actualizar
            if (e.getErrorCode() == 1062) { // Código de error de MySQL para duplicado
                return actualizarProveedor(nit, nombre);
            }
            e.printStackTrace();
            return false;
        }
    }
}