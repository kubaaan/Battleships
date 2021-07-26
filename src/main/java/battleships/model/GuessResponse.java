package battleships.model;

import battleships.game.grid.FieldStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class GuessResponse {
    private FieldStatus guessResult;
    private int guess;
    private boolean isGameEnded;

    public GuessResponse(FieldStatus guessResult, int guess) {
        this(guessResult, guess, false);
    }
}
