package Front_end;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import Back_end.*;


public class Front_Player extends JPanel {
    private HandPanel handPanel; // Le panneau contenant les cartes du joueur
    private TextPanel textPanel; // Le panneau de texte (par exemple pour afficher les tours restants)

    // Constructeur
    public Front_Player(Player player) {
        setLayout(null); // Pas de layout manager pour un contrôle total
        setBackground(Color.BLUE); // Optionnel, pour visualiser le panneau

        // Créer le HandPanel
        createHandPanel(player);

        // Créer un ou plusieurs TextPanel (par exemple un pour afficher le nombre de tours restants)
        createTextPanel();

        // Ajouter des composants
        add(handPanel, BorderLayout.SOUTH);
        add(textPanel);

        // Ajuster la taille du HandPanel pour qu'il occupe un certain pourcentage de Front_Player
        adjustHandPanelSize();
    }

    private void createHandPanel(Player p) {
        // Créer la liste des chemins d'images des cartes du joueur
        ArrayList<Card> hand = p.getHand();

        // Créer une liste de chemins d'images pour les cartes
        ArrayList<CardPanel> Card = new ArrayList<CardPanel>();
        for (int i = 0; i < hand.size(); ++i) {
            CardPanel temp = new CardPanel(1, 1, hand.get(i).getImagePath());
            Card.add(temp);
        }

        // Créer le panneau principal de la main de cartes
        handPanel = new HandPanel(Card);
    }

    private void createTextPanel() {
        // Exemple de TextPanel pour afficher le nombre de tours restants
        textPanel = new TextPanel("Tours restants: 5");
        textPanel.setBounds(50, 50, 300, 50); // Positionner le TextPanel
        textPanel.setFont(new Font("Arial", Font.PLAIN, 30));
    }

    // Méthode pour ajuster la taille du HandPanel pour qu'il occupe un pourcentage de Front_Player
    private void adjustHandPanelSize() {
        float coefHeightHand = 0.6f; // hand of player use x % of the Panel front player

        // Ajuster la taille et la position du HandPanel
        handPanel.setBounds((int) Math.ceil(getWidth()*0.25),  (int) Math.ceil(getHeight() - getHeight() * coefHeightHand)-30, (int) Math.ceil(getWidth()*0.7), (int) Math.ceil(getHeight()*coefHeightHand));
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
