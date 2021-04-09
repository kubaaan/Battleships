package game.grid;

import game.Utilities;
import lombok.Getter;

import java.util.*;

public class Grid {

    public static final int SIZE = 10;
    private final Map<String, Field> fields;

    public Grid() {
        this.fields = new HashMap<>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                fields.put(i + Integer.toString(j), new Field());
            }
        }
    }

    public FieldStatus getFieldStatus(String address) {
        return fields.get(address).status;
    }

    public Queue<String> getValidSurroundingTargets(String address) {

        List<CoordinateDirection> directions = new LinkedList<>
                (Arrays.asList(CoordinateDirection.UP, CoordinateDirection.DOWN,
                        CoordinateDirection.LEFT, CoordinateDirection.RIGHT));

        Queue<String> targets = new LinkedList<>();

        for (CoordinateDirection direction : directions) {
            if (isPossibleTarget(address, direction) != null) {
                targets.add(isPossibleTarget(address, direction));
            }
        }

        return targets;
    }

    private String isPossibleTarget(String address, CoordinateDirection direction) {
        String examinedAddress = Utilities.getNeighbourAddress(address, direction);

        if (isFieldInGrid(examinedAddress) && !isFieldRevealed(examinedAddress)) {
            return examinedAddress;
        } else {
            return null;
        }
    }

    private boolean isFieldInGrid(String address) {
        return fields.containsKey(address);
    }

    private boolean isFieldRevealed(String address) {
        return fields.get(address).isRevealed();
    }

    private boolean checkIfDeploymentPossible(int x, int y, int dir, ShipType shipType) {
        Field currentField;
        int shipLength = shipType.getLength();
        if (dir == 104) {
            //if horizontal
            if ((shipLength + x) > SIZE) {
                return false;
            }
            for (int l = 0; l < shipLength; l++) {
                currentField = fields.get((x + l) + Integer.toString(y));
                if (currentField.status != FieldStatus.EMPTY) {
                    return false;
                }
            }
        } else if (dir == 118) {
            //if vertical
            if ((shipLength + y) > SIZE) {
                return false;
            }
            for (int l = 0; l < shipLength; l++) {
                currentField = fields.get(x + Integer.toString(y + l));
                if (currentField.status != FieldStatus.EMPTY) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    public boolean deployShip(int x, int y, int dir, ShipType shipType) {
        Field currentField;
        int shipLength = shipType.getLength();
        if (!checkIfDeploymentPossible(x, y, dir, shipType)) {
            return false;
        }
        if (dir == 104) {
            //if horizontal
            for (int l = 0; l < shipLength; l++) {
                currentField = fields.get((x + l) + Integer.toString(y));
                currentField.status = FieldStatus.OCCUPIED;
            }
        } else if (dir == 118) {
            //if vertical
            for (int l = 0; l < shipLength; l++) {
                currentField = fields.get(x + Integer.toString(y + l));
                currentField.status = FieldStatus.OCCUPIED;
            }
        } else {
            return false;
        }
        return true;
    }

    public void printGrid(boolean mapIsRevealed) {
        GridPrinter.printGrid(fields, mapIsRevealed);
    }

    public void markStatusOnMap(String coordinates, FieldStatus fieldStatus) {
        fields.get(coordinates).status = fieldStatus;
        System.out.println(fields.get(coordinates).status);
    }


    class Field {
        @Getter
        private FieldStatus status;

        Field() {
            this.status = FieldStatus.EMPTY;
        }

        private boolean isRevealed() {
            return status == FieldStatus.HIT || status == FieldStatus.MISSED;
        }
    }
}
