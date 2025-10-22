package Movimientos_Items;

import BaseDatos.ConexionMySQL;
import java.sql.*;

public class MovimientosDAO {
    private ConexionMySQL conexion;
    
    public MovimientosDAO() {
        this.conexion = new ConexionMySQL();
    }
    
    /**
     * Registra un movimiento de inventario (ingreso o salida)
     * @param idProducto ID del producto
     * @param fecha Fecha del movimiento
     * @param hora Hora del movimiento  
     * @param tipoMov "C" para compra (ingreso), "V" para venta (salida)
     * @param cantidad Cantidad del movimiento
     * @param descripcion Descripción adicional del movimiento
     * @return true si se registró correctamente
     */
    public boolean insertarMovimiento(Integer idProducto, Date fecha, Time hora, String tipoMov, Integer cantidad, String descripcion) {
        if (idProducto == null || fecha == null || hora == null || 
            tipoMov == null || cantidad == null || cantidad <= 0) {
            return false;
        }
        
        Connection conn = null;
        try {
            conn = conexion.getConexion();
            conn.setAutoCommit(false);
            
            // 1. Obtener el saldo actual del producto
            Integer saldoActual = obtenerSaldoProducto(conn, idProducto);
            
            // 2. Calcular nuevo saldo
            Integer cantIngreso = "C".equals(tipoMov) ? cantidad : 0;
            Integer cantSalida = "V".equals(tipoMov) ? cantidad : 0;
            Integer nuevoSaldo = saldoActual + cantIngreso - cantSalida;
            
            // Validar saldo negativo (opcional pero recomendado)
            if (nuevoSaldo < 0) {
                throw new SQLException("Saldo insuficiente para la venta");
            }
            
            // 3. Actualizar saldo en producto
            actualizarSaldoProducto(conn, idProducto, nuevoSaldo);
            
            // 4. Insertar movimiento
            String sql = """
                INSERT INTO movimientos(
                    idproducto, fecha, hora, descripcion, ingreso, salida, saldo
                ) VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
            
            String descMovimiento = (descripcion != null ? descripcion : "") + 
                                  " - " + ("C".equals(tipoMov) ? "Ingreso por compra" : "Salida por venta") + 
                                  " el " + fecha.toString();
            
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idProducto);
                ps.setDate(2, fecha);
                ps.setTime(3, hora);
                ps.setString(4, descMovimiento);
                ps.setInt(5, cantIngreso);
                ps.setInt(6, cantSalida);
                ps.setInt(7, nuevoSaldo);
                ps.executeUpdate();
            }
            
            conn.commit();
            return true;
            
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private Integer obtenerSaldoProducto(Connection conn, Integer idProducto) throws SQLException {
        String sql = "SELECT COALESCE(saldo, 0) as saldo FROM producto WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("saldo");
                }
            }
        }
        return 0; // Si el producto no existe, saldo 0
    }
    
    private void actualizarSaldoProducto(Connection conn, Integer idProducto, Integer nuevoSaldo) throws SQLException {
        String sql = "UPDATE producto SET saldo = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nuevoSaldo);
            ps.setInt(2, idProducto);
            ps.executeUpdate();
        }
    }
}