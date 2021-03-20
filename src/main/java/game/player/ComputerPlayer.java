package game.player;

import game.*;
import game.grid.FieldStatus;
import game.grid.Grid;
import game.grid.ShipType;

import java.util.*;

public class ComputerPlayer extends Player {

    private boolean targetMode;
    private Queue<String> targets = new LinkedList<>();

    public ComputerPlayer() {
        super("Computer");
        this.targetMode = false;
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
    public void guess(Player rival) {

        String address = generateAddress();

        FieldStatus status = rival.grid.getFieldStatus(address);
        switch (status) {
            case EMPTY:
                //missed shot
                rival.grid.markStatusOnMap(address, FieldStatus.MISSED);
                break;
            case OCCUPIED:
                //ship hit
                addSurroundingsToTargetList(rival.grid, address);
                this.targetMode = true;
                rival.grid.markStatusOnMap(address, FieldStatus.HIT);
                rival.shipPoints--;
                System.out.println("Computer hit your ship.");
                break;
            default:
                //field already revealed (status MISSED or HIT)
                guess(rival);
        }
    }

    private String generateAddress() {

        if (this.targetMode) {
            if (targets.peek() != null) {
                return targets.poll();
            } else {
                this.targetMode = false;
                return generateAddress();
            }
        } else {
            int x = Utilities.generateRandomCoordinate(grid.SIZE);
            int y = Utilities.generateRandomCoordinate(grid.SIZE);
            return x + Integer.toString(y);
        }
    }

    private void addSurroundingsToTargetList(Grid grid, String address) {

        List<CoordinateDirection> directions = new LinkedList<>
                (Arrays.asList(CoordinateDirection.UP, CoordinateDirection.DOWN,
                        CoordinateDirection.LEFT, CoordinateDirection.RIGHT));

        for (CoordinateDirection direction : directions) {
            if (grid.isPossibleTarget(address, direction) != null) {
                targets.add(grid.isPossibleTarget(address, direction));
            }
        }
    }


}