package game;

public class Battleships {

    private Player humanPlayer;
    private Player computerPlayer;

    public Battleships() {
        humanPlayer = new HumanPlayer("Human");
        computerPlayer = new ComputerPlayer();
    }

    public void runGame() {
        int fleetSize = 5;
        System.out.println("**** Welcome to Battle Ships game ****\n");
        System.out.println("Right now, the sea is empty\n");
        humanPlayer.grid.printGrid();
        System.out.println("Deploy your ships:");
        while (humanPlayer.numberOfShips < fleetSize) {
            humanPlayer.deployShip();
        }
        while (computerPlayer.numberOfShips < fleetSize) {
            computerPlayer.deployShip();
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
    }
}