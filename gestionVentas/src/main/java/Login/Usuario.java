package Login;

public class Usuario {
    private Integer id;
    private String name;
    private String password;
    private Integer rol;
    
    // Constructores
    public Usuario() {}
    
    public Usuario(String name, String password) {
        this.name = name;
        this.password = password;
    }
    
    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public Integer getRol() { return rol; }
    public void setRol(Integer rol) { this.rol = rol; }
}