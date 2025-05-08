package Front_end;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.function.Consumer;


public class GameField extends JPanel {
    private DrawPanel DrawPanel; // Le panneau contenant les cartes du joueur
    //private TextPanel textPanel;
    private CardPanel BinCardPanel;// Le panneau de texte (par exemple pour afficher les tours restants)
    private Consumer<Integer> onDrawClicked;
    private Consumer<Integer> HandDownClicked;
    // Constructeur
    public GameField(int bin, ArrayList<Integer> Deck) {
        setLayout(null);
        setOpaque(false);

        createBinCardPanel(bin);
        createDrawPanel(Deck);
        add(BinCardPanel);
        add(DrawPanel);
        
        // Attendre que le GameField soit affiché pour ajuster les tailles
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
            	Responsive();
            }
        });
        
        DrawPanel.drawClick((value)-> {
            if (onDrawClicked != null) {
            	onDrawClicked.accept(value); // ou null si JPanel inutile
                //onDrawClicked = null;
            }
        });
    }

    private void createBinCardPanel(int value) {
        System.out.println("Bin pannel");
        BinCardPanel = new CardPanel(value,  true, false, false);
        BinCardPanel.setLayout(null);
    }
    
    private void createDrawPanel(ArrayList<Integer> Deck) {
    	ArrayList<CardPanel> draw = new ArrayList<>();
    	if(Deck.size() <= 10) {
	        for (int i = 0; i < Deck.size(); ++i) {
	            CardPanel temp;
	            if(i == 0) {
	                temp = new CardPanel(Deck.get(i), false, false, true);
	            } else {
	        	    temp = new CardPanel(Deck.get(i), false, false, false);
	            }
	            temp.setLayout(null);
	            draw.add(temp);
	        }
    	}
    	else {
	        for (int i = 0; i < Deck.size()/3; ++i) {
	            CardPanel temp;
	            if(i == 0) {
	                temp = new CardPanel(Deck.get(i), false, false, true);
	            } else {
	        	    temp = new CardPanel(Deck.get(i), false, false, false);
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
    
    public void DrawClick(Consumer<Integer> listener) {
        this.onDrawClicked = listener;
    }

    public void HandDownClicked(Consumer<Integer> listener) {
        this.HandDownClicked = listener;
    }

    protected void updateBin(int bin) {
        BinCardPanel.update(bin, true);
        BinCardPanel.startAnimation();
        BinCardPanel.revalidate();
        BinCardPanel.repaint();
    }

    protected void updateDraw() {
        DrawPanel.update();
        DrawPanel.revalidate();
        DrawPanel.repaint();
    }
}
