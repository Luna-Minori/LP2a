package Back_end;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in); // Declare scanner once here

		// Proceed with the rest of your game logic
		Board board = new Board();
		int nb_player = getnumberPlayers(scanner);
		for (int i = 0; i < nb_player; i++) {
			board.addPlayer(new Player(getname(scanner), true));
		}
		int nb_bot = getnumberBot(scanner);
		for (int i = 0; i < nb_bot; i++) {
			board.addPlayer(new Player("Bot" + i, false));
		}

		// Continue with the game
		board.new_turn();
		boolean bool = true;
		while (board.stateOfGame()) {
			for (int i = 0; i < board.getPlayers().size(); i++) {
				if (!board.stateOfGame()) {
					break;
				}
				if (board.getPlayers().get(i).getHandDown() != true) {
					board.getPlayers().get(i).affhand();
					if(board.getPlayers().get(i).getHuman() == true) {
						System.out.print(board.toStringBin());
						boolean endturn = false;
						do {
							try {
								switch (getaction(scanner)) {
								case 1:
									board.getPlayers().get(i).drawCard(board.getDeck().draw());
									board.getPlayers().get(i).affhand();
									endturn = true;
									break;
								case 2:
									board.getPlayers().get(i).affhand();
									board.cardPlayble(getindex(board.getPlayers().get(i).getHand().size(), scanner), i);
									endturn = true;
									break;
								case 3:
									board.getPlayers().get(i).setHandDown(true);
									board.getPlayers().get(i).affhand();
									endturn = true;
									break;
								default:
									break;
								}
							} catch (IllegalArgumentException e) {
								System.out.println(e.getMessage());
							}
						} while (!endturn);
					}
					else {
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
		}
		board.countPointsTurn();
		scanner.close(); // Close scanner at the end
	}

	private static String getname(Scanner scanner) {
		String name;
		do {
			System.out.println("Veuillez saisir votre nom: ");
			name = scanner.nextLine();
		} while (name.isEmpty());
		return name;
	}

	private static int getnumberPlayers(Scanner scanner) {
		int number;
		do {
			System.out.println("Veuillez saisir un nombre de joueur entre 2 et 6: ");
			number = scanner.nextInt();
		} while (!(number >= 2 && number <= 6));
		return number;
	}
	
	private static int getnumberBot(Scanner scanner) {
		int number;
		do {
			System.out.println("Veuillez saisir un nombre de Bot entre 1 et 2 : ");
			number = scanner.nextInt();
		} while (!(number >= 1 && number <= 2));
		return number;
	}

	private static int getaction(Scanner scanner) {
		int number;
		do {
			System.out.println("Veuillez saisir un nombre entre 1 et 3: ");
			System.out.println(" 1 : draw ");
			System.out.println(" 2 : discard ");
			System.out.println(" 3 : Quit turn");
			number = scanner.nextInt();
		} while (!(number >= 1 && number <= 3));
		return number;
	}

	private static int getindex(int taille, Scanner scanner) {
		int number;
		do {
			System.out.println("Veuillez saisir un nombre entre 1 et " + taille);
			number = scanner.nextInt();
		} while (!(number >= 1 && number <= taille));
		return number - 1;
	}
}
