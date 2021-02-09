package game;


public class Battleships {

    private Player humanPlayer;
    private Player computerPlayer;

    public Battleships() {
        humanPlayer = new HumanPlayer("Human");
        computerPlayer = new ComputerPlayer();
    }

    public void runGame() {
        //int fleetSize = 5;
        System.out.println("**** Welcome to Battle Ships game ****\n");
        System.out.println("Right now, the sea is empty\n");
        humanPlayer.grid.printPlayersMap();
        System.out.println("Deploy your ships:");

        for(ShipType shipType : humanPlayer.getShipsList()){
            humanPlayer.deployShip(shipType);
        }
        for(ShipType shipType : computerPlayer.getShipsList()){
            computerPlayer.deployShip(shipType);
        }
        while (humanPlayer.numberOfShips != 0 && computerPlayer.numberOfShips != 0) {
            playTurn();
        }
    }

    private void playTurn() {
        System.out.println("YOUR TURN");
        humanPlayer.guess(computerPlayer);
        System.out.println("COMPUTER'S TURN");
        computerPlayer.guess(humanPlayer);
        System.out.println("Your map");
        humanPlayer.grid.printPlayersMap();
        System.out.println("PC map");
        computerPlayer.grid.printPCMap();
    }
}