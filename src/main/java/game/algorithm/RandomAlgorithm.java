package game.algorithm;

import game.Utilities;
import game.grid.FieldStatus;
import game.grid.Grid;

public class RandomAlgorithm implements Algorithm {
    @Override
    public boolean guess(Grid grid) {
        String address = Utilities.generateRandomAddress(Grid.SIZE);
        FieldStatus status = grid.guess(address);

        switch (status) {
            case EMPTY:
                //missed shot
                return false;
            case OCCUPIED:
                //ship hit
                return true;
            default:
                //field already revealed (status MISSED or HIT)
                return guess(grid);
        }
    }
}
