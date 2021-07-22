package battleships.services;

import battleships.model.GuessRequest;
import battleships.model.GuessResponse;

public interface GuessService {
    GuessResponse evaluateGuess(int address);
    void saveGuessResult(GuessRequest request);
}
