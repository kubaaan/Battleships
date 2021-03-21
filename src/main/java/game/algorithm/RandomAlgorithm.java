package game.algorithm;

import game.Utilities;
import game.grid.FieldStatus;
import game.grid.Grid;

public class RandomAlgorithm implements Algorithm {
    @Override
    public boolean guess(Grid grid) {
        String address = Utilities.generateRandomAddress(Grid.SIZE);
        FieldStatus status = grid.getFieldStatus(address);

        switch (status) {
            case EMPTY:
                //missed shot
                grid.markStatusOnMap(address, FieldStatus.MISSED);
                return false;
            case OCCUPIED:
                //ship hit
                grid.markStatusOnMap(address, FieldStatus.HIT);
                return true;
            default:
                //field already revealed (status MISSED or HIT)
                return guess(grid);
        }
    }
}
