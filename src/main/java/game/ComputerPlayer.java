package game;

import java.util.Random;

public class ComputerPlayer extends Player {

    public ComputerPlayer() {
        super("Computer");
    }

    @Override
    public void deployShip(ShipType shipType) {

        Random random = new Random();
        int x = random.nextInt(10);
        int y = random.nextInt(10);
        int dir = random.nextInt(2);
        if (grid.deployShip(x, y, (dir==0)?104:118, shipType)) {
            this.numberOfShips++;
        } else {
            this.deployShip(shipType);
        }
    }

    @Override
    public void guess(Player rival) {
        Random random = new Random();
        int x = random.nextInt(10);
        int y = random.nextInt(10);
        String coordinates = x + Integer.toString(y);

        FieldStatus status = rival.grid.getFieldStatus(coordinates);
        switch (status){
            case EMPTY:
                //missed shot
                rival.grid.markStatusOnMap(coordinates, FieldStatus.MISSED);
                break;
            case OCCUPIED:
                //ship hit
                rival.grid.markStatusOnMap(coordinates, FieldStatus.HIT);
                rival.shipPoints--;
                System.out.println("Computer hit your ship.");
                break;
            default:
                //field already revealed (status MISSED or HIT)
                guess(rival);
        }
    }
}