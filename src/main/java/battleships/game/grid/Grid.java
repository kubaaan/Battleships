package battleships.game.grid;

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
        return battleships.game.grid.FieldSurroundingsScanner.getValidSurroundingTargets(fields, address);
    }

    public battleships.game.grid.FieldStatus guess(String address) {
        Field targetedField = fields.get(address);
        return battleships.game.grid.ShotValidator.evaluateShot(targetedField);
    }

    public boolean deployShip(battleships.game.grid.Ship ship) {
        return battleships.game.grid.ShipDeployer.deployShip(fields, ship, shipsLocations);
    }

    class Field {
        @Getter
        private battleships.game.grid.FieldStatus status;

        Field() {
            this.status = battleships.game.grid.FieldStatus.EMPTY;
        }

        protected void deploy() {
            this.status = battleships.game.grid.FieldStatus.OCCUPIED;
        }

        protected void reveal() {
            switch (this.status) {
                case EMPTY:
                    //missed shot
                    this.status = battleships.game.grid.FieldStatus.MISSED;
                    break;
                case OCCUPIED:
                    //ship hit
                    this.status = battleships.game.grid.FieldStatus.HIT;
                    break;
            }
        }

        protected boolean isEmpty() {
            return this.status == battleships.game.grid.FieldStatus.EMPTY;
        }

        protected boolean isRevealed() {
            return status == battleships.game.grid.FieldStatus.HIT || status == battleships.game.grid.FieldStatus.MISSED;
        }
    }
}