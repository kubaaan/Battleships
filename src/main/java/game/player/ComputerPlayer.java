package game.player;

import game.algorithm.Algorithm;
import game.algorithm.AlgorithmType;
import game.algorithm.HuntTargetAlgorithm;
import game.algorithm.RandomAlgorithm;
import game.grid.ShipType;

import java.util.*;

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
            case RANDOM:
            default:
                this.shootingAlgorithm = new RandomAlgorithm();
                break;
        }
    }

    @Override
    public void deployShips() {
        for (ShipType shipType : getShipsList()) {
            deployShip(shipType);
        }
    }

    @Override
    public void deployShip(ShipType shipType) {

        Random random = new Random();
        int x = random.nextInt(10);
        int y = random.nextInt(10);
        int dir = random.nextInt(2);

        if (grid.deployShip(x, y, (dir == 0) ? 104 : 118, shipType)) {
            this.numberOfShips++;
        } else {
            this.deployShip(shipType);
        }
    }

    @Override
    public void playTurn(Player rival) {
        printGrid(false);
        System.out.println(name.toUpperCase() + "'S TURN");
        guess(rival);
    }

    @Override
    public void guess(Player rival) {

        boolean status = shootingAlgorithm.guess(rival.grid);

        if (status) {
            rival.shipPoints--;
        }
    }

}