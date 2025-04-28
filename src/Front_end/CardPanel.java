package Front_end;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

    public class CardPanel extends JPanel {
        private float widthPercent = 0.1f;
        private float heightPercent = 0.2f;
        private Image bgImage;
        private float scale = 0.8f;
        private Timer animationTimer;

        public CardPanel(float widthPercent, float heightPercent, String lien) {
            this.widthPercent = widthPercent;
            this.heightPercent = heightPercent;

            // Charger l'image de la carte
            ImageIcon bgIcon = new ImageIcon(lien);
            this.bgImage = bgIcon.getImage();
            setOpaque(false);
            setLayout(null);

            // Ajouter le MouseListener pour détecter un clic
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Action à effectuer lorsque la carte est cliquée
                    handleCardClick();
                }
            });
            startAnimation();
        }

        private void handleCardClick() {
            // Action à exécuter lors du clic sur la carte
            System.out.println("Carte cliquée !");
            // Tu peux ajouter ici la logique pour gérer l'action lors du clic, comme changer l'état du jeu
            // Par exemple, afficher un message, effectuer une action de jeu, etc.
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

            setSize(w, h); // PAS setBounds ici !
        }

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            resizeWithin(getParent());  // Assurer que l'image est redimensionnée correctement
            Graphics2D g2d = (Graphics2D) g;
            int width = (int) (getWidth() * scale);
            int height = (int) (getHeight() * scale);
            g2d.drawImage(bgImage, 0, 0, width, height, this);
        }
    }


    private void startAnimation() {
        animationTimer = new Timer(20, e -> {
            scale += 0.01f;
            if (scale >= 1.0f) {
                scale = 1.0f;
                animationTimer.stop();
            }
            repaint();
        });
        animationTimer.start();
    }
}
