package game.grid;

import game.Utilities;

import java.util.Map;

public class ShipDeployer {

    private ShipDeployer() {
    }

    protected static boolean deployShip(Map<String,Grid.Field> fields, Ship ship, Map<String, Integer> shipsLocations){

        int shipLength = ship.getType().getLength();
        Direction direction = ship.getDirection();
        String startFieldAddress = ship.getAddress();

        if (!checkIfDeploymentPossible(fields, ship)) {
            return false;
        }
        for (int l = 0; l < shipLength; l++) {
            String examinedAddress =
                    Utilities.getNeighbourAddress(startFieldAddress, direction, l);
            Grid.Field currentField = fields.get(examinedAddress);
            shipsLocations.put(examinedAddress, shipLength);
            currentField.deploy();
        }
        return true;
    }

    private static boolean checkIfDeploymentPossible(Map<String,Grid.Field> fields, Ship ship) {

        String startFieldAddress = ship.getAddress();
        Direction direction = ship.getDirection();
        int shipLength = ship.getType().getLength();

        String endFieldAddress
                = Utilities.getNeighbourAddress(startFieldAddress, direction, shipLength - 1);
        if (!fields.containsKey(endFieldAddress)) {
            return false;
        }
        for (int l = 0; l < shipLength; l++) {
            String examinedAddress = Utilities.getNeighbourAddress(startFieldAddress, direction, l);
            Grid.Field currentField = fields.get(examinedAddress);
            if (!currentField.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}