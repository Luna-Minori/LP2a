package Front_end;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class InfoPanel extends JPanel {
	
	    private ArrayList<InfoPlayer> infoPlayer; // Le panneau contenant les cartes du joueur
	    private TextPanel textPanel;

	    // Constructeur
	    public InfoPanel(ArrayList<ArrayList<Integer>> hands, ArrayList<String> names, int MainPlayer) {
	        setLayout(null);
	        setOpaque(false);
	        infoPlayer = new ArrayList<>();
	        int j = 0;
	        for (int i = 0; i < hands.size(); i++) {
	        	if(MainPlayer != i) {
		            infoPlayer.add(new InfoPlayer(hands.get(i), hands.size(), names.get(i)));
		            add(infoPlayer.get(j));
		            j++;
	        	}
	        	
	        }

	        // ✅ Écouteur pour positionner après affichage
	        addComponentListener(new ComponentAdapter() {
	            @Override
	            public void componentResized(ComponentEvent e) {
	                PositionInfoPlayers(hands.size());
	            }
	        });
	    }


	    private void PositionInfoPlayers(int nbP) {
	    	int nbPlayer = nbP;
	    	int marge = 50;
	    	int w = (getWidth()-marge) / nbPlayer ;
	    	int h = (int) (getHeight() / 1.5f);
	    	
	    	for(int i=0; i < nbPlayer; i++) {
	    		infoPlayer.get(i).setBounds(w * i +marge/2, 0, w, h);	  
	    	}
	    	
	    }   
}