package battleships.game;

import battleships.game.algorithm.AlgorithmType;
import battleships.game.grid.ShipType;
import battleships.game.player.*;

import java.util.ArrayList;
import java.util.List;

public class Battleships {

    private static Battleships battleships;
    private final Player playerOne;
    private final Player playerTwo;
    private int currentTurn;
    private final List<ShipType> shipsList;

    private Battleships() {
        this("playerVsCPU");
    }

    private Battleships(String gameMode) {
        this.currentTurn = 0;

        this.shipsList = new ArrayList<>();
        this.shipsList.add(ShipType.CARRIER);
        this.shipsList.add(ShipType.CRUISER);
        this.shipsList.add(ShipType.SUBMARINE_1);
        this.shipsList.add(ShipType.SUBMARINE_2);
        this.shipsList.add(ShipType.DESTROYER);

        if (gameMode.equals("simulation")) {
            playerOne = new ComputerPlayer("CPU1", AlgorithmType.RANDOM);
            playerTwo = new ComputerPlayer("CPU2", AlgorithmType.HUNT_TARGET);
        } else {
            playerOne = new HumanPlayer("Human");
            playerTwo = new ComputerPlayer();
        }
    }

    public static Battleships getBattleships(String gameMode){

        if(Battleships.battleships == null){
            battleships = new Battleships(gameMode);
        }

        return battleships;
    }

    public void runGame() {

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