package Front_end;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Game {

    public Game() {
        JFrame frame = new JFrame("Lama Game");
        frame.setSize(1000, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        ImageIcon bgIcon = new ImageIcon("C:/Users/Luna/eclipse-workspace/Lp2a_Project/src/Front_end/Back.png");
        Image bgImage = bgIcon.getImage();
        BackgroundPanel background = new BackgroundPanel(bgImage);
        background.setLayout(null); // permet de positionner librement

        frame.setContentPane(background);

        // Création du panel "carte" (ex. 30% largeur, 50% hauteur)
        CardPanel carte = new CardPanel(0.3f, 0.5f, "C:/Users/Luna/eclipse-workspace/Lp2a_Project/src/Front_end/1.png");
        background.add(carte);

        // Comportement responsive quand on redimensionne
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                carte.resizeWithin(background);
                background.repaint();
            }
        });

        // Ajout du texte
        String nb_tour = "5";
        JTextArea textArea = new JTextArea(nb_tour);
        textArea.setFont(new Font("Arial", Font.PLAIN, 100)); // Définir la police et la taille
        textArea.setLineWrap(true); // Activer le retour à la ligne automatique
        textArea.setWrapStyleWord(true); // Activer le retour à la ligne par mot
        textArea.setEditable(false); // Rendre le texte non modifiable
        textArea.setOpaque(false); // Rendre le fond transparent

        // Positionnement du texte
        textArea.setBounds(50, FlowLayout.CENTER, 100, 100); // Définir la position et la taille
        carte.setBounds(50,100, 100, 100);
        background.add(textArea);
        frame.add(carte);

        // Positionnement initial de la carte
        carte.resizeWithin(background);

        frame.setVisible(true);
    }
}
