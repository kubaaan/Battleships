package game;

import game.algorithm.AlgorithmType;
import game.player.*;

public class Battleships {

    private final Player playerOne;
    private final Player playerTwo;

    public Battleships() {
        this("playerVsCPU");
    }

    public Battleships(String gameMode) {
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
            playTurn();
        }
    }

    private void playTurn() {
        playerOne.playTurn(playerTwo);
        playerTwo.playTurn(playerOne);
    }
}