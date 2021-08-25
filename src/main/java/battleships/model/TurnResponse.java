package battleships.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TurnResponse {
    private Guess guessRequest;
    private Guess guessResponse;

    public boolean isGameEnded(){
        return guessRequest.isGameEnded() || guessResponse.isGameEnded();
    }
}
