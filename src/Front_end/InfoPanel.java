package Front_end;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import Back_end.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class InfoPanel extends JPanel {
	
	    private ArrayList<InfoPlayer> infoPlayer; // Le panneau contenant les cartes du joueur
	    private TextPanel textPanel;

	    // Constructeur
	    public InfoPanel(Board board) {
	        setLayout(null);
	        setOpaque(true);
	        setBackground(Color.RED);
	        infoPlayer = new ArrayList<>();


	        for(int i = 0; i < board.getPlayers().size(); i++) {
	        	System.out.println(board.getPlayers().get(i).getName());
	        	infoPlayer.add(new InfoPlayer(board.getPlayers().get(i)));
	        	add(infoPlayer.get(i));
	        }
	    }
	    
	    private void adjustHandPanelSize() {
	        float coefHeightHand = 0.6f; // hand of player use x % of the Panel front player

	        // Ajuster la taille et la position du HandPanel
	        setBounds(10,10,100,100);
	    }


	/*
	    private void TextPanel(Player p) {
	        // Exemple de TextPanel pour afficher le nombre de tours restants
	        TextPanel Name = new TextPanel(p.getName());
	        Name.setBounds(100, 50, 300, 50); // Positionner le TextPanel
	        Name.setFont(new Font("Arial", Font.PLAIN, 34));
	        System.out.println("w "+ getWidth());
	        TextPanel TextPoints = new TextPanel("Hand Points :");
	        TextPoints.setBounds(10, 270, 220, 50);
	        TextPoints.setFont(new Font("Arial",Font.PLAIN, 24));
	        //Point.setColor(Color.black);
	        TextPanel Points = new TextPanel("" + p.getPoint());
	        Points.setBounds(85, 310, 50, 50);
	        Points.setFont(new Font("Arial",Font.PLAIN, 24));
	        add(Name);
	        add(TextPoints);
	        add(Points);
	    }
	   */     
}