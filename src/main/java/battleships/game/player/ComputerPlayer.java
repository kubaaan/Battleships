package battleships.game.player;

import battleships.game.Battleships;
import battleships.game.algorithm.*;
import battleships.game.grid.FieldStatus;
import battleships.game.grid.ShipType;
import battleships.game.grid.Direction;
import battleships.model.DeployRequest;
import battleships.model.GuessResponse;

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

    private void deployShip(ShipType shipType){
        DeployRequest deployRequest = createDeployRequest(shipType);

        if (deployShip(deployRequest)) {
            this.numberOfShips++;
        } else {
            this.deployShip(shipType);
        }
    }

    private DeployRequest createDeployRequest(ShipType shipType){

        Random random = new Random();
        int x = random.nextInt(10);
        int y = random.nextInt(10);
        int dir = random.nextInt(2);

        int address = 10 * x + y;
        Direction direction = (dir == 0) ? Direction.RIGHT : Direction.DOWN;

        return new DeployRequest(shipType, address, direction, shipType.getLength());
    }

    private void deployShips() {
        Map<ShipType,Integer> shipList = Battleships.getShipsList();
        shipList.forEach((ship, length) -> deployShip(ship));
    }

    @Override
    public int guess(Player rival) {

        GuessResponse guessResponse = shootingAlgorithm.guess(rival.grid);

        if (guessResponse.getGuessResult() == FieldStatus.HIT) {
            rival.shipPoints--;
        }

        return guessResponse.getGuess();
    }

}