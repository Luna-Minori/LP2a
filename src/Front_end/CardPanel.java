package Front_end;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

    public class CardPanel extends JPanel {
        private float widthPercent;
        private float heightPercent;
        private Image FrontCard;
        private Image BackCard;
        private boolean front;
        private float scale = 0.6f;
        private Timer animationTimer;
        private boolean handcard;

        public CardPanel(float widthPercent, float heightPercent, String lien, boolean front, boolean playanimation, boolean needclicked) {
            this.widthPercent = widthPercent;
            this.heightPercent = heightPercent;
            this.front = front;
            this.handcard = playanimation;
            // Charger l'image de la carte
            ImageIcon FrontC = new ImageIcon(lien);
            ImageIcon BackC = new ImageIcon("./src/Front_end/Card_Back.png");
            this.FrontCard = FrontC.getImage();
            this.BackCard = BackC.getImage();
            setOpaque(false);

            
            if( playanimation == true) {
            	startAnimation();
            	
                MouseAdapter hoverEffect = new MouseAdapter() {
                    private int originalY;

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        CardPanel card = (CardPanel) e.getSource();
                        originalY = card.getY();
                        System.out.println("hello");
                        card.setLocation(card.getX(), originalY - 25); // Soulève la carte
                        card.repaint();
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        CardPanel card = (CardPanel) e.getSource();
                        card.setLocation(card.getX(), originalY); // Repose la carte
                        card.repaint();
                    }
                };
                this.addMouseListener(hoverEffect);
            }
            if(needclicked == true) {
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Action à effectuer lorsque la carte est cliquée
                        handleCardClick();
                    }
                });
            }
            
        }

        private void handleCardClick() {
            // Action à exécuter lors du clic sur la carte
            System.out.println("Carte cliquée !");
            // Tu peux ajouter ici la logique pour gérer l'action lors du clic, comme changer l'état du jeu
            // Par exemple, afficher un message, effectuer une action de jeu, etc.
        }
		/*
		 * Resize CardPanel
		 */
        public void resizeWithin(Container parent) {
            int parentWidth = parent.getWidth();
            int parentHeight = parent.getHeight();
            double frontAspectRatio = (double) FrontCard.getWidth(null) / FrontCard.getHeight(null);
            double backAspectRatio = (double) BackCard.getWidth(null) / BackCard.getHeight(null);
            if(handcard) {
                if(front == true && FrontCard != null) {
    	            int w = (int) (widthPercent * parentWidth);
    	            int h = (int) (w / frontAspectRatio);
    	
    	            if (h > (int) (heightPercent * parentHeight)) {
    	                h = (int) (heightPercent * parentHeight);
    	                w = (int) (h * frontAspectRatio);
    	            }
    	            setSize(w, h); // PAS setBounds ici !
                }
                
    	        if(front == false && BackCard != null){
    	            int w = (int) (widthPercent * parentWidth);
    	            int h = (int) (w / backAspectRatio);
    	            setSize(w, h); // PAS setBounds ici !
    	            
    	            if (h > parentHeight) {
    	                h = parentHeight;
    	                w = (int) (h * backAspectRatio);
    	            }
    	            setSize(w,h);
    	        }
            }
            else {
	            if(front == true && FrontCard != null) {
	                int w = parentWidth / 6;
	                int h = (int) (w / frontAspectRatio);
/*
	                if (h > parentHeight) {
	                    h = parentHeight;
	                    w = (int) (h * frontAspectRatio);
	                }
*/
	                setSize(w, h); // Carte arrière prend toute la taille disponible
	            }
	            
		        if(front == false && BackCard != null){
    	            int w = (int) (widthPercent * parentWidth);
    	            int h = (int) (w / backAspectRatio);
    	            setSize(w, h); // PAS setBounds ici !
    	            
    	            if (h > parentHeight) {
    	                h = parentHeight;
    	                w = (int) (h * backAspectRatio);
    	            }

		            setSize(w, h); // Carte arrière prend toute la taille disponible
		        }
            }
        }
        
        private void flip() {
        	front = !front;
        }
    
    @Override
    /*
     * Resize the image of CardPanel
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(front == true && FrontCard != null) {
	          	resizeWithin(getParent());  // Assurer que l'image est redimensionnée correctement
	            Graphics2D g2d = (Graphics2D) g;
	            int width = (int) (getWidth()*0.95);
	            int height = (int) (getHeight()*0.95);
	            g2d.drawImage(FrontCard, 0, 0, width, height, this);
        }
        
    	if(front == false && BackCard != null){
            resizeWithin(getParent());  // Assurer que l'image est redimensionnée correctement
            Graphics2D g2d = (Graphics2D) g;
            int width = (int) (getWidth() * 0.90);
            int height = (int) (getHeight() * 0.90);
            g2d.drawImage(BackCard, 15, 0, width, height, this);
    	}
        
    }


    private void startAnimation() {
        animationTimer = new Timer(20, e -> {
            scale += 0.015f;
            if (scale >= 1.0f) {
                scale = 1.0f;
                animationTimer.stop();
            }
            repaint();
        });
        animationTimer.start();
    }
}
