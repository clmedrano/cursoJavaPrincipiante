package Categoria;

public class Categoria {
    private Integer id;
    private String nombre;
    
    public Categoria() {
        this.id = null;
        this.nombre = "-- Seleccione --";
    }
    
    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    @Override
    public String toString() {
        return nombre; // útil para JComboBox, JList
    }
}