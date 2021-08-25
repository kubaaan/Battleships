package battleships.game;

import battleships.game.algorithm.AlgorithmType;
import battleships.game.grid.FieldStatus;
import battleships.game.grid.ShipType;
import battleships.game.player.*;
import battleships.model.DeployRequest;
import battleships.model.Guess;
import battleships.model.TurnResponse;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Battleships {

    private static Battleships battleships;
    private final Player playerOne;
    private final Player playerTwo;
    private int currentTurn;
    @Getter
    private static final Map<ShipType, Integer> shipsList = new HashMap<>();

    static {
        shipsList.put(ShipType.CARRIER, ShipType.CARRIER.getLength());
        shipsList.put(ShipType.CRUISER, ShipType.CRUISER.getLength());
        shipsList.put(ShipType.SUBMARINE_1, ShipType.SUBMARINE_1.getLength());
        shipsList.put(ShipType.SUBMARINE_2, ShipType.SUBMARINE_2.getLength());
        shipsList.put(ShipType.DESTROYER, ShipType.DESTROYER.getLength());
    }

    private Battleships(String gameMode) {
        this.currentTurn = 0;

        if (gameMode.equals("simulation")) {
            playerOne = new ComputerPlayer("CPU1", AlgorithmType.RANDOM);
            playerTwo = new ComputerPlayer("CPU2", AlgorithmType.HUNT_TARGET);
        } else {
            playerOne = new HumanPlayer("Human");
            playerTwo = new ComputerPlayer();
        }
    }

    public static void startGame(String gameMode) {
        Battleships.battleships = new Battleships(gameMode);
    }

    public static Battleships getBattleships() {
        return battleships;
    }

    public void deployShip(DeployRequest deployRequest) {
        this.playerOne.deployShip(deployRequest);
    }

    public Guess evaluateGuess(int address) {
        return evaluateGuess(address, playerOne, playerTwo);
    }

    public Guess evaluateGuess(int address, Player shootingPlayer, Player targetPlayer) {
        FieldStatus guessResult = targetPlayer.getGrid().guess(address);
        if (guessResult == FieldStatus.OCCUPIED) {
            int points = targetPlayer.getShipPoints() - 1;
            targetPlayer.setShipPoints(points);
        }
        int guess = targetPlayer.guess(shootingPlayer);

        Guess guessResponse = new Guess(guessResult, guess);

        if (playerOne.getShipPoints() == 0 || playerTwo.getShipPoints() == 0) {
            guessResponse.setGameEnded(true);
        }

        return guessResponse;
    }

    public TurnResponse playTurn(int address) {
        Guess playerOneGuess = playerOne.playTurn(address, playerTwo);
        Guess playerTwoGuess = playerTwo.playTurn(address, playerOne);
        return new TurnResponse(playerOneGuess, playerTwoGuess);
    }

    public void runGame() {

        while (playerOne.getShipPoints() != 0 && playerTwo.getShipPoints() != 0) {
            currentTurn++;
        }
        printStatistics();
    }

    public ArrayList<TurnResponse> simulate() {
        ArrayList<TurnResponse> simulationResult = new ArrayList<>();
        TurnResponse playedTurn;

        do {
            playedTurn = playTurn(-1);
            simulationResult.add(playedTurn);
        }
        while (!playedTurn.isGameEnded());

        return simulationResult;
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