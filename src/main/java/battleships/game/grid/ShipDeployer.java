package battleships.game.grid;

import battleships.game.Utilities;
import battleships.model.DeployRequest;

import java.util.Map;

public class ShipDeployer {

    private ShipDeployer() {
    }

    public static boolean deployShip(Map<Integer, Grid.Field> fields, DeployRequest deployRequest, Map<Integer, Integer> shipsLocations){

        int shipLength = deployRequest.getLength();
        Direction direction = deployRequest.getDirection();
        int startFieldAddress = deployRequest.getAddress();

        if (!checkIfDeploymentPossible(fields, deployRequest)) {
            return false;
        }
        for (int l = 0; l < shipLength; l++) {
            int examinedAddress =
                    Utilities.getNeighbourAddress(startFieldAddress, direction, l);
            Grid.Field currentField = fields.get(examinedAddress);
            shipsLocations.put(examinedAddress, shipLength);
            currentField.deploy();
        }
        System.out.println(deployRequest);
        return true;
    }

    private static boolean checkIfDeploymentPossible(Map<Integer,Grid.Field> fields, DeployRequest deployRequest) {

        int shipLength = deployRequest.getLength();
        Direction direction = deployRequest.getDirection();
        int startFieldAddress = deployRequest.getAddress();

        int endFieldAddress
                = Utilities.getNeighbourAddress(startFieldAddress, direction, shipLength - 1);

        if (!fields.containsKey(endFieldAddress)) {
            return false;
        }

        switch (direction) {
            case DOWN:
                if(startFieldAddress/10 != endFieldAddress/10){
                    return false;
                }
            case RIGHT:
                if(startFieldAddress%10 != endFieldAddress%10){
                    return false;
                }
        }

        for (int l = 0; l < shipLength; l++) {
            int examinedAddress = Utilities.getNeighbourAddress(startFieldAddress, direction, l);
            Grid.Field currentField = fields.get(examinedAddress);
            if (!currentField.isEmpty()) {
               return false;
            }
        }
        return true;
    }
}