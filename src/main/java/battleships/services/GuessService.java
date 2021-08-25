package battleships.services;

import battleships.model.GuessRequest;
import battleships.model.Guess;

public interface GuessService {
    Guess evaluateGuess(int address);
    void saveGuessResult(GuessRequest request);
}
