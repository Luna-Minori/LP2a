package Front_end;

import java.awt.*;
import javax.swing.*;
import java.util.function.Consumer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

    public class CardPanel extends JPanel {
        private float widthPercent;
        private float heightPercent;
        private Image FrontCard;
        private Image BackCard;
        private boolean front;
        private int value;
        private Consumer<Integer> onCardClicked;
        private boolean handcard;
        private int totalPlayers; // for responsive

        public CardPanel(float widthPercent, float heightPercent, int value, int totalPlayers, boolean front, boolean playanimation, boolean needclicked) {
            this.widthPercent = widthPercent;
            this.heightPercent = heightPercent;
            this.front = front;
            this.handcard = playanimation;
            this.totalPlayers = totalPlayers;
            this.value = value;
            // Charger l'image de la carte
            ImageIcon FrontC = new ImageIcon("./src/Front_end/Card_" + value + ".png");
            ImageIcon BackC = new ImageIcon("./src/Front_end/Card_Back.png");
            this.FrontCard = FrontC.getImage();
            this.BackCard = BackC.getImage();
            setOpaque(false);
            
            if(handcard) {
            	int y = 25;
	                MouseAdapter hoverEffect = new MouseAdapter() {
	                    private int originalY;
	
	                    @Override
	                    public void mouseEntered(MouseEvent e) {
	                        CardPanel card = (CardPanel) e.getSource();
	                        originalY = card.getY();
	                        if(front) {
	                        	card.setLocation(card.getX(), originalY - y); // Soulève la carte
	                        }
	                        else {
	                        	card.setLocation(card.getX(), originalY + y/4);
	                        }
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
            if(needclicked) {
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Action à effectuer lorsque la carte est cliquée
                        handleCardClick();
                    }
                });
            }
            
        }

        public CardPanel(float widthPercent, float heightPercent, int value, boolean front, boolean playanimation, boolean needclicked) {
           this(widthPercent, heightPercent, value, 1, front, playanimation, needclicked);
        }
        
        private void handleCardClick() {
            if (onCardClicked != null) {
                System.out.println("clique callback CardPanel ");
                onCardClicked.accept(this.value);
                //onCardClicked = null;
            }
        }
		/*
		 * Resize CardPanel
		 */
        public void resizeWithin(Container parent) {
        	//System.out.println("Nom du parent : " + parent.getClass().getName());
            int parentWidth = parent.getWidth();
            int parentHeight = parent.getHeight();
            double frontAspectRatio = (double) FrontCard.getWidth(null) / FrontCard.getHeight(null);
            double backAspectRatio = (double) BackCard.getWidth(null) / BackCard.getHeight(null);
            if(handcard) {
                if(front && FrontCard != null) {
    	            int w = (int) (widthPercent * parentWidth);
    	            int h = (int) (w / frontAspectRatio);
    	
    	            if (h > (int) (heightPercent * parentHeight)) {
    	                h = (int) (heightPercent * parentHeight);
    	                w = (int) (h * frontAspectRatio);
    	            }
    	            setSize(w, h); // PAS setBounds ici !
                }
                
    	        if(!front && BackCard != null){
    	        	if (parent instanceof HandPanel) {
    	        	    HandPanel handPanel = (HandPanel) parent;
    	        	    int h = parentHeight;//handPanel.getCardPanels().size();
    	                int w = (int) (((parentWidth*(totalPlayers-1))/handPanel.getCardPanels().size())*0.95);
    	                setSize(w, h); // Carte arrière prend toute la taille disponible
    	        	}
	                
    	        }
            }
            else {
	            if(front && FrontCard != null) {
	                int w = parentWidth / 6;
	                int h = (int) (w / frontAspectRatio);
	                setSize(w, h); // Carte arrière prend toute la taille disponible
	            }
	            
		        if(!front && BackCard != null){
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
        	repaint();
        }
    
    @Override
    /*
     * Resize the image of CardPanel
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(front && FrontCard != null) {
	          	resizeWithin(getParent());  // Assurer que l'image est redimensionnée correctement
	            Graphics2D g2d = (Graphics2D) g;
	            int width = (int) (getWidth()*0.95);
	            int height = (int) (getHeight()*0.95);
	            g2d.drawImage(FrontCard, 0, 0, width, height, this);
        }
        
    	if(!front && BackCard != null){
            resizeWithin(getParent());  // Assurer que l'image est redimensionnée correctement
            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();
            if(handcard) {
                g2d.drawImage(BackCard, 0, 0, width, height, this);
            }
            else {
            	g2d.drawImage(BackCard, 15, 0, (int) (width*0.9), (int) (height*0.9), this);
            }

    	}
        
    }
    
    public void setOnCardClicked(Consumer<Integer> listener) {
        this.onCardClicked = listener;
    }

    protected void update(int value, boolean front){
        ImageIcon FrontC = new ImageIcon("./src/Front_end/Card_" + value + ".png");
        this.FrontCard = FrontC.getImage();
        this.front = front;
    }
}
