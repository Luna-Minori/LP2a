package Controler;

import Back_end.Board;
import Back_end.Card;
import Back_end.Player;
import Front_end.BoardPanel;
import Front_end.Menu;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Main game controller class that initializes and manages the game loop,
 * handles player turns (human and bot), and updates the front-end accordingly.
 */
public class Controler {
    private static int currentPlayerIndex;
    private static Player currentPlayer;
    private static Board board;
    private static BoardPanel pB;
    private static boolean played;
    private static boolean ManyHuman = false;
    private static boolean noHuman = false;
    private static boolean end = false;

    /**
     * Main entry point of the game. Initializes players, starts the game loop,
     * and manages the turn logic.
     */
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);

        Menu menu = new Menu();
        board = new Board();

        menu.initGameReady((playerNames, nbBots) -> {
            for (String name : playerNames) {
                board.addPlayer(new Player(name, true));
            }
            if (playerNames.size() > 1) {
                ManyHuman = true;
            }
            if (playerNames.size() == 0) {
                noHuman = true;
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

        board.new_round();

        pB = new BoardPanel(createHandsFront(board), createNamesFront(board), board.getBin().getValue(), createDeckFront(board), 0, createListHandPoint(board), createOfPoint(board));

        boolean notFirstTurn = false;
        /*
        pB.setOnPauseClicked((value) -> {
            if (value == 2) {
                //Settings settings = new Settings();
            }
            if (value == 3) {
                // Quit game and return to menu
                SwingUtilities.invokeLater(() -> {
                    JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(pB);
                    if (topFrame != null) {
                        topFrame.dispose();
                    }
                    end = true;
                    restartGame();
                });
            }
        });
        */

        currentPlayerIndex = 0;
        currentPlayer = board.getPlayers().get(currentPlayerIndex);

        // Main game loop
        while (board.stateOfGame() && !end) {
            while (board.stateOfRound() && !end) {
                if (!currentPlayer.getHuman()) {
                    try {
                        if (noHuman) {
                            Thread.sleep(100);
                            updateFront(true);
                        } else {
                            Thread.sleep(1000 + new Random().nextInt(3000));
                        }
                        iaTurn();
                    } catch (InterruptedException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                } else {
                    if (notFirstTurn) {
                        pB.updateInfoPanel(createHandsFront(board), createNamesFront(board), currentPlayerIndex, createOfPoint(board));
                    }
                    if (ManyHuman && notFirstTurn) {
                        ArrayList<Integer> hand = createHandFront(currentPlayer);
                        String name = currentPlayer.getName();
                        int point = currentPlayer.getHandValue();
                        pB.updateFrontPlayer(hand, name, point);
                    }
                    notFirstTurn = true;
                    playerTurn();
                }

                currentPlayerIndex = (currentPlayerIndex + 1) % board.getPlayers().size();
                currentPlayer = board.getPlayers().get(currentPlayerIndex);
            }

            currentPlayerIndex = 0;
            currentPlayer = board.getPlayers().get(currentPlayerIndex);
            board.countPointsTurn();
            board.countPointGame();
            board.new_round();
            updateFront();
            pB.overlayUpdate(createNamesFront(board), createOfPoint(board));
            pB.showOverlay();
        }

        pB.clear();
    }

    /**
     * Handles the current human player's turn and waits for user interaction.
     */
    private static void playerTurn() {
        played = false;
        CountDownLatch latch = new CountDownLatch(1);

        pB.setOnDrawRequested(value -> {
            if (played) return;
            played = true;
            currentPlayer.drawCard(board.getDeck().draw());
            pB.updateDraw();
            pB.updateHand(createHandFront(currentPlayer));
            latch.countDown();
        });

        pB.setOnPlayCardRequested(index -> {
            if (played) return;
            try {
                board.cardPlayble(index, currentPlayerIndex);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                return;
            }
            played = true;
            pB.updateBin(board.getBin().getValue());
            pB.updateHand(createHandFront(currentPlayer));
            latch.countDown();
        });

        pB.setOnHandDownRequested(value -> {
            if (played) return;
            played = true;
            currentPlayer.setHandDown(true);
            updateFront();
            latch.countDown();
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        pB.setOnDrawRequested(null);
        pB.setOnPlayCardRequested(null);
        pB.setOnHandDownRequested(null);
        pB.updateHandPoint(currentPlayer.getHandValue());
    }

    /**
     * Manages the turn logic for a bot (AI player).
     */
    private static void iaTurn() {
        ArrayList<Integer> cardValue = new ArrayList<>();
        ArrayList<Integer> cardIndex = new ArrayList<>();

        for (int j = 0; j < currentPlayer.getHand().size(); j++) {
            Card card = currentPlayer.getCard(j);
            if (card.getValue() >= board.getBin().getValue() || (board.getBin().getValue() == 7 && card.getValue() == 1)) {
                cardValue.add(card.getValue());
                cardIndex.add(j);
            }
        }

        if (!cardValue.isEmpty()) {
            try {
                board.cardPlayble(cardIndex.get(bestCard(cardValue)), currentPlayerIndex);
                pB.updateBin(board.getBin().getValue());
            } catch (IllegalArgumentException ignored) {
                currentPlayer.drawCard(board.getDeck().draw());
                pB.updateDraw();
            }
        } else {
            try {
                if (currentPlayer.getHandValue() <= 5) {
                    currentPlayer.setHandDown(true);
                } else {
                    currentPlayer.drawCard(board.getDeck().draw());
                    pB.updateDraw();
                }
            } catch (IllegalArgumentException e) {
                currentPlayer.setHandDown(true);
            }
        }
    }

    /**
     * Updates the front-end with current game state if the player is human.
     */
    private static void updateFront(boolean force) {
        if (force) {
            pB.update(createHandsFront(board), createNamesFront(board), board.getBin().getValue(), createDeckFront(board), currentPlayerIndex, createListHandPoint(board), createOfPoint(board));
            return;
        } else {
            if (!currentPlayer.getHuman()) return;
            pB.update(createHandsFront(board), createNamesFront(board), board.getBin().getValue(), createDeckFront(board), currentPlayerIndex, createListHandPoint(board), createOfPoint(board));
        }
    }

    private static void updateFront() {
        if (!currentPlayer.getHuman()) return;
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

    /**
     * Returns the index of the best (lowest) card value in the list.
     */
    private static int bestCard(ArrayList<Integer> list) {
        int minIndex = 0;
        int minValue = list.getFirst();
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) < minValue) {
                minValue = list.get(i);
                minIndex = i;
            }
        }
        return minIndex;
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

    public static void restartGame() {
        SwingUtilities.invokeLater(() -> {
            try {
                main(new String[]{});  // Relance le jeu depuis le début
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}