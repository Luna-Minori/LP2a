package Front_end;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.function.Consumer;

public class GameField extends JPanel {
    private DrawPanel drawPanel; // Panel containing the player's cards
    private CardPanel binCardPanel; // Panel for the card bin (e.g., to show the remaining rounds)
    private Consumer<Integer> onDrawClicked; // Consumer for handling card draw clicks
    private Consumer<Integer> handDownClicked; // Consumer for handling "hand down" clicks (if applicable)

    /**
     * Constructor for the GameField class.
     * Initializes the panels and handles component resize events.
     * @param bin Value representing the bin card
     * @param deck List of card values to be displayed in the draw panel
     */
    public GameField(int bin, ArrayList<Integer> deck) {
        setLayout(null);
        setOpaque(false);

        createBinCardPanel(bin); // Initialize the bin card panel
        createDrawPanel(deck); // Initialize the draw panel
        add(binCardPanel); // Add the bin card panel to the main panel
        add(drawPanel); // Add the draw panel to the main panel

        // Wait for the GameField to be displayed before adjusting sizes
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                responsiveLayout(); // Adjust layout when resized
            }
        });

        // Set click listener for the draw panel cards
        drawPanel.drawClicked((value) -> {
            if (onDrawClicked != null) {
                onDrawClicked.accept(value); // Trigger the draw click callback
            }
        });
    }

    /**
     * Creates the Bin Card Panel and sets it up with the specified card value.
     * @param value The value of the card to be displayed in the bin panel
     */
    private void createBinCardPanel(int value) {
        System.out.println("Creating Bin Panel");
        binCardPanel = new CardPanel(value, true, false, false); // Initialize the bin card panel with appropriate properties
        binCardPanel.setLayout(null);
    }

    /**
     * Creates the Draw Panel, which will hold all the cards of the player.
     * Depending on the size of the deck, it will create a different number of cards.
     * @param deck The list of card values to display in the draw panel
     */
    private void createDrawPanel(ArrayList<Integer> deck) {
        ArrayList<CardPanel> drawCards = new ArrayList<>();
        if (deck.size() <= 10) {
            // If the deck size is less than or equal to 10, create a normal set of cards
            for (int i = 0; i < deck.size(); ++i) {
                CardPanel temp;
                if (i == 0) {
                    temp = new CardPanel(deck.get(i), false, false, true); // First card with animation
                } else {
                    temp = new CardPanel(deck.get(i), false, false, false); // Other cards without animation
                }
                temp.setLayout(null);
                drawCards.add(temp); // Add card to the draw panel list
            }
        } else {
            // If the deck size is larger than 10, create cards in batches
            for (int i = 0; i < deck.size() / 3; ++i) {
                CardPanel temp;
                if (i == 0) {
                    temp = new CardPanel(deck.get(i), false, false, true); // First card with animation
                } else {
                    temp = new CardPanel(deck.get(i), false, false, false); // Other cards without animation
                }
                temp.setLayout(null);
                drawCards.add(temp); // Add card to the draw panel list
            }
        }
        drawPanel = new DrawPanel(drawCards); // Initialize the draw panel with the list of card panels
    }

    /**
     * Handles the layout and positioning of the panels in response to a resize event.
     * Adjusts the size and position of the Bin Card Panel and Draw Panel.
     */
    private void responsiveLayout() {
        int panelWidth = (int) (getWidth() * 0.25);

        // Position of BinCardPanel (to the left of the center)
        int xBin = (int) ((getWidth() - panelWidth) / 2.5);
        int y = (int) ((getHeight() - getHeight()) / 5.5); // Position the panels vertically centered
        binCardPanel.setBounds(xBin, y, panelWidth, (int) (getHeight() * 0.9));

        // Position of DrawPanel (symmetrical to the right of the BinCardPanel)
        int xDraw = (int) ((getWidth() - panelWidth) / 1.5f);
        drawPanel.setBounds(xDraw, y, panelWidth, getHeight());
    }

    /**
     * Sets the callback listener for the draw panel card click event.
     * @param listener The listener to be called when a card is clicked in the draw panel
     */
    public void drawClick(Consumer<Integer> listener) {
        this.onDrawClicked = listener;
    }

    /**
     * Sets the callback listener for the "hand down" click event (if applicable).
     * @param listener The listener to be called when the "hand down" action occurs
     */
    public void handDownClicked(Consumer<Integer> listener) {
        this.handDownClicked = listener;
    }

    /**
     * Updates the Bin card with a new value and starts the animation for the BinCardPanel.
     * @param bin The new value to update the Bin card with
     */
    protected void updateBin(int bin) {
        binCardPanel.update(bin, true); // Update the Bin card
        binCardPanel.startAnimation(); // Start the animation for the Bin card
        binCardPanel.revalidate(); // Revalidate the component to update its layout
        binCardPanel.repaint(); // Repaint the component to reflect the changes
    }

    /**
     * Updates the Draw panel, specifically removing the first card and resetting the click listener.
     */
    protected void updateDraw() {
        drawPanel.updateDraw(); // Update the Draw panel by removing the first card
        drawPanel.revalidate(); // Revalidate the component to update its layout
        drawPanel.repaint(); // Repaint the component to reflect the changes
    }

    protected void updateDrawPanel(ArrayList<Integer> deck) {
        drawPanel.update(deck); // Update the Draw panel by removing the first card
        drawPanel.revalidate(); // Revalidate the component to update its layout
        drawPanel.repaint(); // Repaint the component to reflect the changes
    }
}
