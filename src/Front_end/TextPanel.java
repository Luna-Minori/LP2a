package Front_end;

import javax.swing.*;
import java.awt.*;

public class TextPanel extends JPanel {
    private String text;

    public TextPanel(String text) {
        this.text = text;
        setOpaque(false); // Le panneau est transparent
    }

    // Redéfinir paintComponent pour dessiner le texte
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.BOLD, 24)); // Définir la police
        g.setColor(Color.BLACK); // Définir la couleur du texte
        g.drawString(text, 10, 30); // Dessiner le texte à la position (10, 30)
    }

    // Méthode pour mettre à jour le texte
    public void setText(String newText) {
        this.text = newText;
        repaint(); // Redessiner le panneau
    }
    
    public String getText() {
    	return text;
    }
}
