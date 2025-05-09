package Back_end;

import java.util.ArrayList;

public class Player {

	private int pointRound = 0;
	private int pointGame = 0;
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

	public int getPointGame() {
		return pointGame;
	}

	public int getPointRound() {
		return pointRound;
	}

	public void addPoint(int point) {
		this.pointRound += point;
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

	protected void setPointRound(){
		this.pointRound = 0;
	}

	public boolean getHandDown() {
		return handDown;
	}

	public int getHandValue() {
		int temp = 0;
		for(int i = 0; i < hand.size(); i++) {
			if(getCard(i).getValue() == 7) {
				temp += 10;
			}
			else{
				temp += getCard(i).getValue();
			}
		}
		return temp;
	}

	public void setHandDown(boolean handDown) {
		this.handDown = handDown;
	}
	
	private boolean contains(int[] storage, int val) {
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
				if(c.getValue() != 7) {
					addPoint(c.getValue());
				}
				else{
					addPoint(10);
				}
				storage[i] = c.getValue();
				i++;
			}
		}
	}

	protected void updatePointGame(){
		this.pointGame += this.pointRound;
		this.pointRound = 0;
	}
}
