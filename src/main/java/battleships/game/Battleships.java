package battleships.game;

import battleships.game.algorithm.AlgorithmType;
import battleships.game.player.*;

public class Battleships {

    private final Player playerOne;
    private final Player playerTwo;
    private int currentTurn;

    public Battleships() {
        this("playerVsCPU");
    }

    public Battleships(String gameMode) {
        this.currentTurn = 0;
        if (gameMode.equals("simulation")) {
            playerOne = new ComputerPlayer("CPU1", AlgorithmType.RANDOM);
            playerTwo = new ComputerPlayer("CPU2", AlgorithmType.HUNT_TARGET);
        } else {
            playerOne = new HumanPlayer("Human");
            playerTwo = new ComputerPlayer();
        }
    }

    public void runGame() {
        System.out.println("**** Welcome to Battle Ships game ****\n");
        System.out.println("Right now, the sea is empty\n");

        playerOne.printGrid(true);
        playerOne.deployShips();
        playerTwo.deployShips();

        while (playerOne.getShipPoints() != 0 && playerTwo.getShipPoints() != 0) {
            currentTurn++;
            playTurn();
        }

        printStatistics();
    }

    private void playTurn() {
        playerOne.playTurn(playerTwo);
        playerTwo.playTurn(playerOne);
    }

    private void printStatistics() {
        Player winner = determineWinner();
        if (winner != null) {
            System.out.print("And the winner is ");
            winner.printStatistics(currentTurn);
        } else {
            System.out.println("It's a draw");
            playerOne.printStatistics(currentTurn);
            playerTwo.printStatistics(currentTurn);
        }
    }

    private Player determineWinner() {
        if (playerOne.getShipPoints() > playerTwo.getShipPoints()) {
            return playerOne;
        } else if (playerTwo.getShipPoints() > playerOne.getShipPoints()) {
            return playerTwo;
        } else {
            return null;
        }
    }
}