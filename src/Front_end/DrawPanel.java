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
        setLayout(null);
        setOpaque(false);


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
                
                addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        updateCardPositions();
                    }
                });

            } else {
                ((Timer) e.getSource()).stop();
            }
        });
        timer.start(); 

    }
    // Calcule une position en éventail ou colonne
    private Rectangle calculateCardPosition(int index) {
        int cardWidth = (int) (getWidth());
        int cardHeight = (int) (getHeight());
        return new Rectangle(spacingX * index, spacingY * index, cardWidth, cardHeight);
    }
        
    private void updateCardPositions() {
        for (int i = 0; i < cardPanels.size(); i++) {
            CardPanel card = cardPanels.get(i);
            Rectangle bounds = calculateCardPosition(i);
            card.setBounds(bounds);
        }
        revalidate();
        repaint();
    }

}