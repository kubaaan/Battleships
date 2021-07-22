package battleships.services;

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
        Random random = new Random();
        int rnd = random.nextInt(99);

        return new GuessResponse(rnd, FieldStatus.HIT);
    }

    @Override
    public void saveGuessResult(GuessRequest request) {
        System.out.println(request.getGuessResult());
    }
}
