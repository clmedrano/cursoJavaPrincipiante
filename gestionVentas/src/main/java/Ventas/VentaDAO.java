package Ventas;

import BaseDatos.ConexionMySQL;
import Proveedor_Cliente.ClienteDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// DAO = Data Access Object → patrón común para operaciones de base de datos
public class VentaDAO {
    private ConexionMySQL conexion;
    private ClienteDAO clienteDAO = new ClienteDAO();
    
    public VentaDAO() {
        this.conexion = new ConexionMySQL();
    }
    
    /**
     * Insertar una nueva categoría
     * @param nit
     * @param cliente
     * @param fecha
     * @param total
     * @param descripcion
     * @return 
     */
    public Integer insertarCabecera(Integer nit, String cliente, Date fecha, Double total, String descripcion) {
        String sql = "INSERT INTO venta (nit, cliente, fecha, total, descripcion) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conexion.getConexion();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, nit);
            ps.setString(2, cliente);
            ps.setDate(3, new java.sql.Date(fecha.getTime()));
            ps.setDouble(4, total);
            ps.setString(5, descripcion);
            ps.executeUpdate();
            
            clienteDAO.guardarCliente(nit, cliente);
            
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
     * @param idVenta
     * @param idProducto
     * @param cantidad
     * @param precio
     * @param subTotal
     * @return 
     */
    public boolean insertarItem(Integer idVenta, Integer idProducto, Integer cantidad, Double precio, Double subTotal) {
        String sql = """
            INSERT INTO venta_item (idventa, idproducto, cantidad, precio, subtotal) 
            VALUES (?, ?, ?, ?, ?)
            """;
        
        try (Connection conn = conexion.getConexion();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idVenta);
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
    public List<Venta> listar() {
        List<Venta> venta = new ArrayList<>();
        String sql = """
                     SELECT c.id, c.nit, c.cliente, c.fecha, c.total
                     FROM venta c""";
        
        try (Connection conn = conexion.getConexion();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Venta c = new Venta();
                c.setId(rs.getInt("id"));
                c.setNit(rs.getInt("nit"));
                c.setCliente(rs.getString("cliente"));
                c.setFecha(rs.getDate("fecha"));
                c.setTotal(rs.getDouble("total"));
                
                venta.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return venta;
    }
    
//    /**
//     * Actualizar una categoría
//     * @param id
//     * @param nombre
//     * @param idcategoria
//     * @param precioVta
//     * @return 
//     */
//    public boolean actualizar(int id, String nombre, Integer idcategoria, Double precioVta) {
//        String sql = "UPDATE producto SET name = ?, idcategoria = ?, precio_venta = ? WHERE id = ?";
//        try (Connection conn = conexion.getConexion();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setString(1, nombre);
//            ps.setInt(2, idcategoria);
//            ps.setDouble(3, precioVta);
//            ps.setInt(4, id);
//            return ps.executeUpdate() > 0;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * Eliminar una categoría por ID
//     * @param id
//     * @return 
//     */
//    public boolean eliminar(int id) {
//        String sql = "DELETE FROM categoria WHERE id = ?";
//        try (Connection conn = conexion.getConexion();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setInt(1, id);
//            return ps.executeUpdate() > 0;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
    
    public Venta obtenerVentaPorId(Integer idVenta) {
        String sql = """
            SELECT id, nit, cliente, fecha, total 
            FROM venta 
            WHERE id = ?
            """;

        try (Connection conn = conexion.getConexion();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idVenta);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Venta venta = new Venta();
                    venta.setId(rs.getInt("id"));
                    venta.setNit(rs.getInt("nit"));
                    venta.setCliente(rs.getString("cliente"));
                    venta.setFecha(rs.getDate("fecha"));
                    venta.setTotal(rs.getDouble("total"));
                    return venta;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // No encontrada
    }
}