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
        g.setFont(getFont()); // Définir la police
        g.setColor(getForeground()); // Définir la couleur du texte
        g.drawString(text, 0, getHeight() / 2); // Dessiner le texte à la position (10, 30)
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
