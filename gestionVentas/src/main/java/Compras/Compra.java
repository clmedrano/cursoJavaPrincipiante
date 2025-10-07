package Compras;

import java.util.Date;

public class Compra {
    private Integer id;
    private Integer nit;
    private String proveedor;
    private Date fecha;
    private Double total;
    
    // Constructores
    public Compra() {}
    
    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public Integer getNit() { return nit; }
    public void setNit(Integer nit) { this.nit = nit; }
    
    public String getProveedor() { return proveedor; }
    public void setProveedor(String nombre) { this.proveedor = nombre; }
    
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
}