package Categoria;

import java.awt.BorderLayout;
import javax.swing.JPanel;

public class Categoria {
    private Integer id;
    private String nombre;
    
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