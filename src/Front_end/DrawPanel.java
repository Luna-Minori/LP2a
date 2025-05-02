package Front_end;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DrawPanel extends JPanel {
    private ArrayList<CardPanel> cardPanels; // Liste des panneaux de cartes
    private int spacingY = 2; // Espacement entre les cartes
    private int spacingX = -1;
    public DrawPanel(ArrayList<CardPanel> deck) {
        this.cardPanels = new ArrayList<>();
        //etLayout(null);
        setOpaque(true);
        setBackground(Color.RED);

        int[] i = {0};
        Timer timer = new Timer(50, e -> {
            if (i[0] < deck.size()) {
                CardPanel carte = deck.get(i[0]);
                Rectangle bounds = calculateCardPosition(i[0]);
                carte.setBounds(bounds);
                this.cardPanels.add(carte);
                this.add(carte);
                i[0]++;
                this.repaint();
            } else {
                ((Timer) e.getSource()).stop();
            }
        });
        timer.start(); 

    }
    // Calcule une position en éventail ou colonne
    private Rectangle calculateCardPosition(int index) {
        int cardWidth = (int) (getWidth() * 0.8);
        int cardHeight = (int) (getHeight() *0.8);
        return new Rectangle(spacingX * index, spacingY * index, cardWidth, cardHeight);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (CardPanel panel : cardPanels) {
            panel.repaint(); // Assurez-vous que chaque carte se redessine correctement
        }
    }

}