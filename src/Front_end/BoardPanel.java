package Front_end;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.function.Consumer;

public class BoardPanel {
    private PlayerPanel frontPlayer = null; // Panel representing the player's part
    private final GameField gamefield;
    private final Overlay overlay;
    private final InfoPanel infoPanel;
    private JButton settingsButton;
    private Consumer<Integer> onDrawClicked;
    private Consumer<Integer> playCardClick;
    private Consumer<Integer> HandDownClicked;
    private Consumer<Integer> inPauseClicked;
    private Consumer<Integer> onNextRoundClicked;
    private Settings settings;

    /**
     * Constructor for the BoardPanel class.
     * Initializes the main game window and all related panels.
     *
     * @param hands           The list of hands for each player.
     * @param names           The list of player names.
     * @param bin             The value of the bin (discard pile).
     * @param Deck            The current deck of cards.
     * @param indexMainPlayer The index of the main player in the hands list.
     * @param listHandPoint   List of points for each player in the current round.
     * @param gamePoints      List of total points for each player in the game.
     */
    public BoardPanel(ArrayList<ArrayList<Integer>> hands, ArrayList<String> names, int bin, ArrayList<Integer> Deck, int indexMainPlayer, ArrayList<Integer> listHandPoint, ArrayList<Integer> gamePoints) {
        // Initialize main game window (board)
        JFrame frame = new JFrame();
        frame.setTitle("Lama Game - Board");
        frame.setSize(1000, 800); // Set board size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the window

        ImageIcon bgIcon = new ImageIcon("./src/Front_end/Back.png");
        Image bgImage = bgIcon.getImage();
        BackgroundPanel background = new BackgroundPanel(bgImage);
        background.setLayout(null);

        settings = new Settings(); // 1 seule fois
        settings.setBounds(50, 50, 300, 300); // Position/taille adaptées à ton layout
        settings.setVisible(false);
        background.add(settings);

        frame.setContentPane(background);

        frontPlayer = new PlayerPanel(hands.get(indexMainPlayer), names.get(indexMainPlayer), listHandPoint.get(indexMainPlayer));
        infoPanel = new InfoPanel(hands, names, indexMainPlayer, gamePoints);
        gamefield = new GameField(bin, Deck);
        setSettingButton();

        gamefield.drawClick((Integer value) -> {
            if (onDrawClicked != null) {
                onDrawClicked.accept(value);
            }
        });

        gamefield.handDownClicked((Integer value) -> {
            if (HandDownClicked != null) {
                HandDownClicked.accept(value);
            }
        });

        frontPlayer.playCardClick((indexCard) -> {
            System.out.println("Board Panel " + indexCard);
            if (playCardClick != null) {
                playCardClick.accept(indexCard);
            }
        });

        overlay = new Overlay(true, frame.getWidth(), frame.getHeight(), names, gamePoints);
        overlay.setVisible(false); // Initially hidden
        overlay.setLayout(null); // Needed for absolute positioning
        overlay.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        frame.add(overlay);

        overlay.setPauseClicked((value) -> {
            System.out.println("v " + value);

            switch (value) {
                case 1:
                    overlay.setVisible(false);
                    frontPlayer.setVisible(true);
                    gamefield.setVisible(true);
                    infoPanel.setVisible(true);
                    settings.setVisible(false);
                    break;

                case 2:/*
                    if (size != null) {
                        frame.setSize(size, size);
                    }*/
                    overlay.setVisible(false);
                    frontPlayer.setVisible(false);
                    gamefield.setVisible(false);
                    infoPanel.setVisible(false);
                    settings.setVisible(true);
                    settings.settingsValidated((w, h) -> {
                        System.out.println(w + " " + h);
                        frame.setSize(w, h);
                        overlay.setVisible(true);
                        settings.setVisible(false);
                        frame.revalidate();
                        frame.repaint();
                    });
                    break;

                case 3:
                    if (inPauseClicked != null) {
                        inPauseClicked.accept(value);
                    }
                    break;
            }
        });

        overlay.setNextRoundClicked((value) -> {
            System.out.println("v "+ value);
            if (onNextRoundClicked != null) {
                if (value == 1) {
                    overlay.setVisible(false);
                }
                else {
                    onNextRoundClicked.accept(value);
                }
            }
        });

        // Add panels to the frame
        frame.add(settingsButton);
        frame.add(infoPanel);
        frame.add(gamefield);
        frame.add(frontPlayer);

        // Resize listener to dynamically adjust sizes
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = frame.getWidth();
                int h = frame.getHeight();

                overlay.setBounds(0, 0, w, h);
                infoPanel.setBounds(0, 0, (int) (w * 0.9), (int) (h * 0.3));
                settingsButton.setBounds(w - 100, 0, 50, 50);
                gamefield.setBounds(0, (int) (h * 0.3), w, (int) (h * 0.3));
                frontPlayer.setBounds(0, h - frontPlayer.getHeight(), (int) (w * 0.9), (int) (h * 0.4));

                frame.repaint(); // If needed
            }
        });

        frame.setVisible(true);
    }

    /**
     * Sets up the settings button with an icon.
     */
    private void setSettingButton() {
        ImageIcon icon = new ImageIcon("./src/Front_end/Setting.png");
        Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);
        settingsButton = new JButton(icon);
        settingsButton.setToolTipText("Settings");
        settingsButton.setBorderPainted(false);
        settingsButton.setContentAreaFilled(false);
        settingsButton.setFocusPainted(false);
        settingsButton.setOpaque(false);

        settingsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Settings clicked");
                overlay.flipMode(false); // pause menu
                overlay.setVisible(true);
            }
        });
    }

    /**
     * Sets the callback for when a draw is requested.
     *
     * @param listener The listener to handle the draw request.
     */
    public void setOnDrawRequested(Consumer<Integer> listener) {
        this.onDrawClicked = listener;
    }

    /**
     * Sets the callback for when a card is played.
     *
     * @param listener The listener to handle the card play request.
     */
    public void setOnPlayCardRequested(Consumer<Integer> listener) {
        this.playCardClick = listener;
    }

    /**
     * Sets the callback for when the hand down action is requested.
     *
     * @param listener The listener to handle the hand down request.
     */
    public void setOnHandDownRequested(Consumer<Integer> listener) {
        this.HandDownClicked = listener;
    }

    /**
     * Sets the callback for when the pause action is clicked.
     *
     * @param listener The listener to handle the pause action.
     */
    public void setOnPauseClicked(Consumer<Integer> listener) {
        this.inPauseClicked = listener;
    }

    /**
     * Updates the board with new game information.
     *
     * @param hands           The updated hands of each player.
     * @param names           The updated names of players.
     * @param bin             The updated value of the bin.
     * @param Deck            The updated deck.
     * @param indexMainPlayer The index of the main player.
     * @param handPoint       The updated points for each hand.
     * @param gamePoints      The updated points for each player.
     */
    public void update(ArrayList<ArrayList<Integer>> hands, ArrayList<String> names, int bin, ArrayList<Integer> Deck, int indexMainPlayer, ArrayList<Integer> handPoint, ArrayList<Integer> gamePoints) {
        updateFrontPlayer(hands.get(indexMainPlayer), names.get(indexMainPlayer), handPoint.get(indexMainPlayer));
        infoPanel.update(hands, names, indexMainPlayer, gamePoints);
        gamefield.updateDrawPanel(Deck);
        gamefield.updateBin(bin);
    }

    /**
     * Updates the front player's information.
     *
     * @param hand  The new hand for the front player.
     * @param name  The name of the front player.
     * @param point The points of the front player.
     */
    public void updateFrontPlayer(ArrayList<Integer> hand, String name, int point) {
        frontPlayer.update(hand, name, point);
    }

    /**
     * Updates the information panel with new game data.
     *
     * @param hands           The updated hands of each player.
     * @param names           The updated names of players.
     * @param indexMainPlayer The index of the main player.
     * @param gamePoints      The updated points of the game.
     */
    public void updateInfoPanel(ArrayList<ArrayList<Integer>> hands, ArrayList<String> names, int indexMainPlayer, ArrayList<Integer> gamePoints) {
        infoPanel.update(hands, names, indexMainPlayer, gamePoints);
    }

    /**
     * Updates the hand of the front player.
     *
     * @param hand The new hand for the front player.
     */
    public void updateHand(ArrayList<Integer> hand) {
        frontPlayer.updateHand(hand);
    }

    /**
     * Updates the hand points of the front player.
     *
     * @param point The new points for the front player.
     */
    public void updateHandPoint(int point) {
        frontPlayer.updateHandPoint(point);
    }

    /**
     * Updates the draw in the gamefield.
     */
    public void updateDraw() {
        gamefield.updateDraw();
    }

    /**
     * Updates the bin (discard pile) in the gamefield.
     *
     * @param bin The updated value of the bin.
     */
    public void updateBin(int bin) {
        gamefield.updateBin(bin);
    }

    /**
     * Shows the overlay (e.g., pause or game round details).
     */
    public void showOverlay() {
        overlay.showOverlay(this);
    }

    /**
     * Updates the overlay with new game data.
     *
     * @param names The updated names of players.
     * @param score The updated scores of players.
     */
    public void overlayUpdate(ArrayList<String> names, ArrayList<Integer> score) {
        overlay.update(names, score);
    }

    /**
     * Clears the visibility of all components on the board.
     */
    public void clear() {
        frontPlayer.setVisible(false);
        gamefield.setVisible(false);
        infoPanel.setVisible(false);
        settingsButton.setVisible(false);
    }
}