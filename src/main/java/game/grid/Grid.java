package game.grid;

import game.CoordinateDirection;
import java.util.HashMap;
import java.util.Map;

public class Grid {

    public static final int SIZE = 10;

    public static final Map<FieldStatus, String> PLAYER_MARKERS
            = (Map.of(FieldStatus.HIT, "X",
            FieldStatus.OCCUPIED, "@",
            FieldStatus.MISSED, "O",
            FieldStatus.EMPTY, " "));

    private Map<String, Field> fields;

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

    public String isPossibleTarget(String address, CoordinateDirection direction) {
        String examinedAddress = getNeighbourAddress(address, direction);

        if(isFieldInGrid(examinedAddress) && !isFieldRevealed(examinedAddress)){
            return examinedAddress;
        }else {
            return null;
        }
    }

    private boolean isFieldInGrid(String address) {
        int x = readCoordinateFromAddress(address,"x");
        int y = readCoordinateFromAddress(address,"y");

        return 0 <= x && x < SIZE && 0 <= y && y < SIZE;
    }

    private boolean isFieldRevealed(String address) {
        FieldStatus status = getFieldStatus(address);
        return status == FieldStatus.HIT || status == FieldStatus.MISSED;
    }

    private String getNeighbourAddress(String address, CoordinateDirection direction) {
        int x = readCoordinateFromAddress(address,"x") + direction.getHORIZONTAL_MOVEMENT();
        int y = readCoordinateFromAddress(address,"y") + direction.getVERTICAL_MOVEMENT();

        return convertCoordinatesToAddress(x,y);
    }

    public int readCoordinateFromAddress(String address, String axe) {
        char coordinate = address.charAt(axe.equals("x") ? 0 : 1);
        return Character.getNumericValue(coordinate);
    }

    private String convertCoordinatesToAddress(int x, int y) {
        return x + Integer.toString(y);
    }

    private boolean checkIfDeploymentPossible(int x, int y, int dir, ShipType shipType) {
        Field currentField;
        int shipsLength = shipType.getLength();
        if (dir == 104) {
            //if horizontal
            if ((shipsLength + x) > SIZE) {
                return false;
            }
            for (int l = 0; l < shipsLength; l++) {
                currentField = fields.get(y + Integer.toString(x + l));
                if (currentField.status != FieldStatus.EMPTY) {
                    return false;
                }
            }
        } else if (dir == 118) {
            //if vertical
            if ((shipsLength + y) > SIZE) {
                return false;
            }
            for (int l = 0; l < shipsLength; l++) {
                currentField = fields.get((y + l) + Integer.toString(x));
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
        int shipsLength = shipType.getLength();
        if (!checkIfDeploymentPossible(x, y, dir, shipType)) {
            return false;
        }
        if (dir == 104) {
            //if horizontal
            for (int l = 0; l < shipsLength; l++) {
                currentField = fields.get(y + Integer.toString(x + l));
                currentField.status = FieldStatus.OCCUPIED;
            }
        } else if (dir == 118) {
            //if vertical
            for (int l = 0; l < shipsLength; l++) {
                currentField = fields.get((y + l) + Integer.toString(x));
                currentField.status = FieldStatus.OCCUPIED;
            }
        } else {
            return false;
        }
        return true;
    }

    public void printGrid(boolean mapIsRevealed) {

        Runnable printXAxe = () -> {
            System.out.print("   ");
            for (int i = 0; i < SIZE; i++) {
                System.out.print(i);
            }
            System.out.println();
        };

        printXAxe.run();

        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + " |");
            for (int j = 0; j < SIZE; j++) {
                Field currentField = fields.get(i + Integer.toString(j));
                if (mapIsRevealed) {
                    System.out.print(PLAYER_MARKERS.get(currentField.status));
                } else if (!mapIsRevealed) {
                    switch (currentField.status) {
                        case HIT:
                        case MISSED:
                            System.out.print(PLAYER_MARKERS.get(currentField.status));
                            break;
                        default:
                            System.out.print(PLAYER_MARKERS.get(FieldStatus.EMPTY));
                    }
                }
            }
            System.out.println("| " + i);
        }
        printXAxe.run();
    }

    public void markStatusOnMap(String coordinates, FieldStatus fieldStatus) {
        fields.get(coordinates).status = fieldStatus;
        System.out.println(fields.get(coordinates).status);
    }

    class Field {
        private FieldStatus status;

        Field() {
            this.status = FieldStatus.EMPTY;
        }
    }
}
