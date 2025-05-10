package Back_end;

import java.util.ArrayList;
import java.util.Random;

public class Deck extends ArrayList<Card> {

	/**
	 * Constructor for the Deck class.
	 * It initializes the deck with 8 sets of 7 cards each, where the value of each card ranges from 1 to 7.
	 * The deck is then shuffled.
	 */
	public Deck() {
		super();
		// Create 8 sets of 7 cards with values from 1 to 7
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 7; j++) {
				this.add(new Card(j + 1));
			}
		}
		// Shuffle the deck
		this.shuffle();
	}

	/**
	 * Shuffles the deck using a random order.
	 * This method randomly rearranges the order of the cards in the deck.
	 */
	public void shuffle() {
		Random rand = new Random();
		for (int i = this.size() - 1; i > 0; i--) {
			int j = rand.nextInt(this.size());
			Card temp = this.get(i);
			this.set(i, this.get(j));
			this.set(j, temp);
		}
	}

	/**
	 * Draws (removes and returns) the top card from the deck.
	 * If the deck is empty, an exception is thrown.
	 *
	 * @return The top card of the deck.
	 * @throws IllegalArgumentException If the deck is empty.
	 */
	public Card draw() {
		if (size() <= 0)
			throw new IllegalArgumentException("There are no more cards in the deck.");
		return this.remove(0); // Removes and returns the first card
	}

	/**
	 * Gets a hand of 6 cards by drawing them from the deck.
	 *
	 * @return An ArrayList of 6 drawn cards.
	 */
	protected ArrayList<Card> getHand() {
		ArrayList<Card> temp = new ArrayList<Card>(6);
		for (int i = 0; i < 6; i++) {
			temp.add(this.draw()); // Draw 6 cards
		}
		// temp.sort(null); // Uncomment if you want to sort the hand
		return temp;
	}
}
