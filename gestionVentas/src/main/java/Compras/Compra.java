package Compras;

import Categoria.Categoria;

public class Compra {
    private Integer id;
    private String nombre;
    private Categoria categoria;
    private Double precioCompra;
    private Double precioVenta;
    private Integer idcategoria;
    private String grupo;
    private Integer saldo;
    
    // Constructores
    public Compra() {}
    public Compra(String nombre, Categoria categoria) {
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
    
    public Double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(Double precioVenta) { this.precioVenta = precioVenta; }
    
    public Integer getIdCategoria() { return idcategoria; }
    public void setIdCategoria(Integer idcategoria) { this.idcategoria = idcategoria; }
    
    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }
    
    public Integer getSaldo() { return saldo; }
    public void setSaldo(Integer saldo) { this.saldo = saldo; }
    
    @Override
    public String toString() {
        return nombre; // útil para JComboBox, JList
    }
    
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}