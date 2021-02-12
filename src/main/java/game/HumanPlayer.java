package game;

import java.util.Scanner;

public class HumanPlayer extends Player {

    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public void deployShip(ShipType shipType) {
        String inputMessage = "Enter X coordinate for your " + (numberOfShips + 1) + ". ship: " + shipType.name();
        int x = getUserInputCoordinate(inputMessage);
        inputMessage = "Enter Y coordinate for your " + (numberOfShips + 1) + ". ship: " + shipType.name();
        int y = getUserInputCoordinate(inputMessage);
        inputMessage = "Enter direction (h/v) for your " + (numberOfShips + 1) + ". ship: " + shipType.name();
        int dir = getUserInputDirection(inputMessage);

        if (grid.deployShip(x, y, dir, shipType)) {
            numberOfShips++;
            System.out.println("Player ship " + numberOfShips + " has been deployed");
        } else {
            System.out.println("The field is already occupied or out of map");
            this.deployShip(shipType);
        }
        grid.printGrid(true);
    }

    @Override
    public void guess(Player rival) {
        String inputMessage = "Enter X coordinate:";
        int x = getUserInputCoordinate(inputMessage);
        inputMessage = "Enter Y coordinate:";
        int y = getUserInputCoordinate(inputMessage);
        String coordinates = y + Integer.toString(x);

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
                break;
            default:
                //field already revealed (status MISSED or HIT)
                System.out.println("Field was already chosen. You loose your turn.");
        }
    }

    private int getUserInputCoordinate(String displayedMessage) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(displayedMessage);
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

    private int getUserInputDirection(String displayedMessage){
        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println(displayedMessage);
            if (scanner.hasNext()){
                String in = scanner.nextLine();
                if(in.equals("h")){
                    return 104;
                }else if(in.equals("v")){
                    return 118;
                }else{
                    scanner.nextLine();
                    System.out.println("Invalid direction");
                }
            }
            scanner.nextLine();
            System.out.println("Invalid direction");
        }
    }
}
