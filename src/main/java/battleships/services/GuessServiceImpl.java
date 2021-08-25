package battleships.services;

import battleships.game.Battleships;
import battleships.model.GuessRequest;
import battleships.model.Guess;
import org.springframework.stereotype.Service;

@Service
public class GuessServiceImpl implements GuessService {

    public GuessServiceImpl() {
    }

    @Override
    public Guess evaluateGuess(int address) {
        return Battleships.getBattleships().evaluateGuess(address);
    }

    @Override
    public void saveGuessResult(GuessRequest request) {
        System.out.println(request.getGuessResult());
    }
}
