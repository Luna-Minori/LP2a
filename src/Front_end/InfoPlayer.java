package Front_end;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import Back_end.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class InfoPlayer extends JPanel {
	private String name;
	private HandPanel handPanel;
	
	public InfoPlayer(Player p) {
		this.name = p.getName();
        setLayout(null);
        setOpaque(true);
        setBackground(Color.BLUE);
        // create Panel for player
        createHandPanel(p);
        add(handPanel);
        adjustHandPanelSize(); // try to be responsive	
        // ✅ Ajouter un listener pour être responsive
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustHandPanelSize();
            }
        });
    }

    private void createHandPanel(Player p) {
        // Créer la liste des chemins d'images des cartes du joueur
        ArrayList<Card> hand = p.getHand();

        // Créer une liste de chemins d'images pour les cartes
        ArrayList<CardPanel> Card = new ArrayList<CardPanel>();
        for (int i = 0; i < hand.size(); ++i) {//this.imagePath = "C:/Users/Luna/eclipse-workspace/Lp2a_Project/src/Front_end/" +/* value */"1"+ ".png";
            //URL imageUrl = getClass().getResource("Card_" + hand.get(i).getValue() + ".png");
        	System.out.println("Working directory: " + System.getProperty("user.dir"));
            CardPanel temp = new CardPanel(0.2f, 0.2f, "./src/Front_end/Card_" + hand.get(i).getValue() + ".png", true, true, true);
            System.out.println("./Front_end/Card_"+hand.get(i).getValue()+".png");
            temp.setLayout(null);
            Card.add(temp);
        }
        // Créer le panneau principal de la main de cartes
        handPanel = new HandPanel(Card, 20);
    }
    
    // Méthode pour ajuster la taille du HandPanel pour qu'il occupe un pourcentage de Front_Player
    private void adjustHandPanelSize() {
        float coefHeightHand = 0.6f; // hand of player use x % of the Panel front player

        // Ajuster la taille et la position du HandPanel
        handPanel.setBounds(10,10, 100,200);
        }

    // Méthode pour redimensionner le Front_Player et ajuster le HandPanel
    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        // Réajuster la taille du HandPanel chaque fois que le parent est redimensionné
        adjustHandPanelSize();
    }
}
