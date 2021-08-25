package battleships.game.grid;

import battleships.model.DeployRequest;
import lombok.Getter;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class Grid {

    public static final int SIZE = 10;
    private final Map<Integer, Field> fields;
    @Getter private Map<Integer, Integer> shipsLocations;

    public Grid() {
        this.shipsLocations = new HashMap<>();
        this.fields = new HashMap<>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                fields.put(10* i + j, new Field());
            }
        }
    }


    public Queue<Integer> getValidSurroundingTargets(int address) {
        return FieldSurroundingsScanner.getValidSurroundingTargets(fields, address);
    }

    public FieldStatus guess(int address) {
        Field targetedField = fields.get(address);
        return ShotValidator.evaluateShot(targetedField);
    }

    public boolean deployShip(DeployRequest deployRequest) {
        return ShipDeployer.deployShip(fields, deployRequest, shipsLocations);
    }

    public Field getField(int address){
        return fields.get(address);
    }

    public class Field {
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
            return this.status == battleships.game.grid.FieldStatus.EMPTY;
        }

        protected boolean isRevealed() {
            return status == battleships.game.grid.FieldStatus.HIT || status == battleships.game.grid.FieldStatus.MISSED;
        }
    }
}