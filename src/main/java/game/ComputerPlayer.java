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
        int dirInt = random.nextInt(2);
        char direction = (dirInt == 0) ? 'h':'v';
        while(true){
            if(grid.validateShipsDeployment(x, y, direction, shipType.getLength())){
                grid.placeShipOnMap(x, y, direction, shipType.getLength());
                break;
            }
        }
    }

    @Override
    public void guess(Player rival) {
        Random random = new Random();
        boolean flag = true;
        while(flag) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);
            int fieldStatus = rival.grid.fieldStatus(x, y);
            if(fieldStatus==1){
                rival.grid.markHit(x, y);
                flag = false;
            }else if(fieldStatus==2){
                rival.grid.markMissed(x, y);
                flag = false;
            }
        }
    }
}