package Front_end;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class InfoPlayer extends JPanel {
	private TextPanel Name;
	private HandPanel handPanel;
	
	public InfoPlayer(ArrayList<Integer> hand, int totalPlayers, String name) {
        setLayout(null);
        setOpaque(false);

        // create Panel for player
        createHandPanel(hand, totalPlayers);
        createTextPanel(name);
        add(handPanel);
        add(Name);
        adjustHandPanelSize(); // try to be responsive
        adjustTextPanelSize();
        // ✅ Ajouter un listener pour être responsive
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustHandPanelSize();
                adjustTextPanelSize();
            }
        });
    }

    private void createHandPanel(ArrayList<Integer> hand, int totalPlayers) {
        ArrayList<CardPanel> Card = new ArrayList<CardPanel>();
        for (int i = 0; i < hand.size(); ++i) {
            CardPanel temp = new CardPanel(1f, 1f, hand.get(i), totalPlayers, false, true, false);
            temp.setLayout(null);
            Card.add(temp);
        }
        // Créer le panneau principal de la main de cartes
        handPanel = new HandPanel(Card, false);
    }
    
    private void createTextPanel(String name) {
        // Exemple de TextPanel pour afficher le nombre de tours restants
        Name = new TextPanel(name);
        Name.setFont(new Font("Arial", Font.PLAIN, 24));
    }
    
    // Méthode pour ajuster la taille du HandPanel pour qu'il occupe un pourcentage de Front_Play	er
    private void adjustHandPanelSize() {
        float coefHeightHand = 0.9f; // hand of player use x % of the Panel front player

        // Ajuster la taille et la position du HandPanel
        handPanel.setBounds((int) Math.ceil(Name.getWidth()*1.05), 0, (int) Math.ceil(getWidth()*0.75), (int) Math.ceil(getHeight()*coefHeightHand));
    }
    
    private void adjustTextPanelSize() {
        Name.setBounds(0, 0, (int) (Name.getText().length()*20) ,(int) (getHeight()*0.5));
    }
    
    // Méthode pour redimensionner le Front_Player et ajuster le HandPanel
    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        // Réajuster la taille du HandPanel chaque fois que le parent est redimensionné
        adjustHandPanelSize();
        adjustTextPanelSize();
    }
}
