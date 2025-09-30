package Producto;

import BaseDatos.ConexionMySQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// DAO = Data Access Object → patrón común para operaciones de base de datos
public class ProductoDAO {
    private ConexionMySQL conexion;

    public ProductoDAO() {
        this.conexion = new ConexionMySQL();
    }

    /**
     * Insertar una nueva categoría
     * @param nombre
     * @param idcategoria
     * @return 
     */
    public boolean insertar(String nombre, Integer idcategoria) {
        String sql = "INSERT INTO producto (name, idcategoria) VALUES (?, ?)";
        try (Connection conn = conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setInt(2, idcategoria);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtener todas las categorías
     * @return 
     */
    public List<Producto> listar() {
        List<Producto> categorias = new ArrayList<>();
        String sql = "SELECT id, name, precio_compra, precio_venta, idcategoria, saldo FROM producto";

        try (Connection conn = conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto c = new Producto();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("name"));
                c.setPrecioCompra(rs.getDouble("precio_compra"));
                categorias.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categorias;
    }

    /**
     * Actualizar una categoría
     * @param id
     * @param nombre
     * @return 
     */
    public boolean actualizar(int id, String nombre) {
        String sql = "UPDATE categoria SET name = ? WHERE id = ?";
        try (Connection conn = conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Eliminar una categoría por ID
     * @param id
     * @return 
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM categoria WHERE id = ?";
        try (Connection conn = conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}