package Ventas;

import java.util.Date;

public class Venta {
    private Integer id;
    private Integer nit;
    private String cliente;
    private Date fecha;
    private Double total;
    
    // Constructores
    public Venta() {}
    
    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public Integer getNit() { return nit; }
    public void setNit(Integer nit) { this.nit = nit; }
    
    public String getCliente() { return cliente; }
    public void setCliente(String nombre) { this.cliente = nombre; }
    
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
}