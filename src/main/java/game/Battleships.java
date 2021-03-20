package game;

import game.grid.ShipType;
import game.player.*;

public class Battleships {

    private Player humanPlayer;
    private Player computerPlayer;

    public Battleships() {
        humanPlayer = new HumanPlayer("Human");
        computerPlayer = new ComputerPlayer();
    }

    public void runGame() {
        System.out.println("**** Welcome to Battle Ships game ****\n");
        System.out.println("Right now, the sea is empty\n");
      
        humanPlayer.printGrid(true);
        System.out.println("Deploy your ships:");

        for(ShipType shipType : humanPlayer.getShipsList()){
            humanPlayer.deployShip(shipType);
        }

        for(ShipType shipType : humanPlayer.getShipsList()){
            computerPlayer.deployShip(shipType);
        }

        while (humanPlayer.getShipPoints() != 0 && computerPlayer.getShipPoints() != 0) {
            playTurn();
        }
    }

    private void playTurn() {
        System.out.println("YOUR TURN");
        humanPlayer.printGrid(true);
        humanPlayer.guess(computerPlayer);
        computerPlayer.printGrid(false);
        System.out.println("COMPUTER'S TURN");
        computerPlayer.guess(humanPlayer);
    }
}