package Front_end;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.*;
import javax.swing.JPanel;

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
        setOpaque(true); // On va dessiner manuellement le fond
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
        int w = (int) (widthPercent * parentWidth);
        int h = (int) (heightPercent * parentHeight);
        int x = (parentWidth - w) / 2; // centré horizontalement
        int y = (parentHeight - h) / 2; // centré verticalement
        setBounds(x, y, w, h);
    }
}
