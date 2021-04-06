package game.grid;

import java.util.*;

public class Grid {

    public static final int SIZE = 10;

    public static final Map<FieldStatus, String> PLAYER_MARKERS
            = (Map.of(FieldStatus.HIT, "X",
            FieldStatus.OCCUPIED, "@",
            FieldStatus.MISSED, "O",
            FieldStatus.EMPTY, " "));

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

    public Queue<String> getValidSurroundingTargets(String address){

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
        String examinedAddress = getNeighbourAddress(address, direction);

        if(isFieldInGrid(examinedAddress) && !isFieldRevealed(examinedAddress)){
            return examinedAddress;
        }else {
            return null;
        }
    }

    private boolean isFieldInGrid(String address) {
        return fields.containsKey(address);
    }

    private boolean isFieldRevealed(String address) {
        return fields.get(address).isRevealed();
    }

    private String getNeighbourAddress(String address, CoordinateDirection direction) {
        int x = readCoordinateFromAddress(address,"x") + direction.getHORIZONTAL_MOVEMENT();
        int y = readCoordinateFromAddress(address,"y") + direction.getVERTICAL_MOVEMENT();

        return convertCoordinatesToAddress(x,y);
    }

    private int readCoordinateFromAddress(String address, String axe) {
        char coordinate = address.charAt(axe.equals("x") ? 0 : 1);
        return Character.getNumericValue(coordinate);
    }

    private String convertCoordinatesToAddress(int x, int y) {
        return x + Integer.toString(y);
    }

    private boolean checkIfDeploymentPossible(int x, int y, int dir, ShipType shipType){
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
                Field currentField = fields.get(j + Integer.toString(i));
                if (mapIsRevealed) {
                    System.out.print(PLAYER_MARKERS.get(currentField.status));
                } else {
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

    public enum CoordinateDirection {
        UP(0,-1),
        DOWN(0,1),
        LEFT(-1,0),
        RIGHT(1,0);

        private final int HORIZONTAL_MOVEMENT;
        private final int VERTICAL_MOVEMENT;

        CoordinateDirection(int x, int y) {
            this.HORIZONTAL_MOVEMENT = x;
            this.VERTICAL_MOVEMENT = y;
        }

        public int getHORIZONTAL_MOVEMENT() {
            return HORIZONTAL_MOVEMENT;
        }

        public int getVERTICAL_MOVEMENT() {
            return VERTICAL_MOVEMENT;
        }
    }

    class Field {
        private FieldStatus status;

        Field() {
            this.status = FieldStatus.EMPTY;
        }

        private boolean isRevealed(){
            return status == FieldStatus.HIT || status == FieldStatus.MISSED;
        }
    }
}
