package Back_end;

import java.util.ArrayList;

public class Player {

	private int point = 0;
	private ArrayList<Card> hand = new ArrayList<>();
	private String name;
	private boolean handDown = false;
	private boolean human = true;
	
	public boolean getHuman() {
		return this.human;
	}
	
	public Player(String name, boolean hum) {
		this.name = name;
		this.human = hum;
	}
	
	public int getPoint() {
		return point;
	}

	public void addPoint(int point) {
		this.point += point;
	}
	
	public String getName() {
		return name;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}
	
	public Card getCard(int index) {
		return this.hand.get(index);
	}

	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}
	
	public void drawCard(Card card) {
		this.hand.add(card);
	}

	public boolean getHandDown() {
		return handDown;
	}
	
	public int getHandValue() {
		int temp = 0;
		for(int i = 0; i < hand.size(); i++) {
			temp += getCard(i).getValue();
		}
		return temp;
	}

	public void setHandDown(boolean handDown) {
		this.handDown = handDown;
	}
	
	private boolean contains(int storage[], int val) {
		for(int a : storage) {
			if(val == a) {
				return true;
			}
		}
		return false;
	}
	
	protected void countPoints() {
		int[] storage = new int[7];
		int i=0;
		for(Card c : hand) {
			if(!contains(storage, c.getValue())){
				System.out.println("Storage :" + storage[i] + "Value : " + c.getValue());
				addPoint(c.getValue());
				storage[i] = c.getValue();
				i++;
			}
		}
	}
	
	public void affhand() {
        System.out.println(name + " a en main : ");
        if (hand.isEmpty()) {
            System.out.println("aucune carte.");
        } else {
            for (Card card : hand) {
                System.out.println(card.toString() + " ");
            }
            System.out.println();
        }
	}
}
