package Front_end;

import javax.swing.*;
import Back_end.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import Back_end.*;

public class BoardPanel {
	    private PlayerPanel frontPlayer; // Le panneau représentant la partie du joueur
	    private GameField gamefield;
	    
	    public BoardPanel() {
	        // Définir les propriétés de la fenêtre principale (plateau)
	    	JFrame frame = new JFrame();
	        frame.setTitle("Lama Game - Plateau");
	        frame.setSize(1000, 800); // Taille du plateau
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setLocationRelativeTo(null); // Centrer la fenêtre

	        ImageIcon bgIcon = new ImageIcon("C:/Users/Luna/eclipse-workspace/Lp2a_Project/src/Front_end/Back.png");
	        Image bgImage = bgIcon.getImage();
	        BackgroundPanel background = new BackgroundPanel(bgImage);
	        background.setLayout(null);
	        
	        frame.setContentPane(background);

	        
	        // Créer le Front_Player (panneau du joueur)
	        Board board = new Board();
	        board.addPlayer(new Player("Luna", true));
	        board.new_turn();
	        int BinCardValue =  board.getBin().getValue();

        	System.out.println("Working directory: " + System.getProperty("user.dir"));
        	
        	
            //TopPanel topPanel = new TopPanel();
	        PlayerPanel p = new PlayerPanel(board.getPlayers().get(0));
	        GameField gf = new GameField(board);

            // Ajout des panels
            //add(topPanel);
            frame.add(gf);
            frame.add(p);

            // Écouteur de redimensionnement pour adapter dynamiquement les tailles
            frame.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    int w = frame.getWidth();
                    int h = frame.getHeight();

                    //topPanel.setBounds(0, 0, w, (int)(h * 0.2));
                    gf.setBounds(0, (int)(h * 0.2), w, (int)(h * 0.4));
                    p.setBounds(0, h-p.getHeight()-50, w, (int)(h * 0.4));

                    frame.repaint(); // si besoin
                }
            });

            frame.setVisible(true);
        }
}

