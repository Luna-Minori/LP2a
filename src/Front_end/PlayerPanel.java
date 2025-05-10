package Front_end;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;


public class PlayerPanel extends JPanel {
    private HandPanel handPanel; // Le panneau contenant les cartes du joueur
    private TextPanel namePanel;
    private TextPanel pointTextPanel;
    private ArrayList<Integer> hand;
    private String name;
    private Consumer<Integer> CardClick;
    private int point;

    // Constructeur
    public PlayerPanel(ArrayList<Integer> hand, String name, int point) {
        setLayout(null);
        setOpaque(false);
        // create Panel for player
        this.hand = hand;
        this.name = name;
        this.point = point;
        createHandPanel();
        TextPanel();
        add(handPanel);
        add(namePanel);
        add(pointTextPanel);
        adjustTextPanelSize();
        adjustHandPanelSize(); // try to be responsive

    }

    private void adjustTextPanelSize() {
        namePanel.setBounds((int) (getWidth() * 0.05), (int) (getHeight() * 0.7), namePanel.getText().length() * 30, (int) (getHeight() * 0.2));
        pointTextPanel.setBounds((int) (getWidth() * 0.05), (int) (getHeight() * 0.5), pointTextPanel.getText().length() * 30, (int) (getHeight() * 0.2));
    }

    private void createHandPanel() {
        // Créer une liste de chemins d'images pour les cartes
        ArrayList<CardPanel> Card = new ArrayList<CardPanel>();
        for (int i = 0; i < hand.size(); ++i) {
            CardPanel temp = new CardPanel(hand.get(i), true, true, true);
            int indexCard = i;
            temp.setOnCardClicked(value -> {
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
        pointTextPanel = new TextPanel("Hand Points : " + point);
        pointTextPanel.setBounds(10, 270, 220, 50);
        pointTextPanel.setFont(new Font("Arial", Font.PLAIN, 20));
    }

    // Méthode pour ajuster la taille du HandPanel pour qu'il occupe un pourcentage de Front_Player
    private void adjustHandPanelSize() {
        float coefHeightHand = 0.8f; // hand of player use x % of the Panel front player

        // Ajuster la taille et la position du HandPanel
        handPanel.setBounds((int) Math.ceil(namePanel.getWidth() * 1.05), (int) Math.ceil(getHeight() - getHeight() * coefHeightHand - getHeight() * 0.15), (int) Math.ceil((getWidth() - namePanel.getWidth() * 1.05) * 0.95), (int) Math.ceil(getHeight() * coefHeightHand));
    }

    // Méthode pour redimensionner le Front_Player et ajuster le HandPanel
    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        adjustTextPanelSize();
        adjustHandPanelSize();
    }

    protected void update(ArrayList<Integer> hand, String name, int point) {
        this.name = name;
        this.hand = hand;
        this.point = point;
        updateTextPanel(point);
        updateHandPanel();
        adjustTextPanelSize();
        adjustHandPanelSize();
    }


    protected void updateHand(ArrayList<Integer> hand) {
        this.hand = hand;
        updateHandPanel();
        adjustHandPanelSize();
    }

    private void updateHandPanel() {
        ArrayList<CardPanel> Card = new ArrayList<CardPanel>();
        for (int i = 0; i < hand.size(); ++i) {
            CardPanel temp = new CardPanel(hand.get(i), true, true, true);
            temp.setLayout(null);
            Card.add(temp);
            int indexCard = i;
            temp.setOnCardClicked(value -> {
                if (CardClick != null) {
                    CardClick.accept(indexCard);
                }
            });
        }
        handPanel.clear();
        handPanel.update(Card, true);
    }

    private void updateTextPanel(int point) {
        // Exemple de TextPanel pour afficher le nombre de tours restants
        namePanel.setText(name);
        pointTextPanel.setText("Hand points : " + point);
        repaint();
    }

    public void playCardClick(Consumer<Integer> listener) {
        this.CardClick = listener;
    }

    public void updateHandPoint(int point) {
        updateTextPanel(point);
    }
}
