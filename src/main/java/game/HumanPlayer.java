package game;

import java.util.Scanner;

public class HumanPlayer extends Player {

    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public void deployShip() {
        String inputMessage = "Enter X coordinate for your " + (numberOfShips + 1) + ". ship:";
        int x = getUserInputCoordinate(inputMessage);
        inputMessage = "Enter Y coordinate for your " + (numberOfShips + 1) + ". ship:";
        int y = getUserInputCoordinate(inputMessage);

        if (grid.deployShip(x, y, 1)) {
            numberOfShips++;
            System.out.println("Player ship " + numberOfShips + " has been deployed");
        } else {
            System.out.println("The field is already occupied");
            this.deployShip();
        }
    }

    @Override
    public void guess(Player rival) {
        String inputMessage = "Enter X coordinate:";
        int x = getUserInputCoordinate(inputMessage);
        inputMessage = "Enter Y coordinate:";
        int y = getUserInputCoordinate(inputMessage);
        String coordinates = x + Integer.toString(y);

        if (rival.grid.checkIfFieldIsRevealed(coordinates)) {
            System.out.println("The field has been already revealed. You need to choose another one.");
            guess(rival);
        } else {
            rival.grid.markGuessedField(coordinates);
            switch (rival.grid.checkFieldShipOwner(coordinates)) {
                case 0:
                    System.out.println("You missed.");
                    break;
                case 2:
                    System.out.println("BOOM! You sunk the ship!");
                    rival.numberOfShips--;
                    break;
                default:
                    System.out.println("Wrong mark on the board");
            }
            rival.grid.printGrid(this.numberOfShips, rival.numberOfShips);
        }

    }

    private int getUserInputCoordinate(String displayedMessage) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(displayedMessage);
            if (scanner.hasNextInt()) {
                int userEntry = scanner.nextInt();
                if (userEntry >= 0 && userEntry < grid.SIZE) {
                    return userEntry;
                }
            }
            scanner.nextLine();
            System.out.println("Invalid coordinate");
        }
    }
}