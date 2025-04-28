package Front_end;

import java.awt.*;
import javax.swing.*;

import Back_end.*;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

public class Game {

    public Game() {
        JFrame frame = new JFrame("Lama Game");
        frame.setSize(1000, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        ImageIcon bgIcon = new ImageIcon("C:/Users/Luna/eclipse-workspace/Lp2a_Project/src/Front_end/Back.png");
        Image bgImage = bgIcon.getImage();
        BackgroundPanel background = new BackgroundPanel(bgImage);
        background.setLayout(null); // position libre

        frame.setContentPane(background);
        
        Board b = new Board();
        b.addPlayer(new Player("Luna", true));
        b.new_turn();
        int nbPlayer = b.getPlayers().size();
        for(int i = 0; i < nbPlayer; i++) {
        	ShowHand(b.getPlayers().get(i), frame);
        }
        // Ajout du texte
        String nb_tour = "5";
        JTextArea textArea = new JTextArea(nb_tour);
        textArea.setFont(new Font("Arial", Font.PLAIN, 100));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setOpaque(false);

        // Correction du bounds du texte
        textArea.setBounds(50, 50, 100, 100); // x=50, y=50
        background.add(textArea);



        frame.setVisible(true);
    }
    
    protected void ShowHand(Player p, JFrame frame) {
    	// Création d'un joueur fictif avec quelques cartes pour le test
        

        // (Normalement tu les pioche depuis un Deck mais pour l'instant on simule)

        // Maintenant, afficher la main du joueur
        ArrayList<Card> hand = p.getHand();
        CardPanel[] cartes = new CardPanel[hand.size()];
        for (int i = 0; i < hand.size(); i++) {
            Card carte = hand.get(i);
            
            cartes[i] = new CardPanel(0.15f, 0.4f, carte.getImagePath());
            System.out.println(hand.get(i).getValue());
            System.out.println("Carte " + i + ": Position " + cartes[i].getBounds() + ", Taille: " + cartes[i].getWidth() + "x" + cartes[i].getHeight());
            // Ajoute l'effet de main de cartes : rotation et décalage
            int angle = (i - hand.size() / 2) * 10; // cartes écartées et tournées
            int decalageX = (i - hand.size() / 2) * 30; 
            int decalageY = Math.abs(i - hand.size() / 2) * 5; // effet de petit éventail

            cartes[i].setRotationAndOffset(angle, decalageX, decalageY);
            Listener(cartes[i], frame);
            frame.add(cartes[i]);

        }

        // Redimensionnement au démarrage
        for (CardPanel carte : cartes) {
            carte.resizeWithin(frame);
        }
    }
    
    private void Listener(CardPanel carte, JFrame frame){
    	
	    // Responsive quand on redimensionne
	    frame.addComponentListener(new ComponentAdapter() {
	        @Override
	        public void componentResized(ComponentEvent e) {
	            carte.resizeWithin(frame);
	            frame.repaint();
	        }
	    });
    }
}
