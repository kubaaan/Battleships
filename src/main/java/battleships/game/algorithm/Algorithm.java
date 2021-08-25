package battleships.game.algorithm;

import battleships.game.grid.Grid;
import battleships.model.Guess;

public interface Algorithm {
    Guess guess(Grid grid);
    int generateAddress();
    void addOccupiedAddress(int address, Grid grid);
}
