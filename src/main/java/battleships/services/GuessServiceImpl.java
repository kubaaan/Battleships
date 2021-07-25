package battleships.services;

import battleships.game.Battleships;
import battleships.game.grid.FieldStatus;
import battleships.model.GuessRequest;
import battleships.model.GuessResponse;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GuessServiceImpl implements GuessService {

    public GuessServiceImpl() {
    }

    @Override
    public GuessResponse evaluateGuess(int address) {
        return Battleships.getBattleships().evaluateGuess(address);
    }

    @Override
    public void saveGuessResult(GuessRequest request) {
        System.out.println(request.getGuessResult());
    }
}
