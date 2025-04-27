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
	
	/**
	 * Ajoute une carte a la main du joueur
	 * @param card la carte a ajouter
	 */
	public void drawCard(Card card) {
		this.hand.add(card);
	}

	/**
	 * Renvoie la main du joueur
	 * @return la main du joueur
	 */
	public boolean getHandDown() {
		return handDown;
	}
	
	/**
	 * Renvoie la valeur de la main du joueur
	 * @return la valeur de la main du joueur
	 */
	public int getHandValue() {
		int temp = 0;
		for(int i = 0; i < hand.size(); i++) {
			temp += getCard(i).getValue();
		}
		return temp;
	}

	/**
	 * Set la main du joueur a une nouvelle main
	 * @param hand
	 */
	public void setHandDown(boolean handDown) {
		this.handDown = handDown;
	}
	
	/**
	 * Verifie si la carte est deja dans le tableau
	 * @param storage
	 * @param val
	 * @return true si la carte est deja dans le tableau, false sinon
	 */
	private boolean contains(int storage[], int val) {
		for(int a : storage) {
			if(val == a) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Compte les points du joueur
	 * @param hand
	 */
	protected void countPoints() {
		int[] storage = new int[7];
		int i=0;
		for(Card c : hand) {
			if(!contains(storage, c.getValue())){
				System.out.println("Storage :" + storage[i] + "Value : " + c.getValue() );
				addPoint(c.getValue());
				storage[i] = c.getValue();
				i++;
			}
		}
	}
	
	/**
	 * Affiche la main du joueur
	 */
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
