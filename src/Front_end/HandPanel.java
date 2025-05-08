package Front_end;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HandPanel extends JPanel {
    private ArrayList<CardPanel> cardPanels; // Liste des panneaux de cartes
    private boolean mainplayer;// Index de la carte en cours d'animation

    public HandPanel(ArrayList<CardPanel> hand, boolean MainPlayer) {
        this.cardPanels = new ArrayList<>();
        this.mainplayer = MainPlayer;
        setLayout(null); // Pour contrôler le placement des cartes
        setOpaque(false); // Transparence du panel


        // Lancer l'ajout des cartes après un délai
        int[] i = {0};
        new Timer(300, e -> {
            if (i[0] < hand.size()) {
                // Créer une nouvelle carte à partir de l'objet CardPanel
                CardPanel carte = hand.get(i[0]);
                // Attendre que la taille du 	panel soit calculée
                cardPanels.add(carte);
                carte.setBounds(calculateCardPosition(i[0])); // Calculer la position pour l'éventail
                add(carte); // Ajouter la carte au panel
                carte.startAnimation();
                i[0]++;
                repaint();
            } else {
                ((Timer) e.getSource()).stop();
            }
        }).start();
    }

    // Calculer la position des cartes pour un affichage en éventail
    private Rectangle calculateCardPosition(int index) {
        int x = (int) (index * getWidth() *0.1) ;
        int endx = x+100;

        System.out.println("x: " + x + " endx: " + endx + " width: " + getWidth());
        if(endx>= getWidth()) {
            x = (int) (((double) (index % getWidth()) / x) * getWidth() *0.1);
            System.out.println("col 2");
            if(mainplayer) {
                return new Rectangle(x,(int) (getHeight()*0.1), 1, 200);
            }
            return new Rectangle(x, (int) -(getHeight()*0.3), 1, 200);
        }
    	if(mainplayer) {
            System.out.println("col 1");
    		return new Rectangle(x,(int) (getHeight()*0.25), 1, 200);
    	}
        return new Rectangle(x, (int) -(getHeight()*0.5), 1, 200);
    }

    protected ArrayList<CardPanel> getCardPanels(){
    	return cardPanels;
    }
    
    protected void update(ArrayList<CardPanel> hand, boolean MainPlayer) {
    	this.mainplayer = MainPlayer;
        for(int i = 0; i < hand.size(); i++) {
                // Créer une nouvelle carte à partir de l'objet CardPanel
                CardPanel carte = hand.get(i);
                // Attendre que la taille du panel soit calculée
                cardPanels.add(carte); // Ajouter la carte à la liste
                carte.setBounds(calculateCardPosition(i)); // Calculer la position pour l'éventail
                add(carte); // Ajouter la carte au panel
                repaint();
        }
    }

    protected void clear() {
        for (CardPanel card : cardPanels) {
            this.remove(card);
        }
        cardPanels.clear();
        repaint();
    }
}
