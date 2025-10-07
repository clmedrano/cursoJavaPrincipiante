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
    public boolean insertar(String nombre, Integer idcategoria, Double precioVta) {
        String sql = "INSERT INTO producto (name, idcategoria, precio_venta) VALUES (?, ?, ?)";
        try (Connection conn = conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setInt(2, idcategoria);
            ps.setDouble(3, precioVta);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtener todas las categorías
     * @param query
     * @return 
     */
    public List<Producto> listar(String query) {
        List<Producto> productos = new ArrayList<>();
        
        // Base de la consulta
        StringBuilder sql = new StringBuilder("""
            SELECT p.id, p.name, p.precio_compra, p.precio_venta, p.idcategoria, c.name as grupo, p.saldo 
            FROM producto p 
            INNER JOIN categoria c ON p.idcategoria = c.id
            """);
        
        // Añadir condición de búsqueda si hay query
        if (query != null && !query.trim().isEmpty()) {
            sql.append(" WHERE p.name LIKE ?"); // ILIKE = case-insensitive en PostgreSQL
        }
        
        try (Connection conn = conexion.getConexion();
            PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            
            // Establecer parámetro si hay búsqueda
            if (query != null && !query.trim().isEmpty()) {
               ps.setString(1, "%" + query.trim() + "%");
            }
            
            try (ResultSet rs = ps.executeQuery()) {
               while (rs.next()) {
                    Producto c = new Producto();
                    c.setId(rs.getInt("id"));
                    c.setNombre(rs.getString("name"));
                    c.setPrecioCompra(rs.getDouble("precio_compra"));
                    c.setPrecioVenta(rs.getDouble("precio_venta"));
                    c.setIdCategoria(rs.getInt("idcategoria"));
                    c.setGrupo(rs.getString("grupo"));
                    c.setSaldo(rs.getInt("saldo"));

                    productos.add(c);
                }
           }
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return productos;
    }

    /**
     * Actualizar una categoría
     * @param id
     * @param nombre
     * @param idcategoria
     * @param precioVta
     * @return 
     */
    public boolean actualizar(int id, String nombre, Integer idcategoria, Double precioVta) {
        String sql = "UPDATE producto SET name = ?, idcategoria = ?, precio_venta = ? WHERE id = ?";
        try (Connection conn = conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setInt(2, idcategoria);
            ps.setDouble(3, precioVta);
            ps.setInt(4, id);
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