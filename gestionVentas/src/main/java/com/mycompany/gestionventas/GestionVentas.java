package com.mycompany.gestionventas;

import Dashboard.Dashboard;
import Login.Login;
import java.awt.Frame;
import static java.awt.SystemColor.window;
import java.awt.Window;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class GestionVentas {

    public static void main(String[] args) {
        //System.out.println("Hello World!");
        
        Login login = new Login(null, true);
        login.setVisible(true); // bloquea hasta que se cierre
        
        if (login.isLoginExitoso()) {
            JOptionPane.showMessageDialog(null, 
                "¡Bienvenido, " + login.getUsuarioAutenticado() + "!");
            
            // ✅ Abrir Dashboard como JFrame (ventana principal)
            Dashboard dashboard = new Dashboard(login.getUsuarioAutenticado());
            dashboard.setVisible(true);
            
            // El login se cierra automáticamente, y el dashboard toma el control
        } else {
            System.exit(0);
        }
    }
}
