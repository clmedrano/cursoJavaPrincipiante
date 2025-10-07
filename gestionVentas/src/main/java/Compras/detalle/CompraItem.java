package Compras.detalle;

import BaseDatos.ConexionMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// PASO 1: Crea una clase para representar el ítem de compra
public class CompraItem {
    private ConexionMySQL conexion;
    
    private Integer idCompra;
    private Integer idProducto;
    private String nombreProducto;
    private Integer cantidad;
    private Double precio;
    private Double subTotal;
    
    // Constructores
    public CompraItem() {
        this.conexion = new ConexionMySQL();
    }
    public CompraItem(Integer idProducto, String nombreProducto, Integer cantidad, Double precio) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.subTotal = cantidad * precio;
    }

    // Getters
    public Integer getIdcompra() { return idCompra; }
    public Integer getIdProducto() { return idProducto; }
    public String getNombreProducto() { return nombreProducto; }
    public Integer getCantidad() { return cantidad; }
    public Double getPrecio() { return precio; }
    public Double getSubTotal() { return subTotal; }
    
    // Setters
    public void setIdcompra(int idcompra) { this.idCompra = idcompra; }
    public void setNombreProducto(String nomProducto) { this.nombreProducto = nomProducto; }
    public void setCan(Integer cantidad) { this.cantidad = cantidad; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public void setSubTotal(Double subtotal) { this.subTotal = subtotal; }
    
    // Setter para cantidad (útil si permites editar)
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        this.subTotal = cantidad * precio;
    }
    
    /**
     * Obtener todos los items de la compra
     * @return 
     */
    public List<CompraItem> compraItem(Integer idcompra) {
        List<CompraItem> compra_item = new ArrayList<>();
        String sql = """
                     SELECT ci.id, ci.idcompra, ci.idproducto, p.name as nombre_producto, ci.cantidad, ci.precio, ci.subtotal
                     FROM compra_item ci INNER JOIN producto p ON ci.idproducto = p.id
                     WHERE ci.idcompra = ?
                     """;
        
        try (Connection conn = conexion.getConexion();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            
            // Establecer el parámetro
            ps.setInt(1, idcompra);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CompraItem c = new CompraItem();
                    c.setIdcompra(rs.getInt("idcompra"));
                    c.setNombreProducto(rs.getString("nombre_producto"));
                    c.setCan(rs.getInt("cantidad"));
                    c.setPrecio(rs.getDouble("precio"));
                    c.setSubTotal(rs.getDouble("subtotal"));

                    compra_item.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return compra_item;
    }
}