package Front_end;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;


public class PlayerPanel extends JPanel {
    private HandPanel handPanel; // Le panneau contenant les cartes du joueur
    private TextPanel namePanel;
    private TextPanel pointTextPanel;
    private TextPanel pointPanel;// Le panneau de texte (par exemple pour afficher les tours restants)
    private ArrayList<Integer> hand;
    private String name;
    private Consumer<Integer> CardClick;
    private int Point;
    // Constructeur
    public PlayerPanel(ArrayList<Integer> hand, String name) {
        setLayout(null);
        setOpaque(false);
        // create Panel for player
        this.hand = hand;
        this.name = name;
        createHandPanel();
        TextPanel();
        add(handPanel);
        adjustHandPanelSize(); // try to be responsive

    }

    private void createHandPanel() {
        // Créer une liste de chemins d'images pour les cartes
        ArrayList<CardPanel> Card = new ArrayList<CardPanel>();
        for (int i = 0; i < hand.size(); ++i) {
            CardPanel temp = new CardPanel(0.8f, 0.8f, hand.get(i), true, true, true);
            int indexCard = i;
            temp.setOnCardClicked(value ->{
                if (CardClick != null) {
                    System.out.println("Player pannel " + value);
                    CardClick.accept(indexCard);
                    //CardClick = null;
                }
            });
            temp.setLayout(null);
            Card.add(temp);
        }

        // Créer le panneau principal de la main de cartes
        handPanel = new HandPanel(Card, true);
    }

    private void TextPanel() {
        // Exemple de TextPanel pour afficher le nombre de tours restants
    	namePanel = new TextPanel(name);
    	namePanel.setBounds(100, 50, 300, 50); // Positionner le TextPanel
    	namePanel.setFont(new Font("Arial", Font.PLAIN, 34));
        System.out.println("w "+ getWidth());
        pointTextPanel = new TextPanel("Hand Points :");
        pointTextPanel.setBounds(10, 270, 220, 50);
        pointTextPanel.setFont(new Font("Arial",Font.PLAIN, 24));
        //Point.setColor(Color.black);
        pointPanel = new TextPanel("" + Point );
        pointPanel.setBounds(85, 310, 50, 50);
        pointPanel.setFont(new Font("Arial",Font.PLAIN, 24));
        add(namePanel);
        add(pointTextPanel);
        add(pointPanel);
    }
    
    // Méthode pour ajuster la taille du HandPanel pour qu'il occupe un pourcentage de Front_Player
    private void adjustHandPanelSize() {
        float coefHeightHand = 0.6f; // hand of player use x % of the Panel front player

        // Ajuster la taille et la position du HandPanel
        handPanel.setBounds((int) Math.ceil(getWidth()*0.2),  (int) Math.ceil(getHeight() - getHeight() * coefHeightHand - 30), (int) Math.ceil(getWidth()*0.8), (int) Math.ceil(getHeight()*coefHeightHand));
    }

    // Méthode pour redimensionner le Front_Player et ajuster le HandPanel
    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        // Réajuster la taille du HandPanel chaque fois que le parent est redimensionné
        adjustHandPanelSize();
    }

    protected void update(ArrayList<Integer> hand, String name){
    	this.name = name;
    	this.hand = hand;
    	updateHandPanel();
    	updateTextPanel();
    	adjustHandPanelSize(); 
    }


    protected void updateHand(ArrayList<Integer> hand){
        this.hand = hand;
        updateHandPanel();
        adjustHandPanelSize();
    }

    private void updateHandPanel() {
        ArrayList<CardPanel> Card = new ArrayList<CardPanel>();
        for (int i = 0; i < hand.size(); ++i) {
            CardPanel temp = new CardPanel(0.8f, 0.8f, hand.get(i), true, true, true);
            temp.setLayout(null);
            Card.add(temp);
            int indexCard = i;
            temp.setOnCardClicked(value ->{
                if (CardClick != null) {
                    System.out.println("Player pannel " + value);
                    CardClick.accept(indexCard);
                    //CardClick = null;
                }
            });
        }
        handPanel.clear();
        handPanel.update(Card, true);
    }
    
    private void updateTextPanel() {
        // Exemple de TextPanel pour afficher le nombre de tours restants
        namePanel.setText(name);
        pointTextPanel.setText(name);
        pointPanel.setText(name);
        repaint();
    }

    public void playCardClick(Consumer<Integer> listener) {
        this.CardClick = listener;
    }
    
}
