package Front_end;

import javax.swing.*;
import java.util.function.Consumer;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

public class BoardPanel {
    private PlayerPanel frontPlayer; // Le panneau représentant la partie du joueur
    private GameField gamefield;
    private Consumer<Integer> onDrawClicked;
    private Consumer<Integer> playCardClick;
    private Consumer<Integer> HandDownClicked;

    public BoardPanel(ArrayList<ArrayList<Integer>> hands, ArrayList<String> names, int bin, ArrayList<Integer> Deck, int indexMainPlayer) {
        // Définir les propriétés de la fenêtre principale (plateau)
        JFrame frame = new JFrame();
        frame.setTitle("Lama Game - Plateau");
        frame.setSize(1000, 800); // Taille du plateau
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Centrer la fenêtre

        ImageIcon bgIcon = new ImageIcon("./src/Front_end/Back.png");
        Image bgImage = bgIcon.getImage();
        BackgroundPanel background = new BackgroundPanel(bgImage);
        background.setLayout(null);

        frame.setContentPane(background);


        frontPlayer = new PlayerPanel(hands.get(indexMainPlayer), names.get(indexMainPlayer));
        InfoPanel info = new InfoPanel(hands, names, indexMainPlayer);
        gamefield = new GameField(bin, Deck);

        gamefield.DrawClick((value) -> {
            if (onDrawClicked != null) {
                onDrawClicked.accept(value);
                //onDrawClicked = null;
            }
        });

        frontPlayer.playCardClick((indexCard) -> {
            System.out.println("Board Panel " + indexCard);
            if (playCardClick != null) {
                playCardClick.accept(indexCard);
                //playCardClick = null;
            }
        });

        gamefield.HandDownClicked(value -> {
            if (HandDownClicked != null) {
                HandDownClicked.accept(value);
                //HandDownClicked = null;
            }
        });


        // Ajout des panels
        frame.add(info);
        frame.add(gamefield);
        frame.add(frontPlayer);

        // Écouteur de redimensionnement pour adapter dynamiquement les tailles
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = frame.getWidth();
                int h = frame.getHeight();

                info.setBounds(0, 0, w, (int) (h * 0.3));
                gamefield.setBounds(0, (int) (h * 0.3), w, (int) (h * 0.3));
                frontPlayer.setBounds(0, h - frontPlayer.getHeight(), w, (int) (h * 0.4));

                frame.repaint(); // si besoin
            }
        });

        frame.setVisible(true);
    }

    public void setOnDrawRequested(Consumer<Integer> listener) {
        this.onDrawClicked = listener;
    }

    public void setOnPlayCardRequested(Consumer<Integer> listener) {
        this.playCardClick = listener;
    }

    public void setOnHandDownRequested(Consumer<Integer> listener) {
        this.HandDownClicked = listener;
    }

    public void update(ArrayList<ArrayList<Integer>> hands, ArrayList<String> names, int bin, ArrayList<Integer> Deck, int indexMainPlayer) {
        updateFrontPlayer(hands.get(indexMainPlayer), names.get(indexMainPlayer));
        InfoPanel info = new InfoPanel(hands, names, indexMainPlayer);
        gamefield.updateDraw();
        gamefield.updateBin(bin);
    }

    private void updateFrontPlayer(ArrayList<Integer> hand, String name) {
        frontPlayer.update(hand, name);

    }

    public void updateHand(ArrayList<Integer> hand) {
        frontPlayer.updateHand(hand);
    }

    public void updateDraw() {
        gamefield.updateDraw();
    }

    public void updateBin(int bin) {
        gamefield.updateBin(bin);
    }

}

