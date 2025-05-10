package Back_end;

import java.util.ArrayList;

public class Board {

    private final ArrayList<Player> players = new ArrayList<>();
    private Deck deck;
    private Card bin;

    /**
     * Adds a player to the board.
     *
     * @param player The player to be added.
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Gets the list of players currently in the game.
     *
     * @return The list of players.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Gets the current deck of cards.
     *
     * @return The current deck.
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Gets the current card in the bin.
     *
     * @return The current card in the bin.
     */
    public Card getBin() {
        return bin;
    }

    /**
     * Sets the card in the bin.
     *
     * @param c The card to be placed in the bin.
     */
    public void setBin(Card c) {
        this.bin = c;
    }

    /**
     * Starts a new turn by resetting the deck and hands of all players.
     * Draws a new card for the bin.
     */
    public void new_turn() {
        deck = new Deck();
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setHand(deck.getHand());
        }
        bin = deck.draw();
    }

    /**
     * Starts a new round by resetting the deck and players' hands.
     * Sets all players' hands to be not down and resets their points for the round.
     * Draws a new card for the bin.
     */
    public void new_round() {
        deck = new Deck();
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setHandDown(false);
            players.get(i).setPointRound();
            players.get(i).setHand(deck.getHand());
        }
        bin = deck.draw();
    }

    /**
     * Checks the state of the round.
     * The round is over if all hands are not empty and players are out of the turn.
     *
     * @return True if the round can proceed, false otherwise.
     */
    public boolean stateOfRound() {
        return statofhands() && playerout();
    }

    /**
     * Checks the state of the game.
     * The game continues as long as no player has reached 40 points.
     *
     * @return True if the game is ongoing, false if a player has reached 40 points.
     */
    public boolean stateOfGame() {
        for (Player p : players) {
            if (p.getPointGame() >= 40) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if all players have cards left in their hands.
     *
     * @return True if no player's hand is empty, false if at least one player's hand is empty.
     */
    private boolean statofhands() {
        int count = 0;
        for (Player p : players) {
            if (p.getHand().isEmpty()) {
                count += 1;
            }
        }
        return count == 0; // True if all players have cards
    }

    /**
     * Checks if any player is still in the game (i.e., hasn't finished their turn).
     *
     * @return True if at least one player is still in the game, false if all players are out.
     */
    private boolean playerout() {
        int count = 0;
        for (Player p : players) {
            if (p.getHandDown()) {
                count += 1;
            }
        }
        return count != players.size(); // If at least one player is still playing
    }

    /**
     * Checks if the card played by the player is valid.
     * A card is playable if its value matches the value of the bin card or is one higher, or if the bin card is 7 and the played card is 1.
     *
     * @param index_card The index of the card played.
     * @param index_player The index of the player playing the card.
     * @throws IllegalArgumentException If the card played is not valid.
     */
    public void cardPlayble(int index_card, int index_player) {
        Player player = this.getPlayers().get(index_player);
        int valueCard = player.getCard(index_card).getValue();
        int valueBin = this.getBin().getValue();
        if (valueCard == valueBin || valueCard == valueBin + 1 || valueBin == 7 && valueCard == 1) {
            setBin(player.getCard(index_card));
            player.getHand().remove(index_card);
        } else {
            throw new IllegalArgumentException("The played card is not valid (choose a card with a value greater than or equal to the visible card)");
        }
    }

    /**
     * Calculates and displays the points for each player at the end of the turn.
     */
    public void countPointsTurn() {
        for (Player p : players) {
            p.countPoints();
            System.out.println(p.getName() + " has: " + p.getPointRound());
        }
    }

    /**
     * Calculates and displays the cumulative points for each player at the end of the game.
     */
    public void countPointGame() {
        for (Player p : players) {
            p.updatePointGame();
            System.out.println(p.getName() + " has: " + p.getPointGame());
        }
    }
}
