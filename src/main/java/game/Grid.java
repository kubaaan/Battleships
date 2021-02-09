package game;

import java.util.HashMap;
import java.util.Map;

public class Grid {

    public static final int SIZE = 10;
    public static final Map<FieldStatus, String> PLAYER_MARKERS
            = (Map.of(FieldStatus.HIT, "X",
            FieldStatus.OCCUPIED, "@",
            FieldStatus.MISSED, "O",
            FieldStatus.EMPTY, " "));

    private Map<String,Field> fields;

    public Grid() {
        this.fields = new HashMap<>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                fields.put(i + Integer.toString(j), new Field());
            }
        }
    }

    public FieldStatus getFieldStatus(String coordinate){
        return fields.get(coordinate).status;
    }

    private boolean checkIfDeploymentPossible(int x, int y, int dir, ShipType shipType){
        Field currentField;
        int shipsLength = shipType.getLength();
        if(dir==104){
            //if horizontal
            if((shipsLength+x)>SIZE){
                return false;
            }
            for(int l=0; l < shipsLength; l++){
                currentField = fields.get(y + Integer.toString(x+l));
                if(currentField.status != FieldStatus.EMPTY){
                    return false;
                }
            }
        }else if(dir == 118){
            //if vertical
            if((shipsLength+y)>SIZE){
                return false;
            }
            for(int l=0; l < shipsLength; l++){
                currentField = fields.get((y+l) + Integer.toString(x));
                if(currentField.status != FieldStatus.EMPTY){
                    return false;
                }
            }
        }else{
            return false;
        }
        return true;
    }

    public boolean deployShip(int x, int y, int dir, ShipType shipType) {
        Field currentField;
        int shipsLength = shipType.getLength();
        if(!checkIfDeploymentPossible(x, y, dir, shipType)){
            return false;
        }
        if(dir==104){
            //if horizontal
            for(int l=0; l < shipsLength; l++){
                currentField = fields.get(y + Integer.toString(x+l));
                currentField.status = FieldStatus.OCCUPIED;
            }
        }else if(dir == 118){
            //if vertical
            for(int l=0; l < shipsLength; l++){
                currentField = fields.get((y+l) + Integer.toString(x));
                currentField.status = FieldStatus.OCCUPIED;
            }
        }else{
            return false;
        }
        return true;
    }

    protected void printGrid(boolean mapRevealed) {

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
                if(mapRevealed) {
                    System.out.print(PLAYER_MARKERS.get(currentField.status));
                }else if(!mapRevealed){
                    switch (currentField.status){
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

    public void markStatusOnMap(String coordinates, FieldStatus fieldStatus){
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
