package game;

import java.util.Scanner;

public class HumanPlayer extends Player {

    public HumanPlayer(String name) {
        super(name);
    }


    @Override
    public void deployShip(ShipType shipType) {

        while(true){
            String inputMessage = "Enter X coordinate for your " + (numberOfShips+1) + ": "+(shipType.name() + " (ship's length = " + shipType.getLength() + ")");
            int x = getUserInputCoordinate(inputMessage);
            inputMessage = "Enter Y coordinate for your " + (numberOfShips+1) + ": "+(shipType.name() + " (ship's length = " + shipType.getLength() + ")");
            int y = getUserInputCoordinate(inputMessage);
            String directionMessage = "Choose direction for your ship: (H)orizontal/(V)ertical";
            char direction = getUserInputDirection(directionMessage);
            if(grid.validateShipsDeployment(x, y, direction, shipType.getLength())){
                grid.placeShipOnMap(x, y, direction, shipType.getLength());
                System.out.println(shipType.name() + " added to map.");
                grid.printPlayersMap();
                break;
            }else{
                System.out.println("Please choose coordinates again.");
            }
        }
    }

    @Override
    public void guess(Player rival) {
        String inputMessage = "Enter X coordinate:";
        int x = getUserInputCoordinate(inputMessage);
        inputMessage = "Enter Y coordinate:";
        int y = getUserInputCoordinate(inputMessage);
        int fieldStatus = rival.grid.fieldStatus(x, y);

        switch (fieldStatus){
            case 1:
                rival.grid.markHit(x, y);
                break;
            case 2:
                rival.grid.markMissed(x, y);
                break;
            case -1:
                System.out.println("You chose that field before.\nPay more attention to the map.\nYou loose your turn. ");
                break;
            default:
                break;

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
    //Added method for direction
    private char getUserInputDirection(String directionMessage){
        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println(directionMessage);
            if(scanner.hasNextLine()){
                String userInput = scanner.nextLine();
                char input = userInput.trim().toLowerCase().charAt(0);

                if(input=='h' || input=='v'){
                    return input;
                }
            }
            scanner.nextLine();
            System.out.println("Invalid coordinate");
        }
    }

}