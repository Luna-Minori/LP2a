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
	
	public String toStringBin() {
		return String.format("La carte visible est : %d\n", bin.getValue());
	}

	public void new_turn() {
		deck = new Deck();
		for (int i = 0; i < players.size(); i++) {
			players.get(i).setHand(deck.getHand());
		}
		bin = deck.draw();
	}
	
	public boolean stateOfRound() {
		return statofhands() && playerout();
	}
	public boolean stateOfGame(){
		for(Player p : players) {
			if(p.getPointGame() >= 40){
				return false;
			};
		}
		return true;
	}
		
	private boolean statofhands() {
		int count = 0;
		for( Player p : players) {
			if(p.getHand().isEmpty()){
				count += 1;
			}
		}
        // false if one hand or more are empty
        return count == 0;// true if every hand are not empty
    }
	
	private boolean playerout() {
		int count = 0;
		for( Player p : players) {
			if(p.getHandDown()) {
				count += 1;
			}
		}
        return count != players.size(); // if player(s) are in the turn
// if all players are out
    }
	
	public void cardPlayble(int index_card, int index_player) {
		Player player = this.getPlayers().get(index_player);
		int valueCard = player.getCard(index_card).getValue();
		int valueBin = this.getBin().getValue();
		if (valueCard == valueBin || valueCard == valueBin + 1 || valueBin == 7 && valueCard == 1) {
			setBin(player.getCard(index_card));
			player.getHand().remove(index_card);
		}		
		else {
			throw new IllegalArgumentException("La carte jouée n'es pas valable (choisir une carte de valeur supérieure ou égale à la carte visible)");
		}		
	}
	
	public void countPointsTurn() {
		for(Player p : players) {
			p.countPoints();
			System.out.println(p.getName() + " a : " + p.getPointRound());
		}
	}

	public void countPointGame(){
		for(Player p : players) {
			p.updatePointGame();
			System.out.println(p.getName() + " a : " + p.getPointRound());
		}
	}
}
