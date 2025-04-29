package Front_end;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import Back_end.*;


public class PlayerPanel extends JPanel {
    private HandPanel handPanel; // Le panneau contenant les cartes du joueur
    private TextPanel textPanel; // Le panneau de texte (par exemple pour afficher les tours restants)

    // Constructeur
    public PlayerPanel(Player player) {
        setLayout(null);
        setOpaque(false);
        // create Panel for player
        createHandPanel(player);
        TextPanel(player);
        add(handPanel, BorderLayout.SOUTH);
        adjustHandPanelSize(); // try to be responsive
    }

    private void createHandPanel(Player p) {
        // Créer la liste des chemins d'images des cartes du joueur
        ArrayList<Card> hand = p.getHand();

        // Créer une liste de chemins d'images pour les cartes
        ArrayList<CardPanel> Card = new ArrayList<CardPanel>();
        for (int i = 0; i < hand.size(); ++i) {
            CardPanel temp = new CardPanel(0.8f, 0.8f, hand.get(i).getImagePath());
            temp.setLayout(null);
            Card.add(temp);
        }

        // Créer le panneau principal de la main de cartes
        handPanel = new HandPanel(Card);
    }

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

    // Méthode pour ajuster la taille du Front_Player (50% de la hauteur de la fenêtre)
    public void adjustSize(int parentHeight, int parentWidth) {
        // Ajuster la taille du Front_Player pour qu'il occupe 50% de la hauteur de la fenêtre parent
        setBounds(0, parentHeight / 2, parentWidth, parentHeight / 2); // Occupation de 50% de la hauteur
        repaint();
    }
}
