package Front_end;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.*;
import javax.swing.*;


public class CardPanel extends JPanel {
    private float widthPercent;
    private float heightPercent;
    private Image bgImage;

    /*
     * widthPercent, heightPercent, directory
     */
    public CardPanel(float widthPercent, float heightPercent, String lien) {
        this.widthPercent = widthPercent;
        this.heightPercent = heightPercent;

        ImageIcon bgIcon = new ImageIcon(lien);
        this.bgImage = bgIcon.getImage();
        setOpaque(false); // On va dessiner manuellement le fond
        setLayout(null);  // Pour positionner des éléments librement
    }
    
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
	
    public void resizeWithin(Container parent) {
        int parentWidth = parent.getWidth();
        int parentHeight = parent.getHeight();

        // Dimensions cibles maximales en pourcentage
        int maxW = (int) (widthPercent * parentWidth);
        int maxH = (int) (heightPercent * parentHeight);

        // Ratio d'origine de l'élément
        double aspectRatio = (double) getWidth() / getHeight();

        int w = maxW;
        int h = (int) (w / aspectRatio);

        if (h > maxH) {
            h = maxH;
            w = (int) (h * aspectRatio);
        }

        int x = (parentWidth - w) / 2;
        int y = (parentHeight - h) / 2;

        setBounds(x, y, w, h);
    }

}
