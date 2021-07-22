package battleships.game.player;


import battleships.game.Utilities;
import battleships.game.grid.*;
import java.util.Scanner;

public class HumanPlayer extends Player {

    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public void deployShips() {
        System.out.println("Deploy your ships:");

        for (ShipType shipType : getShipsList()) {
            deployShip(shipType);
        }
    }

    @Override
    public void deployShip(battleships.game.grid.ShipType shipType) {
        String inputMessage = "Enter X coordinate for your " + (numberOfShips + 1) + ". ship: " + shipType.name();
        int x = getUserInputCoordinate(inputMessage);
        inputMessage = "Enter Y coordinate for your " + (numberOfShips + 1) + ". ship: " + shipType.name();
        int y = getUserInputCoordinate(inputMessage);
        inputMessage = "Enter direction (h/v) for your " + (numberOfShips + 1) + ". ship: " + shipType.name();
        Direction dir = getUserInputDirection(inputMessage);

        Ship ship = new Ship(x, y, dir, shipType);

        if (grid.deployShip(ship)) {
            numberOfShips++;
            System.out.println("Player ship " + numberOfShips + " has been deployed");
        } else {
            System.out.println("The field is already occupied or out of map");
            this.deployShip(shipType);
        }
    }

    @Override
    public void playTurn(Player rival) {
        System.out.println(name.toUpperCase() + "'S TURN");
        guess(rival);
    }

    @Override
    public void guess(Player rival) {
        String inputMessage = "Enter X coordinate:";
        int x = getUserInputCoordinate(inputMessage);
        inputMessage = "Enter Y coordinate:";
        int y = getUserInputCoordinate(inputMessage);
        String address = Utilities.convertCoordinatesToAddress(x, y);

        FieldStatus status = rival.grid.guess(address);
        switch (status) {
            case EMPTY:
                //missed shot
                break;
            case OCCUPIED:
                //ship hit
                rival.shipPoints--;
                break;
            default:
                //field already revealed (status MISSED or HIT)
                System.out.println("Field was already chosen. You loose your turn.");
        }
    }

    private int getUserInputCoordinate(String displayedMessage) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(displayedMessage);
            if (scanner.hasNextInt()) {
                int userEntry = scanner.nextInt();
                if (userEntry >= 0 && userEntry < Grid.SIZE) {
                    return userEntry;
                }
            }
            scanner.nextLine();
            System.out.println("Invalid coordinate");
        }
    }

    private Direction getUserInputDirection(String displayedMessage) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(displayedMessage);

            if (!scanner.hasNextInt()) {
                if (scanner.hasNext()) {
                    String in = scanner.nextLine();
                    if (in.equals("h")) {
                        return Direction.RIGHT;
                    } else if (in.equals("v")) {
                        return Direction.DOWN;
                    } else {
                        System.out.println("Invalid direction");
                    }
                }
            } else {
                scanner.nextLine();
                System.out.println("Invalid direction");
            }
        }
    }
}
