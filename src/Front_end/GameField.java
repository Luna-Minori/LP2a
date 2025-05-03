package Front_end;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import Back_end.*;


public class GameField extends JPanel {
    private DrawPanel DrawPanel; // Le panneau contenant les cartes du joueur
    private TextPanel textPanel;
    private CardPanel BinCardPanel;// Le panneau de texte (par exemple pour afficher les tours restants)

    // Constructeur
    public GameField(Board board) {
        setLayout(null);
        setOpaque(false);

        createBinCardPanel(board.getBin().getValue());
        createDrawPanel(board.getDeck());
        add(BinCardPanel);
        add(DrawPanel);
        
        // Attendre que le GameField soit affiché pour ajuster les tailles
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
            	Responsive();
            }
        });
    }

    private void createBinCardPanel(int value) {
        System.out.println("Bin pannel");
        BinCardPanel = new CardPanel(1, 1, "./src/Front_end/Card_" + value + ".png", true, false, false);
        BinCardPanel.setLayout(null);
    }
    
    private void createDrawPanel(ArrayList<Card> Deck) {
    	ArrayList<CardPanel> draw = new ArrayList<CardPanel>();
    	float cardscale = 0.75f;
    	if(Deck.size() <= 10) {
	        for (int i = 0; i < Deck.size(); ++i) {
	        	CardPanel temp = new CardPanel(cardscale, cardscale, "./src/Front_end/Card_" + Deck.get(i).getValue() + ".png", false, false, false);
	            temp.setLayout(null);
	            draw.add(temp);
	        }
    	}
    	else {
	        for (int i = 0; i < Deck.size()/3; ++i) {
	            CardPanel temp;
	            if(i == 0) {
	                temp = new CardPanel(cardscale, cardscale, "./src/Front_end/Card_" + Deck.get(i).getValue() + ".png", false, false, true);
	                System.out.println("une fois");
	            } else {
	        	    temp = new CardPanel(cardscale, cardscale, "./src/Front_end/Card_" + Deck.get(i).getValue() + ".png", false, false, false);
	            }
	            temp.setLayout(null);
	            draw.add(temp);
	        }
    	}
        DrawPanel = new DrawPanel(draw);
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
    
    private void Responsive() {
        int panelWidth = (int) (getWidth() * 0.25);

        // Position de BinCardPanel (à gauche du centre)
        int xBin = (int) ((getWidth() - panelWidth) / 2.5);
        int y = (int) ((getHeight() - getHeight()) / 5.5);
        BinCardPanel.setBounds(xBin, y, panelWidth, (int) (getHeight()*0.9));
        // Position de DrawPanel (symétrique à droite)	
        int xDraw = (int) ((getWidth() - panelWidth) / 1.5f);
        DrawPanel.setBounds(xDraw, y, panelWidth, getHeight());
    }
}
