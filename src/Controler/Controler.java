package Controler;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import javax.swing.JOptionPane;

import Back_end.*;
import Front_end.*;

public class Controler {
	private boolean gameStarted = false;

	
		public static void main(String[] args) {
			
		}

		/*
	    CountDownLatch latch = new CountDownLatch(1); // 🔒 bloque l’exécution

	    Menu menu = new Menu();
	    Board board = new Board();
	    
        menu.initGameReady((playerNames,nbBots) -> {		        
	        for (String name : playerNames) {
	        	board.addPlayer(new Player(name, true));
	        }
	        for (int i = 0; i < nbBots; i++) {
	        	board.addPlayer(new Player("Bot" + i, false));
	        }
	        System.out.println("Hello " + playerNames);

	        latch.countDown();
	    });

	    try {
	        latch.await();
	    } catch (InterruptedException ex) {
	        ex.printStackTrace();
	    }

	    System.out.println("Le jeu peut maintenant continuer.");
		board.new_turn();
		BoardPanel pB = new BoardPanel(createHandFront(board), createNamesFront(board), board.getBin().getValue(), createDeckFront(board), 0);
		boolean bool = true;
		//while (board.stateOfGame()) {
			for (int i = 0; i < board.getPlayers().size(); i++) {
				Player currentPlayer = board.getPlayers().get(i);
				//pB.update(createHandFront(board), createNamesFront(board), board.getBin().getValue(), createDeckFront(board), i);
				if (!board.stateOfGame()) {
					break;
				}
				if (currentPlayer.getHandDown() != true) {
					if(currentPlayer.getHuman() == true) {
						
						boolean endturn = false;
						/*pB.setOnDrawRequested(v -> {							   
						    currentPlayer.drawCard(board.getDeck().draw());
						    // Mise à jour de l'affichage ici si nécessaire
						});*/
						/*
						playButton.addActionListener(e -> {
						    try {
						        // Ici tu dois afficher la main et permettre de cliquer sur une carte ou choisir son index
						        int index = getIndexFromUser(); // méthode à créer avec une popup ou autre
						        board.cardPlayble(index, i);
						        endTurn();
						    } catch (Exception ex) {
						        JOptionPane.showMessageDialog(null, ex.getMessage());
						    }
						});
						foldButton.addActionListener(e -> {
						    board.getPlayers().get(i).setHandDown(true);
						    board.getPlayers().get(i).affhand();
						    endTurn();
						});
						
					}
					else {
						/*
						if(board.getPlayers().get(i).getHandValue() <= 5) {
							board.getPlayers().get(i).setHandDown(true);
						}	
						else {
							for(int j = 0; j < board.getPlayers().get(i).getHand().size(); j++) {
								if(board.getPlayers().get(i).getCard(j).getValue() >= board.getBin().getValue() || (board.getBin().getValue() == 7 && board.getPlayers().get(i).getCard(j).getValue() == 1)) {
									board.cardPlayble(j, i);
									board.getPlayers().get(i).affhand();
									break;
								}
							}
							if(board.getPlayers().get(i).getHandValue() <= 5) {
								board.getPlayers().get(i).setHandDown(true);
							}	
							else {
								try {
									board.getPlayers().get(i).drawCard(board.getDeck().draw());
								} catch (IllegalArgumentException e) {
									board.getPlayers().get(i).setHandDown(true);
								}
							}
						}
					}
					
				}
			}
		//}
		//board.countPointsTurn();
			*/
		private static ArrayList<ArrayList<Integer>> createHandFront(Board b) {
			ArrayList<ArrayList<Integer>> hands = new ArrayList<>();
			for (Player p : b.getPlayers()) {
			    ArrayList<Integer> handValues = new ArrayList<>();
			    for (Card h : p.getHand()) {
			        handValues.add(h.getValue()); // ou h.toInt() si c’est une méthode équivalente
			    }
			    hands.add(handValues);
			}
			return hands;
		}
		
		private static ArrayList<Integer> createDeckFront(Board b) {
			ArrayList<Integer> deck = new ArrayList<>();
			for (Card c : b.getDeck()){
				deck.add(c.getValue());
			}
			return deck;
		}
		
		private static ArrayList<String> createNamesFront(Board b) {
			ArrayList<String> names = new ArrayList<>();
			for (Player p : b.getPlayers()){
				names.add(p.getName());
			}
			return names;
		}
}
