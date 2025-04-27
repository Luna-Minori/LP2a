package Back_end;

import java.util.ArrayList;
import java.util.Random;

public class Deck extends ArrayList<Card> {


	public Deck() {
		super();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 7; j++) {
				this.add(new Card(j + 1));
			}
		}
		this.shuffle();
	}

	/**
	 * Melange la pioche
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
	 * Renvoie la carte du dessus de la pioche et l'enleve de la pioche
	 * 
	 * @return la carte du dessus de la pioche
	 */
	public Card draw() {
		if (size() <= 0)
			throw new IllegalArgumentException("Il n'y a plus de cartes dans la pioche");
		return this.removeFirst();
	}

	/**
	 * Renvoie une main de 6 cartes
	 * 
	 * @return la main du joueur
	 */
	protected ArrayList<Card> getHand() {
		ArrayList<Card> temp = new ArrayList<Card>(6);
		for (int i = 0; i < 6; i++) {
			temp.add(this.draw());
		}
		// temp.sort(null);
		return temp;
	}
}