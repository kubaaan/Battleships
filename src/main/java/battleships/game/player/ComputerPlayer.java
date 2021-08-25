package battleships.game.player;

import battleships.game.Battleships;
import battleships.game.algorithm.*;
import battleships.game.grid.*;
import battleships.model.DeployRequest;
import battleships.model.Guess;

import java.util.Map;
import java.util.Random;

public class ComputerPlayer extends Player {

    private final Algorithm shootingAlgorithm;

    public ComputerPlayer() {
        this("Computer", AlgorithmType.HUNT_TARGET);
    }

    public ComputerPlayer(String name, AlgorithmType algorithmType) {
        super(name);
        switch (algorithmType) {
            case HUNT_TARGET:
                this.shootingAlgorithm = new HuntTargetAlgorithm();
                break;
            case HUNT_TARGET_WITH_PARITY:
                this.shootingAlgorithm = new HuntTargetWithParityAlgorithm();
                break;
            case RANDOM:
            default:
                this.shootingAlgorithm = new RandomAlgorithm();
                break;
        }

        deployShips();
    }

    private void deployShip(ShipType shipType) {
        DeployRequest deployRequest = createDeployRequest(shipType);

        if (deployShip(deployRequest)) {
            this.numberOfShips++;
        } else {
            this.deployShip(shipType);
        }
    }

    private DeployRequest createDeployRequest(ShipType shipType) {

        Random random = new Random();
        int x = random.nextInt(10);
        int y = random.nextInt(10);
        int dir = random.nextInt(2);

        int address = 10 * x + y;
        Direction direction = (dir == 0) ? Direction.RIGHT : Direction.DOWN;

        return new DeployRequest(shipType, address, direction, shipType.getLength());
    }

    private void deployShips() {
        Map<ShipType, Integer> shipList = Battleships.getShipsList();
        shipList.forEach((ship, length) -> deployShip(ship));
    }

    @Override
    public int guess(Player rival) {

        Guess guess = shootingAlgorithm.guess(rival.grid);

        if (guess.getGuessResult() == FieldStatus.HIT) {
            rival.shipPoints--;
        }

        return guess.getGuess();
    }

    @Override
    public Guess playTurn(int address, Player rival) {

        int playerShot = this.generateAddress(address);
        Guess playerGuess = rival.evaluateGuess(playerShot);
        FieldStatus shotResult = playerGuess.getGuessResult();

        if (shotResult != FieldStatus.EMPTY && shotResult != FieldStatus.OCCUPIED) {
            return playTurn(address, rival);
        }

        if (playerGuess.getGuessResult() == FieldStatus.OCCUPIED) {
            shootingAlgorithm.addOccupiedAddress(playerShot, rival.grid);
            rival.shipPoints--;
        }
        if (rival.getShipPoints() == 0) {
            playerGuess.setGameEnded(true);
        }
        return playerGuess;
    }

    @Override
    public int generateAddress(int address) {
        return shootingAlgorithm.generateAddress();
    }
}