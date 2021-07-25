package battleships.game.algorithm;

import battleships.game.grid.Grid;
import battleships.model.GuessResponse;

public interface Algorithm {
    GuessResponse guess(Grid grid);
}
