package Categoria;

import BaseDatos.ConexionMySQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// DAO = Data Access Object → patrón común para operaciones de base de datos
public class CategoriaDAO {
    private ConexionMySQL conexion;

    public CategoriaDAO() {
        this.conexion = new ConexionMySQL();
    }

    /**
     * Insertar una nueva categoría
     * @param nombre
     * @return 
     */
    public boolean insertar(String nombre) {
        String sql = "INSERT INTO categoria (name) VALUES (?)";
        try (Connection conn = conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
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
    public List<Categoria> listar() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT id, name FROM categoria";

        try (Connection conn = conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Categoria c = new Categoria();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("name"));
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