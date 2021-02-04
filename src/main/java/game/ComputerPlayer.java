package game;

import java.util.Random;

public class ComputerPlayer extends Player {

    public ComputerPlayer() {
        super("Computer");
    }

    @Override
    public void deployShip() {

        Random random = new Random();
        int x = random.nextInt(10);
        int y = random.nextInt(10);

        if (grid.deployShip(x, y, 2)) {
            this.numberOfShips++;
        } else {
            this.deployShip();
        }
    }

    @Override
    public void guess(Player rival) {

        Random random = new Random();
        int x = random.nextInt(10);
        int y = random.nextInt(10);
        String coordinates = x + Integer.toString(y);

        if (rival.grid.checkIfFieldIsRevealed(coordinates)) {
            guess(rival);
        } else if (rival.grid.checkFieldShipOwner(coordinates) == 1) {
            rival.grid.markGuessedField(coordinates);
            rival.numberOfShips--;
            System.out.println("BOOM! Computer sunk the ship!");
            rival.grid.printGrid(rival.numberOfShips, this.numberOfShips);
        } else {
            rival.grid.markGuessedField(coordinates);
            System.out.println("Computer missed.");
            rival.grid.printGrid(rival.numberOfShips, this.numberOfShips);
        }
    }
}