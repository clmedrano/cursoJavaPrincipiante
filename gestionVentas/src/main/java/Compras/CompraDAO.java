package Compras;

import BaseDatos.ConexionMySQL;
import Proveedor.ProveedorDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// DAO = Data Access Object → patrón común para operaciones de base de datos
public class CompraDAO {
    private ConexionMySQL conexion;
    private ProveedorDAO proveedorDAO = new ProveedorDAO();
    
    public CompraDAO() {
        this.conexion = new ConexionMySQL();
    }
    
    /**
     * Insertar una nueva categoría
     * @param nit
     * @param proveedor
     * @param fecha
     * @param total
     * @return 
     */
    public Integer insertarCabecera(Integer nit, String proveedor, Date fecha, Double total) {
        String sql = "INSERT INTO compra (nit, proveedor, fecha, total) VALUES (?, ?, ?, ?)";
        try (Connection conn = conexion.getConexion();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, nit);
            ps.setString(2, proveedor);
            ps.setDate(3, new java.sql.Date(fecha.getTime()));
            ps.setDouble(4, total);
            ps.executeUpdate();
            
            proveedorDAO.guardarProveedor(nit, proveedor);
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Devuelve el ID generado
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Insertar una nueva categoría
     * @param idCompra
     * @param idProducto
     * @param cantidad
     * @param precio
     * @param subTotal
     * @return 
     */
    public boolean insertarItem(Integer idCompra, Integer idProducto, Integer cantidad, Double precio, Double subTotal) {
        String sql = """
            INSERT INTO compra_item (idcompra, idproducto, cantidad, precio, subtotal) 
            VALUES (?, ?, ?, ?, ?)
            """;
        
        try (Connection conn = conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCompra);
            ps.setInt(2, idProducto);
            ps.setInt(3, cantidad);
            ps.setDouble(4, precio);
            ps.setDouble(5, subTotal);
            
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtener todas las compras
     * @return 
     */
    public List<Compra> listar() {
        List<Compra> compra = new ArrayList<>();
        String sql = """
                     SELECT c.id, c.nit, c.proveedor, c.fecha, c.total
                     FROM compra c""";
        
        try (Connection conn = conexion.getConexion();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Compra c = new Compra();
                c.setId(rs.getInt("id"));
                c.setNit(rs.getInt("nit"));
                c.setProveedor(rs.getString("proveedor"));
                c.setFecha(rs.getDate("fecha"));
                c.setTotal(rs.getDouble("total"));
                
                compra.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return compra;
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