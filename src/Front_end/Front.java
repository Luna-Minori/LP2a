package Front_end;

import java.awt.*;
import javax.swing.*;

public class Front {
    public static void main(String[] args) {
        JFrame frame = new JFrame("lama");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Charger l'image
        ImageIcon image = new ImageIcon("C:/Users/Luna/eclipse-workspace/Lp2a_Project/src/Front_end/Back.png");
        if (image.getIconWidth() == -1) {
            System.out.println("Image introuvable !");
        }

        // Créer le label d'image et le mettre en arrière-plan
        JLabel background = new JLabel(image);
        background.setLayout(new FlowLayout()); // Pour ajouter d'autres composants
        frame.setContentPane(background);

        // Ajouter un champ texte et des boutons par-dessus l'image


        // Créer et ajouter les panels
        JPanel[] panels = new JPanel[4];
        for (int i = 0; i < 4; i++) {
            panels[i] = new JPanel();
            panels[i].setLayout(new GridLayout(4, 3));
            panels[i].add(new JLabel("Hello"));
            panels[i].add(new JLabel(" "));
            panels[i].add(new JLabel(" "));
            panels[i].add(new JLabel(" "));

            panels[i].setPreferredSize(new Dimension(300, 300));
            panels[i].setOpaque(false); // transparent pour voir l'image derrière
            background.add(panels[i]);
        }
        background.add(new JLabel("Enter the number of players: "));
        background.add(new JTextField(10));
        background.add(new JButton("Validate"));
        background.add(new JButton("Exit"));

        frame.setVisible(true);
    }
}
