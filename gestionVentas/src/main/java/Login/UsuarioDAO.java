package Login;

import BaseDatos.ConexionMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    private ConexionMySQL conexion;
    
    public UsuarioDAO() {
        this.conexion = new ConexionMySQL();
    }
    
    /**
     * Autentica un usuario por nombre y contraseña
     * @param name Nombre de usuario
     * @param password Contraseña (en texto plano - ver nota de seguridad)
     * @return Usuario autenticado o null si falla
     */
    public Usuario autenticar(String name, String password) {
        if (name == null || password == null) {
            return null;
        }
        
        String sql = "SELECT id, name, password, rol FROM usuario WHERE name = ? AND password = ?";
        
        try (Connection conn = conexion.getConexion();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, name.trim());
            ps.setString(2, password); // Ver nota de seguridad abajo
            //System.out.println(ps);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setName(rs.getString("name"));
                    u.setPassword(rs.getString("password"));
                    u.setRol(rs.getInt("rol"));
                    return u;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}