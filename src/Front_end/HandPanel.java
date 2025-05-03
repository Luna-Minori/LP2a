package Front_end;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class HandPanel extends JPanel {
    private ArrayList<CardPanel> cardPanels; // Liste des panneaux de cartes
    private int spacing; // Espacement entre les cartes

    public HandPanel(ArrayList<CardPanel> hand, int spacing) {
        this.cardPanels = new ArrayList<>();
        this.spacing = spacing;
        setLayout(null); // Pour contrôler le placement des cartes
        setOpaque(false); // Transparence du panel

        // Lancer l'ajout des cartes après un délai
        int[] i = {0};
        new Timer(300, e -> {
            if (i[0] < hand.size()) {
                // Créer une nouvelle carte à partir de l'objet CardPanel
                CardPanel carte = hand.get(i[0]);
                // Attendre que la taille du panel soit calculée
                carte.setBounds(calculateCardPosition(i[0])); // Calculer la position pour l'éventail
                this.cardPanels.add(carte); // Ajouter la carte à la liste
                this.add(carte); // Ajouter la carte au panel
                i[0]++;
                this.repaint();
            } else {
                ((Timer) e.getSource()).stop();
            }
        }).start();
    }

    // Calculer la position des cartes pour un affichage en éventail
    private Rectangle calculateCardPosition(int index) {
        return new Rectangle(spacing * index, 25, 1, 200); // Positionner verticalement et définir taille
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (CardPanel panel : cardPanels) {
            panel.repaint(); // Assurez-vous que chaque carte se redessine correctement
        }
    }

    // Méthode pour ajuster la taille du HandPanel (si nécessaire)
    public void adjustSize(int width, int height) {
        setBounds(0, 0, width, height); // Ajuster la taille du HandPanel
        revalidate(); // Recalculer la disposition du panneau
        repaint(); // Redessiner le panneau
    }
}
