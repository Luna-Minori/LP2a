package Front_end;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class HandPanel extends JPanel {
    private ArrayList<CardPanel> cardPanels; // Liste des panneaux de cartes
    private int spacing = 100; // Espacement entre les cartes

    public HandPanel(ArrayList<CardPanel> hand) {
        this.cardPanels = new ArrayList<>();
        setLayout(null); // Pour contrôler le placement des cartes
        setOpaque(true); // Transparence du panel
        setBackground(Color.GREEN); // Juste pour voir le panel

        // Lancer l'ajout des cartes après un délai
        int[] i = {0};
        new Timer(300, e -> {
            if (i[0] < hand.size()) {
                // Créer une nouvelle carte à partir de l'objet CardPanel
                CardPanel carte = hand.get(i[0]);
                // Attendre que la taille du panel soit calculée
                int coefaff = (int) Math.ceil(getWidth() * 0.1);
                System.out.println("w : " + getWidth());
                System.out.println("coef " + coefaff);
                carte.setBounds(calculateCardPosition(i[0], coefaff)); // Calculer la position pour l'éventail
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
    private Rectangle calculateCardPosition(int index, int coefAff) {
        return new Rectangle(coefAff + spacing * index, 0, 80, 160); // Positionner verticalement et définir taille
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
