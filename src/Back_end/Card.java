package Back_end;

import java.util.Comparator;

public class Card implements Comparator<Card> {
	
	private int value;
	
	public Card(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	@Override
	public int compare(Card c1, Card c2) {
		return Integer.compare(c1.value, c2.value);
	}
	
	public String toString() {
		return String.format("La valeur de ta carte est %d ", value);
	}
	
}
