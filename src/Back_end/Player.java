package Back_end;

import java.util.ArrayList;

public class Player {

    private int pointRound = 0; // Points accumulated in the current round
    private int pointGame = 0;  // Total points accumulated across all rounds
    private ArrayList<Card> hand = new ArrayList<>(); // Cards held by the player
    private final String name;  // Player's name
    private boolean handDown = false; // Whether the player's hand is down (indicating the player is out for the round)
    private boolean human = true; // Whether the player is a human (as opposed to a bot)

    /**
     * Constructor for the Player class.
     *
     * @param name The name of the player.
     * @param hum  Whether the player is human or a bot.
     */
    public Player(String name, boolean hum) {
        this.name = name;
        this.human = hum;
    }

    /**
     * Returns whether the player is human.
     *
     * @return true if the player is human, false if the player is a bot.
     */
    public boolean getHuman() {
        return this.human;
    }

    /**
     * Returns the total points accumulated by the player in the game.
     *
     * @return The total points of the player in the game.
     */
    public int getPointGame() {
        return pointGame;
    }

    /**
     * Returns the points accumulated by the player in the current round.
     *
     * @return The points of the player for the current round.
     */
    public int getPointRound() {
        return pointRound;
    }

    /**
     * Adds points to the player's point total for the current round.
     *
     * @param point The number of points to add.
     */
    public void addPoint(int point) {
        this.pointRound += point;
    }

    /**
     * Returns the player's name.
     *
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the player's hand (the cards they currently hold).
     *
     * @return The list of cards in the player's hand.
     */
    public ArrayList<Card> getHand() {
        return hand;
    }

    /**
     * Sets the player's hand to a given list of cards.
     *
     * @param hand The new hand of cards to assign to the player.
     */
    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    /**
     * Returns the card at a specified index from the player's hand.
     *
     * @param index The index of the card in the hand.
     * @return The card at the specified index.
     */
    public Card getCard(int index) {
        return this.hand.get(index);
    }

    /**
     * Adds a card to the player's hand.
     *
     * @param card The card to draw (add) to the player's hand.
     */
    public void drawCard(Card card) {
        this.hand.add(card);
    }

    /**
     * Resets the player's points for the current round to 0.
     */
    protected void setPointRound() {
        this.pointRound = 0;
    }

    /**
     * Returns whether the player has placed their hand down (is out for the round).
     *
     * @return true if the hand is down (player is out), false otherwise.
     */
    public boolean getHandDown() {
        return handDown;
    }

    /**
     * Sets whether the player's hand is down (the player is out for the round).
     *
     * @param handDown true if the hand is down (player is out), false otherwise.
     */
    public void setHandDown(boolean handDown) {
        this.handDown = handDown;
    }

    /**
     * Returns the total value of the cards in the player's hand.
     *
     * @return The total value of the cards in the hand.
     */
    public int getHandValue() {
        int temp = 0;
        for (int i = 0; i < hand.size(); i++) {
            if (getCard(i).getValue() == 7) {
                temp += 10; // Special case for cards with value 7
            } else {
                temp += getCard(i).getValue();
            }
        }
        return temp;
    }

    /**
     * Checks if a value already exists in the provided array.
     *
     * @param storage The array to check.
     * @param val The value to check for.
     * @return true if the value exists in the array, false otherwise.
     */
    private boolean contains(int[] storage, int val) {
        for (int a : storage) {
            if (val == a) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates the points for the player based on their hand and adds them to the point round.
     * Cards with a value of 7 are worth 10 points, others are worth their value.
     */
    protected void countPoints() {
        ArrayList<Integer> storage = new ArrayList<>();
        for (Card c : hand) {
            int value = c.getValue();
            if (!storage.contains(value)) {
                if (value != 7) {
                    addPoint(value);
                } else {
                    addPoint(10);
                }
                storage.add(value);
            }
        }
    }


    /**
     * Updates the player's total points for the game by adding the points from the current round,
     * and then resets the points for the current round.
     */
    protected void updatePointGame() {
        this.pointGame += this.pointRound;
        this.pointRound = 0; // Reset the points for the current round
    }
}
