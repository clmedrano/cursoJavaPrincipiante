package Categoria;

import java.awt.BorderLayout;
import javax.swing.JPanel;

public class Categoria {
    
    public void showPanel(JPanel panel, JPanel jpContenedor) {
        panel.setSize(426, 325);
        panel.setLocation(0, 0);
        
        jpContenedor.removeAll();
        jpContenedor.add(panel, BorderLayout.CENTER);
        jpContenedor.revalidate();
        jpContenedor.repaint();
    }
}
