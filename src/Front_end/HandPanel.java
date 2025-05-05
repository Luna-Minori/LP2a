package Front_end;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class HandPanel extends JPanel {
    private ArrayList<CardPanel> cardPanels; // Liste des panneaux de cartes
    private boolean mainplayer;

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
                carte.setBounds(calculateCardPosition(i[0])); // Calculer la position pour l'éventail
                this.cardPanels.add(carte); // Ajouter la carte à la liste
                this.add(carte); // Ajouter la carte au panel
                i[0]++;
                this.repaint();
            } else {
                ((Timer) e.getSource()).stop();
            }
        }).start();
    }

    // Calculer la position des cartes pour un affichage en éventail
    private Rectangle calculateCardPosition(int index) {
    	if(mainplayer) {
    		return new Rectangle( (int) (index* getWidth()*0.1), 25, 1, 200);
    	}
    	else {
    		return new Rectangle( (int) (index * getWidth()*0.1), (int) -(getHeight()*0.5), 1, 200);
    	}
         // Positionner verticalement et définir taille
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
                carte.setBounds(calculateCardPosition(i)); // Calculer la position pour l'éventail
                this.cardPanels.add(carte); // Ajouter la carte à la liste
                this.add(carte); // Ajouter la carte au panel
                this.repaint();
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
