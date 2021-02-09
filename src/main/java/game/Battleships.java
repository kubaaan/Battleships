package game;


public class Battleships {

    private Player humanPlayer;
    private Player computerPlayer;

    public Battleships() {
        humanPlayer = new HumanPlayer("Player");
        computerPlayer = new ComputerPlayer();
    }

    public void runGame() {
        int fleetSize = 5;
        System.out.println("**** Welcome to Battle Ships game ****\n");
        System.out.println("Right now, the sea is empty\n");
        humanPlayer.grid.printGrid(true);
        System.out.println("Deploy your ships:");

        for(ShipType shipType : humanPlayer.getShipsList()){
            humanPlayer.deployShip(shipType);
        }

        for(ShipType shipType : humanPlayer.getShipsList()){
            computerPlayer.deployShip(shipType);
        }
        while (humanPlayer.shipPoints != 0 && computerPlayer.shipPoints != 0) {
            playTurn();
        }
    }

    private void playTurn() {
        System.out.println("YOUR TURN");
        humanPlayer.grid.printGrid(true);
        humanPlayer.guess(computerPlayer);
        computerPlayer.grid.printGrid(false);
        System.out.println("COMPUTER'S TURN");
        computerPlayer.guess(humanPlayer);
    }
}
