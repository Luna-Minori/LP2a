package Front_end;

import javax.swing.*;
import Back_end.*;
import java.awt.*;
import java.util.ArrayList;
import Back_end.*;

public class Front_Board {
	    private Front_Player frontPlayer; // Le panneau représentant la partie du joueur

	    public Front_Board() {
	        // Définir les propriétés de la fenêtre principale (plateau)
	    	JFrame frame = new JFrame();
	        frame.setTitle("Lama Game - Plateau");
	        frame.setSize(1000, 800); // Taille du plateau
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setLocationRelativeTo(null); // Centrer la fenêtre

	        // Créer le Front_Player (panneau du joueur)
	        Board b = new Board();
	        b.addPlayer(new Player("Luna", true));
	        b.new_turn();
	        
	        
	        
	        Front_Player p = new Front_Player(b.getPlayers().get(0));

	        // Ajouter le Front_Player au JFrame
	        frame.add(p, BorderLayout.SOUTH); // Le panneau occupe la partie inférieure (50% de la hauteur)

	        // Ajouter un listener pour ajuster le positionnement et la taille dynamiquement
	        frame.addComponentListener(new java.awt.event.ComponentAdapter() {
	            public void componentResized(java.awt.event.ComponentEvent e) {
	                int height = frame.getHeight(); // Hauteur de la fenêtre parent
	                int width = frame.getWidth(); // Largeur de la fenêtre parent

	                // Appeler la méthode pour ajuster la taille du Front_Player
	                p.adjustSize(height, width);

	                frame.repaint();
	            }
	        });

	        // Rendre la fenêtre visible
	        frame.setVisible(true);
	        frame.repaint();
	    }
}
