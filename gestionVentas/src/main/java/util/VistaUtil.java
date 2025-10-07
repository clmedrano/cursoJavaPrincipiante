package util;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VistaUtil {
    public void showPanel(JPanel panel, JPanel jpContenedor) {
        panel.setSize(426, 325);
        panel.setLocation(0, 0);
        
        jpContenedor.removeAll();
        jpContenedor.add(panel, BorderLayout.CENTER);
        jpContenedor.revalidate();
        jpContenedor.repaint();
    }
    
    public Integer valorEntero(JTextField txtElemento, JDialog dialog) {
        try {
            Integer valor = Integer.valueOf(txtElemento.getText().trim());
            return valor;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(dialog, "Cantidad inválida. Ingrese un número entero.");
            txtElemento.requestFocus();
            return 0;
        }
    }
    public Double valorDouble(JTextField txtElemento, JDialog dialog) {
        try {
            Double valor = Double.valueOf(txtElemento.getText().trim());
            return valor;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(dialog, "Cantidad inválida. Ingrese un número entero.");
            txtElemento.requestFocus();
            return 0.0;
        }
    }
}
