package Controler;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import javax.swing.JOptionPane;

import Back_end.*;
import Front_end.*;

public class Controler {
    private static int currentPlayerIndex;
    private static Player currentPlayer;
    private static Board board;
    private static BoardPanel pB;
    private static boolean played;


    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);

        Menu menu = new Menu();
        board = new Board();

        menu.initGameReady((playerNames, nbBots) -> {
            for (String name : playerNames) {
                board.addPlayer(new Player(name, true));
            }
            for (int i = 0; i < nbBots; i++) {
                board.addPlayer(new Player("Bot" + i, false));
            }
            latch.countDown();
        });

        try {
            latch.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        System.out.println("Le jeu peut maintenant continuer.");
        board.new_turn();
        pB = new BoardPanel(createHandsFront(board), createNamesFront(board), board.getBin().getValue(), createDeckFront(board), 0);
        nextTurn();
        //board.countPointsTurn();
        System.out.println("controler finito");
    }

    private static void safelyCountDown(CountDownLatch latch) {
        if (latch.getCount() > 0) {
            latch.countDown();
        }
    }

    private static void nextTurn() {
        if (!board.stateOfGame()) {
            System.out.println("Fin du jeu");
            return;
        }
        currentPlayer = board.getPlayers().get(currentPlayerIndex);

        if (currentPlayer.getHandDown()) {
            return;
        }
        if (currentPlayer.getHuman()) {
            playerTurn();
        } else { // IA
            System.out.println("Tour de l'IA ");
            iaTurn();
            System.out.println("fin Tour de l'IA ");
            nextPlayer();
        }
    }

    private static void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % board.getPlayers().size();
        nextTurn();
    }

    private static void playerTurn() {
        System.out.println("Tour du joueur " + currentPlayer.getName());
        played = false; // reset à chaque tour
        CountDownLatch latch = new CountDownLatch(1);

        pB.setOnDrawRequested(value -> {
            System.out.println(played);
            if (played) {
                return; // ignore si déjà fait
            }
            played = true;
            currentPlayer.drawCard(board.getDeck().draw());
            pB.updateDraw();
            pB.updateHand(createHandFront(currentPlayer));
            latch.countDown();
        });

        pB.setOnPlayCardRequested(index -> {
            if (played) {
                return;
            }
            System.out.println("bin " + board.getBin().getValue());
            try {
                board.cardPlayble(index, currentPlayerIndex);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, e.getMessage()); // Show error message
                return;
            }
            played = true;
            System.out.println("bin " + board.getBin().getValue());
            pB.updateBin(board.getBin().getValue());
            pB.updateHand(createHandFront(currentPlayer));
            latch.countDown();
        });

        pB.setOnHandDownRequested(value -> {
            if (played) {
                return;
            }

            played = true;
            currentPlayer.setHandDown(true);
            updateFront();
            latch.countDown();
        });

        try {
            latch.await(); // Attend la fin de l'une des actions avant de continuer
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pB.setOnDrawRequested(null);
        pB.setOnPlayCardRequested(null);
        pB.setOnHandDownRequested(null);
        nextPlayer();
    }

    private static void iaTurn() {
        ArrayList<Integer> cardValue = new ArrayList<>();
        ArrayList<Integer> cardIndex = new ArrayList<>();
        for (int j = 0; j < currentPlayer.getHand().size(); j++) {
            if (currentPlayer.getCard(j).getValue() >= board.getBin().getValue() || (board.getBin().getValue() == 7 && currentPlayer.getCard(j).getValue() == 1)) {
                cardValue.add(currentPlayer.getCard(j).getValue());
                cardIndex.add(j);
            }
        }
        if (!cardValue.isEmpty()) {
            board.cardPlayble(cardIndex.get(bestCard(cardValue)), currentPlayerIndex);
            pB.updateBin(board.getBin().getValue());
        } else {
            try {
                if (currentPlayer.getHandValue() <= 5) {
                    currentPlayer.setHandDown(true);
                    //update info
                } else {
                    currentPlayer.drawCard(board.getDeck().draw());
                    pB.updateDraw();
                }
            } catch (IllegalArgumentException e) {
                currentPlayer.setHandDown(true);
            }
        }
    }


    private static void updateFront() {
        pB.update(createHandsFront(board), createNamesFront(board), board.getBin().getValue(), createDeckFront(board), currentPlayerIndex);
    }

    private static ArrayList<ArrayList<Integer>> createHandsFront(Board b) {
        ArrayList<ArrayList<Integer>> hands = new ArrayList<>();
        for (Player p : b.getPlayers()) {
            hands.add(createHandFront(p));
        }
        return hands;
    }

    private static ArrayList<Integer> createHandFront(Player p) {
        ArrayList<Integer> handValues = new ArrayList<>();
        for (Card h : p.getHand()) {

            handValues.add(h.getValue());
        }
        return handValues;
    }

    private static ArrayList<Integer> createDeckFront(Board b) {
        ArrayList<Integer> deck = new ArrayList<>();
        for (Card c : b.getDeck()) {
            deck.add(c.getValue());
        }
        return deck;
    }

    private static ArrayList<String> createNamesFront(Board b) {
        ArrayList<String> names = new ArrayList<>();
        for (Player p : b.getPlayers()) {
            names.add(p.getName());
        }
        return names;
    }

    private static int bestCard(ArrayList<Integer> list) {
        int minIndex = 0;
        int minValue = list.getFirst();
        for (int i = 1; i < list.size(); i++) { // shear min
            if (list.get(i) < minValue) {
                minValue = list.get(i);
                minIndex = i;
            }
        }
        return minIndex; // renvoie l'index dans cardValue
    }

}
