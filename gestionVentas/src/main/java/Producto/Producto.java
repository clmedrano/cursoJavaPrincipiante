package Producto;

import Categoria.Categoria;

public class Producto {
    private Integer id;
    private String nombre;
    private Categoria categoria;
    private Double precioCompra;
    private Float precioVenta;
    private Integer idcategoria;
    private Double saldo;
    
    // Constructores
    public Producto() {}
    public Producto(String nombre, Categoria categoria) {
        this.nombre = nombre;
        this.categoria = categoria;
    }
    
    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public Double getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(Double precioCompra) { this.precioCompra = precioCompra; }
    
    @Override
    public String toString() {
        return nombre; // útil para JComboBox, JList
    }
    
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}