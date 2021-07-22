package battleships.controllers;

import battleships.game.grid.FieldStatus;
import battleships.model.DeployResponse;
import battleships.model.GuessRequest;
import battleships.model.GuessResponse;
import battleships.services.GuessService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
public class GuessController {

    private final GuessService guessService;

    public GuessController(GuessService guessService) {
        this.guessService = guessService;
    }

    @GetMapping("/guess")
    public @ResponseBody
    GuessResponse checkGuess (@RequestParam int address){

        return guessService.evaluateGuess(address);
    }

    @PostMapping("/guess")
    public @ResponseBody void saveGuessResult(@RequestBody GuessRequest request){

        guessService.saveGuessResult(request);
    }


}
