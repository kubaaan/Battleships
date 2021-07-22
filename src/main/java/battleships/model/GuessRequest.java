package battleships.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GuessRequest {

    private int guess;
    private String guessResult;
}
