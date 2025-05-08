package Front_end;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;
import java.awt.event.MouseAdapter;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

public class BoardPanel {
    private PlayerPanel frontPlayer; // Le panneau représentant la partie du joueur
    private GameField gamefield;
    private Overlay overlay;
    private InfoPanel infoPanel;
    private JButton settingsButton;
    private Consumer<Integer> onDrawClicked;
    private Consumer<Integer> playCardClick;
    private Consumer<Integer> HandDownClicked;
    private Consumer<Integer> inPauseClicked;

    public BoardPanel(ArrayList<ArrayList<Integer>> hands, ArrayList<String> names, int bin, ArrayList<Integer> Deck, int indexMainPlayer, ArrayList<Integer> listHandPoint, ArrayList<Integer> gamePoints) {
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
        overlay = new Overlay(true, frame.getWidth(), frame.getHeight());
        overlay.setVisible(false); // initialement caché
        overlay.setLayout(null); // nécessaire pour positionnement absolu
        overlay.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        frame.add(overlay);

        overlay.setPauseClicked((value) -> {
            ;
            if (inPauseClicked != null) {
                inPauseClicked.accept(value);
            }
        });

        frontPlayer = new PlayerPanel(hands.get(indexMainPlayer), names.get(indexMainPlayer), listHandPoint.get(indexMainPlayer));
        infoPanel = new InfoPanel(hands, names, indexMainPlayer, gamePoints);
        gamefield = new GameField(bin, Deck);
        setSettingButton();

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
        frame.add(settingsButton);
        frame.add(infoPanel);
        frame.add(gamefield);
        frame.add(frontPlayer);

        // Écouteur de redimensionnement pour adapter dynamiquement les tailles
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = frame.getWidth();
                int h = frame.getHeight();

                infoPanel.setBounds(0, 0, (int) (w * 0.9), (int) (h * 0.3));
                settingsButton.setBounds((int) (w * 0.95),0, (int) (w * 0.05), (int) (h * 0.3));
                gamefield.setBounds(0, (int) (h * 0.3), w, (int) (h * 0.3));
                frontPlayer.setBounds(0, h - frontPlayer.getHeight(), (int) (w * 0.9), (int) (h * 0.4));

                frame.repaint(); // si besoin
            }
        });

        frame.setVisible(true);
    }

    private void setSettingButton(){
        ImageIcon icon = new ImageIcon("./src/Front_end/Setting.png");
        Image img = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);
        settingsButton = new JButton(icon);
        settingsButton.setToolTipText("Paramètres");
        settingsButton.setBorderPainted(false);
        settingsButton.setContentAreaFilled(false);
        settingsButton.setFocusPainted(false);
        settingsButton.setOpaque(false);

        settingsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                overlay.setVisible(false);
            }
        });
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

    public void setOnPauseClicked(Consumer<Integer> listener) {
        this.inPauseClicked = listener;
    }

    public void update(ArrayList<ArrayList<Integer>> hands, ArrayList<String> names, int bin, ArrayList<Integer> Deck, int indexMainPlayer, ArrayList<Integer> handPoint, ArrayList<Integer> gamePoints) {
        updateFrontPlayer(hands.get(indexMainPlayer), names.get(indexMainPlayer), handPoint.get(indexMainPlayer));
        infoPanel.update(hands, names, indexMainPlayer, gamePoints);
        gamefield.updateDraw();
        gamefield.updateBin(bin);
    }

    public void updateFrontPlayer(ArrayList<Integer> hand, String name, int point) {
        frontPlayer.update(hand, name, point);
    }

    public void updateInfoPanel(ArrayList<ArrayList<Integer>> hands, ArrayList<String> names, int indexMainPlayer, ArrayList<Integer> gamePoints) {
        infoPanel.update(hands, names, indexMainPlayer, gamePoints);
    }

    public void updateHand(ArrayList<Integer> hand) {
        frontPlayer.updateHand(hand);
    }

    public void updateHandPoint(int point) {
        frontPlayer.updateHandPoint(point);
    }

    public void updateDraw() {
        gamefield.updateDraw();
    }

    public void updateBin(int bin) {
        gamefield.updateBin(bin);
    }

    public void showOverlay() {
        overlay.showOverlay(this);
    }

    public void hideOverlay() {
        overlay.hideOverlay();
    }

    protected void setGlassPane(Overlay overlay) {
    }

}

