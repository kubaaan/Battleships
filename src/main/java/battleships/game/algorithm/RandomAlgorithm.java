package battleships.game.algorithm;

import battleships.game.Utilities;
import battleships.game.grid.FieldStatus;
import battleships.game.grid.Grid;
import battleships.model.GuessResponse;

public class RandomAlgorithm implements Algorithm {
    @Override
    public GuessResponse guess(Grid grid) {
        int address = Utilities.generateRandomAddress(Grid.SIZE);
        FieldStatus status = grid.guess(address);

        switch (status) {
            case EMPTY:
                //missed shot
                return new GuessResponse(FieldStatus.MISSED, address);
            case OCCUPIED:
                //ship hit
                return new GuessResponse(FieldStatus.HIT, address);
            default:
                //field already revealed (status MISSED or HIT)
                return guess(grid);
        }
    }
}
