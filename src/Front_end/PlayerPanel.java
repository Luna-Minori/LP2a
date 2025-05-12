package Front_end;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * PlayerPanel represents a player's area in the game UI.
 * It contains their hand of cards and information such as name and points.
 */
public class PlayerPanel extends JPanel {

    private HandPanel handPanel; // Panel containing player's cards
    private TextPanel namePanel; // Panel displaying player's name
    private TextPanel pointTextPanel; // Panel displaying player's score
    private ArrayList<Integer> hand; // List of card IDs
    private String name; // Player's name
    private Consumer<Integer> cardClick; // Listener for card click events
    private int point; // Player's score

    /**
     * Constructor for the PlayerPanel.
     *
     * @param hand  the player's initial hand
     * @param name  the player's name
     * @param point the player's score
     */
    public PlayerPanel(ArrayList<Integer> hand, String name, int point) {
        setLayout(null);
        setOpaque(false);
        this.hand = hand;
        this.name = name;
        this.point = point;
        createHandPanel();
        createTextPanel();
        add(handPanel);
        add(namePanel);
        add(pointTextPanel);
        adjustTextPanelSize();
        adjustHandPanelSize();
    }

    /**
     * Adjusts the size and position of the name and point text panels.
     */
    private void adjustTextPanelSize() {
        namePanel.setBounds((int) (getWidth() * 0.05), (int) (getHeight() * 0.7),
                namePanel.getText().length() * 30, (int) (getHeight() * 0.2));
        pointTextPanel.setBounds((int) (getWidth() * 0.05), (int) (getHeight() * 0.5),
                pointTextPanel.getText().length() * 30, (int) (getHeight() * 0.2));
    }

    /**
     * Creates the hand panel and initializes it with cards.
     */
    private void createHandPanel() {
        ArrayList<CardPanel> cards = new ArrayList<>();
        for (int i = 0; i < hand.size(); ++i) {
            CardPanel temp = new CardPanel(hand.get(i), true, true, true);
            int indexCard = i;
            temp.setOnCardClicked(value -> {
                if (cardClick != null) {
                    System.out.println("Player panel card clicked: " + value);
                    cardClick.accept(indexCard);
                }
            });
            temp.setLayout(null);
            cards.add(temp);
        }
        handPanel = new HandPanel(cards, true);
    }

    /**
     * Creates the name and point text panels.
     */
    private void createTextPanel() {
        namePanel = new TextPanel(name);
        namePanel.setBounds(100, 50, 300, 50);
        namePanel.setFont(new Font("Arial", Font.PLAIN, 34));

        pointTextPanel = new TextPanel("Hand Points : " + point);
        pointTextPanel.setBounds(10, 270, 220, 50);
        pointTextPanel.setFont(new Font("Arial", Font.PLAIN, 20));
    }

    /**
     * Adjusts the size and position of the hand panel to fit the player's area.
     */
    private void adjustHandPanelSize() {
        float coefHeightHand = 0.8f;
        handPanel.setBounds(
                (int) Math.ceil(namePanel.getWidth() * 1.05),
                (int) Math.ceil(getHeight() - getHeight() * coefHeightHand - getHeight() * 0.15),
                (int) Math.ceil((getWidth() - namePanel.getWidth() * 1.05) * 0.95),
                (int) Math.ceil(getHeight() * coefHeightHand)
        );
    }

    /**
     * Overrides setBounds to allow dynamic resizing of internal components.
     *
     * @param x      x-position
     * @param y      y-position
     * @param width  panel width
     * @param height panel height
     */
    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        adjustTextPanelSize();
        adjustHandPanelSize();
    }

    /**
     * Updates all elements of the PlayerPanel.
     *
     * @param hand  new hand of cards
     * @param name  new player name
     * @param point new score
     */
    protected void update(ArrayList<Integer> hand, String name, int point) {
        this.name = name;
        this.hand = hand;
        this.point = point;
        updateTextPanel(point);
        updateHandPanel();
        adjustTextPanelSize();
        adjustHandPanelSize();
    }

    /**
     * Updates only the player's hand.
     *
     * @param hand new hand of cards
     */
    protected void updateHand(ArrayList<Integer> hand) {
        this.hand = hand;
        updateHandPanel();
        adjustHandPanelSize();
    }

    /**
     * Regenerates the hand panel with updated card data.
     */
    private void updateHandPanel() {
        ArrayList<CardPanel> cards = new ArrayList<>();
        for (int i = 0; i < hand.size(); ++i) {
            CardPanel temp = new CardPanel(hand.get(i), true, true, true);
            temp.setLayout(null);
            cards.add(temp);
            int indexCard = i;
            temp.setOnCardClicked(value -> {
                if (cardClick != null) {
                    cardClick.accept(indexCard);
                }
            });
        }
        handPanel.clear();
        handPanel.update(cards, true);
    }

    /**
     * Updates the name and point text panels.
     *
     * @param point the new point value to display
     */
    private void updateTextPanel(int point) {
        namePanel.setText(name);
        pointTextPanel.setText("Hand points : " + point);
        repaint();
    }

    /**
     * Registers a click listener for when a card is clicked.
     *
     * @param listener callback to receive the index of the clicked card
     */
    public void playCardClick(Consumer<Integer> listener) {
        this.cardClick = listener;
    }

    /**
     * Updates the displayed point value without changing the rest of the panel.
     *
     * @param point new score to display
     */
    public void updateHandPoint(int point) {
        updateTextPanel(point);
    }
}
