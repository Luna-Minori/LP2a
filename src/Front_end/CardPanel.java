package Front_end;

import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;

public class CardPanel extends JPanel {
    private float widthPercent;
    private float heightPercent;
    private Image bgImage;
    private float scale = 0.8f; // Démarrage animation
    private Timer animationTimer;
    
    private double rotationAngle = 0; // en degrés
    private int offsetX = 0;
    private int offsetY = 0;

    public CardPanel(float widthPercent, float heightPercent, String lien) {
        this.widthPercent = widthPercent;
        this.heightPercent = heightPercent;

        ImageIcon bgIcon = new ImageIcon(lien);
        this.bgImage = bgIcon.getImage();
        setOpaque(false);
        setLayout(null);

        startAnimation();
    }

    // ➔ Setter pour régler l'angle et le décalage
    public void setRotationAndOffset(double angleDegrees, int offsetX, int offsetY) {
        this.rotationAngle = angleDegrees;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            int imgWidth = (int) (getWidth() * scale);
            int imgHeight = (int) (getHeight() * scale);
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;

            g2d.translate(centerX + offsetX, centerY + offsetY);
            g2d.rotate(Math.toRadians(rotationAngle));
            g2d.drawImage(bgImage, -imgWidth / 2, -imgHeight / 2, imgWidth, imgHeight, this);
            g2d.dispose();
        }
    }

    public void resizeWithin(Container parent) {
        int parentWidth = parent.getWidth();
        int parentHeight = parent.getHeight();

        if (bgImage == null) return;

        double aspectRatio = (double) bgImage.getWidth(null) / bgImage.getHeight(null);

        int w = (int) (widthPercent * parentWidth);
        int h = (int) (w / aspectRatio);

        if (h > (int) (heightPercent * parentHeight)) {
            h = (int) (heightPercent * parentHeight);
            w = (int) (h * aspectRatio);
        }

        int x = (parentWidth - w) / 2;
        int y = (parentHeight - h) / 2;

        setBounds(x, y, w, h);
    }

    private void startAnimation() {
        animationTimer = new Timer(20, e -> {
            scale += 0.02f;
            if (scale >= 1.0f) {
                scale = 1.0f;
                animationTimer.stop();
            }
            repaint();
        });
        animationTimer.start();
    }
}
