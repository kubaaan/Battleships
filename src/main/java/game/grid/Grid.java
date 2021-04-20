package game.grid;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class Grid {

    public static final int SIZE = 10;
    private final Map<String, Field> fields;
    @Getter private Map<String, Integer> shipsLocations;

    public Grid() {
        this.shipsLocations = new HashMap<>();
        this.fields = new HashMap<>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                fields.put(i + Integer.toString(j), new Field());
            }
        }
    }

    public void printGrid(boolean mapIsRevealed) {
        GridPrinter.printGrid(fields, mapIsRevealed);
    }

    public Queue<String> getValidSurroundingTargets(String address) {
        return FieldSurroundingsScanner.getValidSurroundingTargets(fields, address);
    }

    public FieldStatus guess(String address) {
        Field targetedField = fields.get(address);
        return ShotValidator.evaluateShot(targetedField);
    }

    public boolean deployShip(Ship ship) {
        return ShipDeployer.deployShip(fields, ship, shipsLocations);
    }

    class Field {
        @Getter
        private FieldStatus status;

        Field() {
            this.status = FieldStatus.EMPTY;
        }

        protected void deploy() {
            this.status = FieldStatus.OCCUPIED;
        }

        protected void reveal() {
            switch (this.status) {
                case EMPTY:
                    //missed shot
                    this.status = FieldStatus.MISSED;
                    break;
                case OCCUPIED:
                    //ship hit
                    this.status = FieldStatus.HIT;
                    break;
            }
        }
        protected boolean isEmpty() {
            return this.status == FieldStatus.EMPTY;
        }

        protected boolean isRevealed() {
            return status == FieldStatus.HIT || status == FieldStatus.MISSED;
        }
    }
}