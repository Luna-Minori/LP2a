package Controler;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import javax.swing.JOptionPane;
import java.util.Random;

import Back_end.*;
import Front_end.*;
import jdk.jshell.EvalException;

public class Controler {
    private static int currentPlayerIndex;
    private static Player currentPlayer;
    private static Board board;
    private static BoardPanel pB;
    private static boolean played;
    private static boolean notfirstRound = false;


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
        board.new_round();
        pB = new BoardPanel(createHandsFront(board), createNamesFront(board), board.getBin().getValue(), createDeckFront(board), 0, createListHandPoint(board), createOfPoint(board));
        notfirstRound = false;
        pB.setOnPauseClicked((value) -> {
            System.out.println(value);
        });
        //init game
        currentPlayerIndex = 0;
        currentPlayer = board.getPlayers().get(currentPlayerIndex);
        int numberOfTurn = 0;

        // start
        while (board.stateOfGame()) {
            // Tant que la manche n'est pas terminée
            while (board.stateOfRound()) {
                if (!currentPlayer.getHuman()) {
                    System.out.println("Tour de l'IA ");
                    int delay = 1000 + new Random().nextInt(3000); // 1 à 4 sec
                    try {
                        Thread.sleep(delay);
                        iaTurn();
                    } catch (InterruptedException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                    System.out.println("fin Tour de l'IA ");
                } else {
                    if (notfirstRound) {
                        System.out.println("update ifno panel");
                        pB.updateInfoPanel(createHandsFront(board), createNamesFront(board), currentPlayerIndex, createOfPoint(board));
                    }
                    notfirstRound = true;
                    playerTurn(); // cette méthode est maintenant bloquante avec CountDownLatch
                }

                // Avancer au joueur suivant
                currentPlayerIndex = (currentPlayerIndex + 1) % board.getPlayers().size();
                currentPlayer = board.getPlayers().get(currentPlayerIndex);
            }

            // Fin de manche
            board.countPointsTurn();
            board.countPointGame();
            board.new_round();
            updateFront();

            pB.overlayUpdate(createNamesFront(board), createOfPoint(board));
            pB.showOverlay();

            numberOfTurn++;
        }

        pB.clear();
        System.out.println("controler finito");
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
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        pB.setOnDrawRequested(null);
        pB.setOnPlayCardRequested(null);
        pB.setOnHandDownRequested(null);
        pB.updateHandPoint(currentPlayer.getHandValue());
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
            try {
                board.cardPlayble(cardIndex.get(bestCard(cardValue)), currentPlayerIndex);
                pB.updateBin(board.getBin().getValue());
            } catch (IllegalArgumentException _) {
                currentPlayer.drawCard(board.getDeck().draw());
                pB.updateDraw();
            }
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
        if (!currentPlayer.getHuman()) {
            System.out.println("Erreur : tentative d'affichage de l'interface pour un bot");
            return; // ou ne rien faire
        }
        pB.update(createHandsFront(board), createNamesFront(board), board.getBin().getValue(), createDeckFront(board), currentPlayerIndex, createListHandPoint(board), createOfPoint(board));
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

    private static ArrayList<Integer> createListHandPoint(Board b) {
        ArrayList<Integer> points = new ArrayList<>();
        for (Player p : b.getPlayers()) {
            points.add(p.getHandValue());
        }
        return points;
    }

    private static ArrayList<Integer> createOfPoint(Board b) {
        ArrayList<Integer> points = new ArrayList<>();
        for (Player p : b.getPlayers()) {
            points.add(p.getPointGame());
        }
        return points;
    }
}
