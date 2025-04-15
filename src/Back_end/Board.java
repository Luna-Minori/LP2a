package Back_end;

import java.util.ArrayList;

public class Board {

	private ArrayList<Player> players = new ArrayList<>();
	private Deck deck;
	private Card bin;

	public void addPlayer(Player player) {
		players.add(player);
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public Deck getDeck() {
		return deck;
	}
	
	public Card getBin() {
		return bin;
	}
	
	public void setBin(Card c) {
		this.bin = c;
	}
	
	/*
	 * Renvoie le nombre de joueurs
	 * 
	 * @return le nombre de joueurs en string
	 */
	public String toStringBin() {
		return String.format("La carte visible est : %d\n", bin.getValue());
	}

	/*
	 * Renvoie le nombre de joueurs
	 * 
	 * @return le nombre de joueurs
	 */
	public void new_turn() {
		deck = new Deck();
		for (int i = 0; i < players.size(); i++) {
			players.get(i).setHand(deck.getHand());
		}
		bin = deck.draw();
	}
	
	/*
	 * Renvoie vrai si toutes les mains sont pleines et si tous les joueurs sont
	 * sortis
	 * 
	 * @return vrai si toutes les mains sont pleines et si tous les joueurs sont
	 * sortis, faux sinon
	 */
	protected boolean stateOfGame() {
		return statofhands() && playerout();
	}
	
	/*
	 * Renvoie vrai si toutes les mains sont pleines
	 * @return vrai si toutes les mains sont pleines, faux sinon
	 */
	private boolean statofhands() {
		int count = 0;
		for( Player p : players) {
			if(p.getHand().isEmpty()){
				count += 1;
			}
		}
		if( count != 0 ) { // false if one hand or more are empty
			return false;
		}
		return true;	// true if every hand are not empty
	}
	
	/*
	 * Renvoie vrai si tous les joueurs sont sortis
	 * @return vrai si tous les joueurs sont sortis, faux sinon
	 */
	private boolean playerout() {
		int count = 0;
		for( Player p : players) {
			if(p.getHandDown()) {
				count += 1;
			}
		}
		if( count != players.size()) {
			return true; // if player(s) are in the turn
		}
		return false; // if all players are out 
	}
	
	/*
	 * Renvoie la carte jouée par le joueur
	 * @param index_card l'index de la carte jouée
	 * @param index_player l'index du joueur
	 * @throws IllegalArgumentException si la carte jouée n'est pas valable
	 */
	protected void cardPlayble(int index_card, int index_player) {
		Player player = this.getPlayers().get(index_player);
		if((player.getCard(index_card).getValue() >= bin.getValue()) || (bin.getValue() == 7 && player.getCard(index_card).getValue() == 1)){
			setBin(player.getCard(index_card));
			player.getHand().remove(index_card);
		}		
		else {
			throw new IllegalArgumentException("La carte jouée n'es pas valable (choisir une carte de valeur supérieure ou égale à la carte visible)");
		}		
	}
	
	/*
	 * Compte les points de chaque joueur
	 */
	protected void countPointsTurn() {
		for(Player p : players) {
			p.getHand();
			p.countPoints();
			System.out.println(p.getName() + " a : " + p.getPoint());
		}
	}
}
