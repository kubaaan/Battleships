package battleships.game.algorithm;

import battleships.game.Utilities;
import battleships.game.grid.FieldStatus;
import battleships.game.grid.Grid;
import battleships.model.Guess;

public class RandomAlgorithm implements Algorithm {
    @Override
    public Guess guess(Grid grid) {
        int address = generateAddress();
        FieldStatus status = grid.guess(address);

        switch (status) {
            case EMPTY:
                //missed shot
                return new Guess(FieldStatus.MISSED, address);
            case OCCUPIED:
                //ship hit
                return new Guess(FieldStatus.HIT, address);
            default:
                //field already revealed (status MISSED or HIT)
                return guess(grid);
        }
    }

    @Override
    public int generateAddress() {
        return Utilities.generateRandomAddress(Grid.SIZE);
    }

    @Override
    public void addOccupiedAddress(int address, Grid grid) {

    }
}
